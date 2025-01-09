package com.su60.quickboot.security.utils;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 工具类
 *
 * @author luyanan
 * @since 2024/04/20
 **/
@UtilityClass
public class SpringSecurityUtils {

	/**
	 * 获取登录的用户
	 *
	 * @param userClazz 登录用户class类
	 * @param <T>       登录用户泛型
	 * @return 登录的用户信息
	 * @since 2024/04/20
	 */
	public <T> T getLoginUser(Class<? extends UserDetails> userClazz) {

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return (T) JSON.parseObject(principal.toString(), userClazz);
	}
}
