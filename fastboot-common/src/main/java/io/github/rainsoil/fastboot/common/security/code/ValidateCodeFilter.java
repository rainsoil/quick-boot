package io.github.rainsoil.fastboot.common.security.code;

import io.github.rainsoil.fastboot.common.security.config.SpringSecurityProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 验证码过滤器
 *
 * @author luyanan
 * @since 2024/01/13
 **/
@Slf4j
@RequiredArgsConstructor
public class ValidateCodeFilter extends OncePerRequestFilter  {

	/**
	 * 失败处理器
	 *
	 * @since 2024/01/13
	 */

	private final AuthenticationFailureHandler authenticationFailureHandler;


	/**
	 * 安全配置
	 *
	 * @since 2024/01/13
	 */

	private final SpringSecurityProperties securityProperties;

	private final ValidateCodeService validateCodeService;

	/**
	 * spring的一个工具类：用来判断 两字符串 是否匹配
	 *
	 * @author luyanan
	 * @since 2024/01/13
	 */
	private AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();

//		SpringSecurityProperties.ValidateCode validateCode = securityProperties.getValidatecode();
//		String matchUrl = validateCode.getMatchurl();
//		if (StrUtil.isNotBlank(matchUrl)) {
//			urls.addAll(Arrays.stream(matchUrl.split(",")).collect(Collectors.toList()));
//		}

	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		boolean flg = false;
		SpringSecurityProperties.ValidateCode validateCode = securityProperties.getValidatecode();
		List<String> urls = Arrays.stream(validateCode.getMatchurl().split(",")).distinct().collect(Collectors.toList());

		for (String url : urls) {
			if (pathMatcher.match(url, request.getRequestURI())) {
				flg = true;
			}
		}

		if (flg) {

			String code = request.getParameter("code");
			String uuid = request.getParameter("uuid");
			try {
				validateCodeService.match(code, uuid);
			} catch (ValidateCodeException e) {
				//返回错误信息给 失败处理器
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}

		}
		filterChain.doFilter(request, response);
	}


}
