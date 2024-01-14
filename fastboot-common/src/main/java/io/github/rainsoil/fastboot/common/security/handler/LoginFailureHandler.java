package io.github.rainsoil.fastboot.common.security.handler;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastboot.common.core.R;
import io.github.rainsoil.fastboot.common.security.code.ValidateCodeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败处理类
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		String msg = "用户名或密码错误";
		if (exception instanceof ValidateCodeException) {
			msg = exception.getLocalizedMessage();
		}
		log.debug("登录失败了");
		ServletUtil.write(response, JSON.toJSONString(R.failed(msg)), "application/json;charset=utf-8");
	}
}
