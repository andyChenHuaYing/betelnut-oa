package org.betelnut.examples.showcase.demos.hystrix.dependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.betelnut.examples.showcase.entity.User;
import org.betelnut.examples.showcase.service.AccountEffectiveService;
import org.betelnut.examples.showcase.webservice.rest.RestException;
import org.betelnut.examples.showcase.webservice.rest.UserDTO;
import org.betelnut.modules.mapper.BeanMapper;
import org.betelnut.modules.utils.Threads;

/**
 * 模拟被Service所依赖的Resource.
 */
@Controller
public class DependencyResourceController {
	public static final int TIMEOUT = 30000;

	public static String status = "normal";

	@Autowired
	private AccountEffectiveService accountService;

	/**
	 * 根据控制器中的状态而演示不同的行为，如正常返回，30秒后返回或直接报错
	 */
	@RequestMapping(value = "/hystrix/resource/{id}", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO getUser(@PathVariable("id") Long id) {
		// 正常返回.
		if ("normal".equals(status)) {
			return handleRequest(id);
		}

		// 演示超时，30秒后返回.
		if ("timeout".equals(status)) {
			Threads.sleep(TIMEOUT);
			return handleRequest(id);
		}

		// 演示服务端出错情况.
		if ("server-error".equals(status)) {
			throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Exception");
		}

		// 演示客户端请求出错。
		if ("bad-request".equals(status)) {
			throw new RestException(HttpStatus.BAD_REQUEST, "Client send a bad request");
		}

		return null;
	}

	private UserDTO handleRequest(Long id) {
		User user = accountService.getUser(id);
		UserDTO dto = BeanMapper.map(user, UserDTO.class);
		dto.setTeamId(user.getTeam().getId());
		return dto;
	}

}
