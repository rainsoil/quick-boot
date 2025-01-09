package com.su60.quickboot.security.token;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;


/**
 * token管理器
 *
 * @author luyanan
 * @since 2024/04/16
 **/
public interface TokenManage {


	/**
	 * 创建token
	 *
	 * @param userDetails 用户信息
	 * @return token
	 * @since 2024/04/16
	 */
	AccessToken createToken(UserDetails userDetails);


	/**
	 * 从token解析出用户信息
	 *
	 * @param token token
	 * @return 用户信息
	 * @since 2024/04/16
	 */
	String getUserDetails(String token);


	/**
	 * 从请求头中解析token
	 *
	 * @param request 请求头
	 * @return 用户信息
	 * @since 2024/04/17
	 */
	String getUserDetails(HttpServletRequest request);

}
