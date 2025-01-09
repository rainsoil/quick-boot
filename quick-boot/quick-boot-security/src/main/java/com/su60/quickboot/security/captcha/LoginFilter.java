package com.su60.quickboot.security.captcha;

import com.su60.quickboot.common.core.R;
import com.su60.quickboot.common.utils.ServletUtil;
import com.su60.quickboot.security.client.OauthClient;
import com.su60.quickboot.security.client.OauthClientService;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * 登录校验过滤器
 *
 * @author luyanan
 * @since 2024/07/17
 **/

@RequiredArgsConstructor
@Slf4j
public class LoginFilter extends OncePerRequestFilter {


	/**
	 * 验证码的key的参数名
	 *
	 * @since 2024/07/17
	 */
	private static final String CAPTCHA_KEY_PARAM = "uuid";

	/**
	 * 验证码的code 的参数名
	 *
	 * @since 2024/07/17
	 */

	private static final String CAPTCHA_CODE_PARAM = "code";
	private static final AntPathRequestMatcher LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");


	private final CaptchaService captchaService;

	private final OauthClientService oauthClientService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


		if (LOGIN_PATH_REQUEST_MATCHER.matches(request)) {

			String key = request.getParameter(CAPTCHA_KEY_PARAM);
			String code = request.getParameter(CAPTCHA_CODE_PARAM);
			OauthClient client = oauthClientService.getClient(request);
			if (null == client) {
				R r = R.failed(500, "客户端id不能为空");
				ServletUtil.write(response, JSON.toJSONString(r), "application/json;charset=utf-8");
				return;
			}
			if (client.getVerificationCode()) {

				boolean check = captchaService.check(key, code);
				if (!check) {
					R r = R.failed(500, "验证码不正确");
					ServletUtil.write(response, JSON.toJSONString(r), "application/json;charset=utf-8");
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
