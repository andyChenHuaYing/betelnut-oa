package org.betelnut.examples.showcase.demos.hystrix.web;

import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.betelnut.examples.showcase.demos.hystrix.dependency.DependencyResourceController;
import org.betelnut.examples.showcase.demos.hystrix.service.UserService;
import org.betelnut.examples.showcase.webservice.rest.UserDTO;
import org.betelnut.modules.web.MediaTypes;

import javax.annotation.PostConstruct;
import java.util.Map;

@Controller
public class HystrixDemoController {
	private Map<String, String> allStatus = Maps.newLinkedHashMap();

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/hystrix", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("allStatus", allStatus);
		model.addAttribute("statusHolder", new StatusHolder(DependencyResourceController.status));
		model.addAttribute("metrics", userService.getHystrixMetrics());
		return "hystrix/hystrix";
	}

	/**
	 * 调用Hystrix保护的UserService从远端资源获取用户信息， 异常由HystrixExceptionHandler统一处理.
	 */
	@RequestMapping(value = "/hystrix/user/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
	@ResponseBody
	public UserDTO getUser(@PathVariable("id") Long id) throws Exception {
		return userService.getUser(id);
	}

	/**
	 * 设定资源的状态.
	 */
	@RequestMapping(value = "/hystrix/status")
	public String updateStatus(@RequestParam("value") String newStatus) {
		DependencyResourceController.status = newStatus;
		return "redirect:/hystrix";
	}

	/**
	 * 从默认的Hystrix线程池模式切换为使用原调用者线程的模式.
	 */
	@RequestMapping(value = "/hystrix/disableIsolateThreadPool")
	public String disableIsolateThreadPool() {
		userService.setIsolateThreadPool(false);
		userService.init();
		return "redirect:/hystrix";

	}

	@PostConstruct
	public void init() {
		allStatus.put("normal", "正常");
		allStatus.put("timeout", "超时");
		allStatus.put("server-error", "服务器错误");
		allStatus.put("bad-request", "请求错误");
	}

	/**
	 * 给Spring Form Tag使用的类.
	 */
	public static class StatusHolder {
		private String value;

		public StatusHolder(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}
}
