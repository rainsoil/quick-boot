package io.github.rainsoil.fastboot.common.security.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastboot.common.security.token.AccessToken;
import io.github.rainsoil.fastboot.common.security.token.TokenManage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录成功的处理
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private TokenManage tokenManage;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		Object principal = authentication.getPrincipal();
		UserDetails userDetails = (UserDetails) principal;

		AccessToken token = tokenManage.createToken(userDetails);
		log.debug("{}登录成功,生成的token:{}", userDetails.getUsername());
		ServletUtil.write(response, JSON.toJSONString(token), "application/json;charset=utf-8");
	}
}
