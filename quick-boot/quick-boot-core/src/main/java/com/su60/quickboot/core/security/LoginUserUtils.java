package com.su60.quickboot.core.security;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 当前登陆人
 *
 * @author luyanan
 * @since 2024/07/07
 **/
@UtilityClass
public class LoginUserUtils {


	/**
	 * 获取当前登录的用户信息
	 *
	 * @return 当前登录的用户信息
	 * @since 2024/07/07
	 */
	public LoginUser getUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (null == context) {
			return null;
		}
		Authentication authentication = context.getAuthentication();
		if (null == authentication) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal instanceof String) {
			String string = principal.toString();
			if (string.equals("anonymousUser")) {
				return null;
			}
			return JSON.parseObject(principal.toString(), LoginUser.class);
		}
		if (principal instanceof LoginUser) {
			return (LoginUser) principal;
		}
		return null;
	}

	/**
	 * 是否为admin
	 *
	 * @param userId 用户id
	 * @return 当前用户是否为admin
	 * @since 2024/8/7
	 */

	public boolean isAdmin(Long userId) {
		return null != userId && 1L == userId;
	}
}
