package com.su60.quickboot.security.token;

import com.su60.quickboot.common.core.R;
import com.su60.quickboot.common.exception.GlobalMsgCode;
import com.su60.quickboot.common.utils.ServletUtil;
import com.su60.quickboot.security.event.SecurityEvent;
import com.su60.quickboot.security.event.SecurityEventDo;
import com.su60.quickboot.security.provider.SpringSecurityHandler;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * 基于jwt  token的处理器
 *
 * @author luyanan
 * @since 2024/04/20
 **/
@Slf4j
@RequiredArgsConstructor
public class JwtSpringSecurityHandler implements SpringSecurityHandler, Serializable {

	private final TokenManage tokenManage;
	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void jwtAuthenticationEntryPoint(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
		log.debug("认证失败 url:{},失败信息:{}", request.getRequestURI(), authException.getMessage());
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.write(JSON.toJSONString(R.failed(GlobalMsgCode.UNAUTHORIZED, authException.getMessage())));
			printWriter.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
		log.debug("{}访问权限不足:{}", request.getRequestURI(), e.getMessage());
		response.setStatus(200);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter printWriter = null;
		try {
			printWriter = response.getWriter();
			printWriter.write(JSON.toJSONString(R.failed(GlobalMsgCode.FORBIDDEN, e.getMessage())));
			printWriter.flush();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public void authenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		AccessToken accessToken = tokenManage.createToken(userDetails);
		log.debug("{}登录成功,生成的token:{}", userDetails.getUsername(), accessToken);
		ServletUtil.write(response, JSON.toJSONString(R.ok(accessToken)), "application/json;charset=utf-8");

		// 发布事件
		SecurityEventDo springEventDo = new SecurityEventDo();
		springEventDo.setUsername(userDetails.getUsername());
		springEventDo.setLoginTime(System.currentTimeMillis());
		springEventDo.setIpaddr(ServletUtil.getClientIP(request));
		springEventDo.setUserAgent(request.getHeader("User-Agent"));
		applicationContext.publishEvent(new SecurityEvent(springEventDo));
	}

	@Override
	public void authenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
		String msg = "用户名或密码错误";
		log.debug("登录失败了");
		ServletUtil.write(response, JSON.toJSONString(R.failed(msg)), "application/json;charset=utf-8");
	}

	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		UserDetails userDetails = JSON.parseObject(authentication.getPrincipal().toString(), UserDetails.class);
		log.debug("{}退出成功", userDetails.getUsername());
		ServletUtil.write(response, JSON.toJSONString(R.ok("退出成功")), "application/json;charset=utf-8");


	}
}
