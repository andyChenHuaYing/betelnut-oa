package org.betelnut.examples.showcase.demos.hystrix.web;

import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixRuntimeException.FailureType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.betelnut.modules.utils.Exceptions;
import org.betelnut.modules.web.MediaTypes;

/**
 * 自定义ExceptionHandler，专门处理Hystrix异常.
 */
@ControllerAdvice
public class HystrixExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 处理Hystrix Runtime异常, 分为两类:
	 * 一类是Command内部抛出异常(返回500).
	 * 一类是Hystrix已进入保护状态(返回503).
	 */
	@ExceptionHandler(value = { HystrixRuntimeException.class })
	public final ResponseEntity<?> handleException(HystrixRuntimeException e, WebRequest request) {
		HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
		String message = e.getMessage();

		FailureType type = e.getFailureType();

		// 对命令抛出的异常进行特殊处理
		if (type.equals(FailureType.COMMAND_EXCEPTION)) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			message = Exceptions.getErrorMessageWithNestedException(e);
		}

		logger.error(message, e);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
		return handleExceptionInternal(e, message, headers, status, request);
	}

	/**
	 * 处理Hystrix ClientException异常(返回404).
	 * ClientException表明是客户端请求参数本身的问题, 不计入异常次数统计。
	 */
	@ExceptionHandler(value = { HystrixBadRequestException.class })
	public final ResponseEntity<?> handleException(HystrixBadRequestException e, WebRequest request) {
		String message = Exceptions.getErrorMessageWithNestedException(e);
		logger.error(message, e);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(MediaTypes.TEXT_PLAIN_UTF_8));
		return handleExceptionInternal(e, message, headers, HttpStatus.BAD_REQUEST, request);
	}
}
