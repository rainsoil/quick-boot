package com.su60.quickboot.security.provider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;


/**
 * 配置处理器
 *
 * @author luyanan
 * @since 2024/04/20
 **/
public interface SpringSecurityHandler {


	/**
	 * 认证失败
	 *
	 * @param request       请求头
	 * @param response      响应头
	 * @param authException 授权异常
	 * @return
	 * @since 2024/04/20
	 */
	void jwtAuthenticationEntryPoint(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException);


	/**
	 * 权限不足
	 *
	 * @param request  请求头
	 * @param response 响应头
	 * @param e        异常
	 * @return
	 * @since 2024/04/20
	 */
	void accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e);


	/**
	 * 登录成功的回调
	 *
	 * @param request        请求头
	 * @param response       响应头
	 * @param authentication 授权信息
	 * @return
	 * @since 2024/04/20
	 */
	void authenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication);


	/**
	 * 登录失败的回调
	 *
	 * @param request   请求头
	 * @param response  响应头
	 * @param exception 授权异常
	 * @return
	 * @since 2024/04/20
	 */
	void authenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception);


	/**
	 * 退出
	 *
	 * @param request        请求头
	 * @param response       响应头
	 * @param authentication 授权信息
	 * @return
	 * @since 2024/04/20
	 */
	void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication);
}
