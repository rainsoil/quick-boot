package com.su60.quickboot.security.config;

import com.su60.quickboot.security.captcha.CaptchaController;
import com.su60.quickboot.security.captcha.CaptchaService;
import com.su60.quickboot.security.captcha.CaptchaServiceImpl;
import com.su60.quickboot.security.captcha.LoginFilter;
import com.su60.quickboot.security.client.OauthClientService;
import com.su60.quickboot.security.provider.SpringSecurityHandler;
import com.su60.quickboot.security.token.JwtAuthenticationTokenFilter;
import com.su60.quickboot.security.token.JwtSpringSecurityHandler;
import com.su60.quickboot.security.token.JwtTokenManager;
import com.su60.quickboot.security.token.TokenManage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 安全配置
 *
 * @author luyanan
 * @since 2024/04/19
 **/
@Configuration
@Import({WebSecurityConfig2.class, CaptchaController.class})
@EnableConfigurationProperties(SpringSecurityProperties.class)
@ConditionalOnProperty(prefix = SpringSecurityProperties.PREFIX, value = "enable", havingValue = "true", matchIfMissing = true)
public class SpringSecurityConfiguration {


	/**
	 * token管理器
	 *
	 * @return token管理器
	 * @since 2024/04/20
	 */
	@Bean
	@ConditionalOnProperty(prefix = SpringSecurityProperties.PREFIX + ".token",
			value = "enable", havingValue = "true", matchIfMissing = true)
	public TokenManage tokenManage() {
		return new JwtTokenManager();
	}

	/**
	 * spring security处理器
	 *
	 * @param tokenManage token管理器
	 * @return spring security处理器
	 * @since 2024/04/20
	 */
	@Bean
	@ConditionalOnProperty(prefix = SpringSecurityProperties.PREFIX + ".token",
			value = "enable", havingValue = "true", matchIfMissing = true)
	public SpringSecurityHandler springSecurityHandler(TokenManage tokenManage) {
		return new JwtSpringSecurityHandler(tokenManage);
	}

	/**
	 * token过滤器
	 *
	 * @return token过滤器
	 * @since 2024/04/20
	 */
	@Bean
	@ConditionalOnProperty(prefix = SpringSecurityProperties.PREFIX + ".token",
			value = "enable", havingValue = "true", matchIfMissing = true)
	public JwtAuthenticationTokenFilter authenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	/**
	 * 登录过滤器
	 *
	 * @param captchaService     验证码service
	 * @param oauthClientService 授权客户端
	 * @return 登录过滤器
	 * @since 2024/07/17
	 */
	@Bean
	public LoginFilter captchaFilter(CaptchaService captchaService, OauthClientService oauthClientService) {
		return new LoginFilter(captchaService, oauthClientService);
	}


	/**
	 * 授权客户端
	 *
	 * @param securityProperties 配置文件
	 * @return 授权客户端
	 * @since 2024/07/19
	 */
	@Bean
	public OauthClientService oauthClientService(SpringSecurityProperties securityProperties) {
		return new OauthClientService(securityProperties);
	}

	/**
	 * 验证码处理器
	 *
	 * @param securityProperties 配置
	 * @param cacheManager       缓存
	 * @return 验证码处理器
	 * @since 2024/07/17
	 */
	@Bean
	public CaptchaService captchaService(SpringSecurityProperties securityProperties, CacheManager cacheManager) {
		return new CaptchaServiceImpl(securityProperties, cacheManager);
	}
}
