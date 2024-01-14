package io.github.rainsoil.fastboot.common.security.code;

import io.github.rainsoil.fastboot.common.security.config.SpringSecurityProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 验证码的配置
 *
 * @author luyanan
 * @since 2024/01/13
 **/
@Configuration
public class ValidateCodeConfiguration {

	/**
	 * 验证码校验service
	 *
	 * @param cacheManager       缓存
	 * @param securityProperties 配置
	 * @return
	 * @since 2024/01/13
	 */
	@Bean
	public ValidateCodeService validateCodeService(CacheManager cacheManager, SpringSecurityProperties securityProperties) {
		return new ValidateCodeServiceImpl(cacheManager, securityProperties);
	}

	/**
	 * 验证码过滤器
	 *
	 * @param failureHandler      失败处理器
	 * @param securityProperties  配置
	 * @param validateCodeService 验证码校验
	 * @return
	 * @since 2024/01/13
	 */
	@Bean
	public ValidateCodeFilter validateCodeFilter(AuthenticationFailureHandler failureHandler,
												 SpringSecurityProperties securityProperties,
												 ValidateCodeService validateCodeService) {
		return new ValidateCodeFilter(failureHandler, securityProperties, validateCodeService);
	}
}
