package io.github.rainsoil.fastapi2.common.security.config;

import io.github.rainsoil.fastapi2.common.security.SmPasswordEncoder;
import io.github.rainsoil.fastapi2.common.security.filter.JwtAuthenticationTokenFilter;
import io.github.rainsoil.fastapi2.common.security.handler.JwtAuthenticationEntryPoint;
import io.github.rainsoil.fastapi2.common.security.handler.LoginFailureHandler;
import io.github.rainsoil.fastapi2.common.security.handler.LoginSuccessHandler;
import io.github.rainsoil.fastapi2.common.security.handler.RestAuthenticationAccessDeniedHandler;
import io.github.rainsoil.fastapi2.common.security.token.SimpleJwtTokenManage;
import io.github.rainsoil.fastapi2.common.security.token.TokenManage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 权限校验自动配置类
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@Configuration
@EnableConfigurationProperties(SpringSecurityProperties.class)
@Import(WebSecurityConfig.class)
public class SpringSecurityAutoConfiguration {
	/**
	 * 装载BCrypt密码编码器
	 *
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new SmPasswordEncoder();
	}
	/**
	 * token管理
	 *
	 * @return
	 * @since 2023/11/29
	 */
	@Bean
	public TokenManage tokenManage() {
		return new SimpleJwtTokenManage();
	}


	/**
	 * 授权失败
	 *
	 * @return
	 * @since 2023/11/29
	 */
	@Bean
	public JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

	/**
	 * 认证失败
	 *
	 * @return
	 * @since 2023/11/29
	 */
	@Bean("RestAuthenticationAccessDeniedHandler")
	public RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler() {
		return new RestAuthenticationAccessDeniedHandler();
	}

	/**
	 * token过滤
	 *
	 * @since 2023/11/29
	 */

	@Bean
	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter();
	}

	/**
	 * 登录成功
	 *
	 * @since 2023/11/29
	 */

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler();
	}

	/**
	 * 登录失败
	 *
	 * @since 2023/11/29
	 */

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler();
	}

}
