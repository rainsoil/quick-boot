package com.su60.example;

import com.su60.quickboot.security.utils.SpringSecurityUtils;
import jakarta.annotation.security.PermitAll;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @author luyanan
 * @since 2024/04/19
 **/
@RestController
@RequestMapping("test")
public class TestController {


	/**
	 * 公开访问
	 *
	 * @return
	 * @since 2024/05/30
	 */
	@PermitAll
	@GetMapping("publicV")
	public String publicV() {
		return "publicV";
	}

	/**
	 * 需要登录才能访问
	 *
	 * @return
	 * @since 2024/05/30
	 */
	@GetMapping("privateV")
	public String privateV() {
		return "privateV";
	}

	/**
	 * 个人详情
	 *
	 * @return
	 * @since 2024/05/30
	 */
	@GetMapping("info")
	public LoginUser info() {
		return SpringSecurityUtils.getLoginUser(LoginUser.class);
	}


}
