package io.github.rainsoil.fastboot.system.controller;

import io.github.rainsoil.fastboot.common.security.code.ValidateCodeService;
import io.github.rainsoil.fastboot.common.spring.restful.annotation.NoRestFul;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录
 *
 * @author luyanan
 * @since 2024/01/13
 **/
@RequiredArgsConstructor
@Controller
public class LoginController {


	private final ValidateCodeService validateCodeService;

	/**
	 * 获取验证码
	 *
	 * @param uuid uuid
	 * @return
	 * @since 2024/01/13
	 */
	@NoRestFul
	@ApiOperation(value = "获取验证码")
	@GetMapping("getCode")
	public void getCode(String uuid, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");
		validateCodeService.createCode(uuid, response);

	}
}
