package io.github.rainsoil.fastboot.common.security.config;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastboot.common.core.R;
import io.github.rainsoil.fastboot.common.security.RequestMethodEnum;
import io.github.rainsoil.fastboot.common.security.annotation.IgnoreAuth;
import io.github.rainsoil.fastboot.common.security.code.ValidateCodeFilter;
import io.github.rainsoil.fastboot.common.security.filter.JwtAuthenticationTokenFilter;
import io.github.rainsoil.fastboot.common.security.handler.JwtAuthenticationEntryPoint;
import io.github.rainsoil.fastboot.common.security.handler.LoginFailureHandler;
import io.github.rainsoil.fastboot.common.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 安全控制模块
 *
 * @author luyanan
 * @since 2023/11/28
 **/
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtAuthenticationEntryPoint unauthorizedHandler;

	private final AccessDeniedHandler accessDeniedHandler;

	private final UserDetailsService customUserDetailsService;

	private final JwtAuthenticationTokenFilter authenticationTokenFilter;
	private final ApplicationContext applicationContext;


	private final LoginSuccessHandler loginSuccessHandler;

	private final LoginFailureHandler loginFailureHandler;
	private final SpringSecurityProperties securityProperties;

	private final PasswordEncoder passwordEncoder;

	private final ValidateCodeFilter validateCodeFilter;


	/**
	 * 配置
	 *
	 * @param authenticationManagerBuilder authenticationManagerBuilder
	 * @return
	 * @since 2023/11/29
	 */
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				// 设置UserDetailsService
				.userDetailsService(this.customUserDetailsService)
				// 使用BCrypt进行密码的hash
				.passwordEncoder(passwordEncoder);
	}


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
		Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
		httpSecurity
				// 禁用csrf
				.csrf().disable()
				.addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
				.exceptionHandling()
				.authenticationEntryPoint(unauthorizedHandler)
				.accessDeniedHandler(accessDeniedHandler)

				// 防止iframe 造成跨域
				.and()
				.headers()
				.frameOptions().disable()
				.and()
				// 不创建会话
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().authorizeRequests()

				//静态资源
				.antMatchers(
						HttpMethod.GET,
						"/*.html",
						"/**/*.html",
						"/**/*.css",
						"/**/*.js",
						"/webSocket/**"
				).permitAll()
				// swagger 文档
				.antMatchers("/swagger-ui.html").permitAll()
				.antMatchers("/swagger-resources/**").permitAll()
				.antMatchers("/webjars/**").permitAll()
				.antMatchers("/*/api-docs").permitAll()
				// 文件
				.antMatchers("/avatar/**").permitAll()
				.antMatchers("/file/**").permitAll()
				// 阿里巴巴 druid
				.antMatchers("/druid/**").permitAll()
				.antMatchers("/login").permitAll()
				.antMatchers("/serverless.function/invoke").permitAll()
				// 放行OPTIONS请求
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				// 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
				// GET
				.antMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).anonymous()
				// POST
				.antMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).anonymous()
				// PUT
				.antMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).anonymous()
				// PATCH
				.antMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).anonymous()
				// DELETE
				.antMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).anonymous()
				// 所有类型的接口都放行
				.antMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).anonymous()
				// 所有请求都需要认证
				.anyRequest().authenticated()
				.and()

				.formLogin().successHandler(loginSuccessHandler)
				.failureHandler(loginFailureHandler)
				.loginProcessingUrl("/login")
				.loginPage("/login")
				.and().logout()
				.addLogoutHandler(new LogoutHandler() {
					@Override
					public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
						ServletUtil.write(response, JSON.toJSONString(R.ok("退出成功")), "application/json;charset=utf-8");

					}
				}).and()
				.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
				.cors();
	}

	/**
	 * 获取需要匿名的url
	 *
	 * @param handlerMethodMap handlerMethodMap
	 * @return
	 * @since 2023/11/29
	 */
	private Map<String, Set<String>> getAnonymousUrl(Map<RequestMappingInfo, HandlerMethod> handlerMethodMap) {
		Map<String, Set<String>> anonymousUrls = new HashMap<>(8);
		Set<String> get = new HashSet<>();
		Set<String> post = new HashSet<>();
		Set<String> put = new HashSet<>();
		Set<String> patch = new HashSet<>();
		Set<String> delete = new HashSet<>();
		Set<String> all = new HashSet<>();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
			HandlerMethod handlerMethod = infoEntry.getValue();
			IgnoreAuth anonymousAccess = handlerMethod.getMethodAnnotation(IgnoreAuth.class);
			if (null != anonymousAccess) {
				List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
				RequestMethodEnum request = RequestMethodEnum.find(requestMethods.size() == 0 ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
				switch (Objects.requireNonNull(request)) {
					case GET:
						get.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
						break;
					case POST:
						post.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
						break;
					case PUT:
						put.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
						break;
					case PATCH:
						patch.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
						break;
					case DELETE:
						delete.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
						break;
					default:
						all.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
						break;
				}
			}
		}
		for (String ignoreUrl : securityProperties.getIgnoreUrls()) {
			all.add(ignoreUrl);
		}
		anonymousUrls.put(RequestMethodEnum.GET.getType(), get);
		anonymousUrls.put(RequestMethodEnum.POST.getType(), post);
		anonymousUrls.put(RequestMethodEnum.PUT.getType(), put);
		anonymousUrls.put(RequestMethodEnum.PATCH.getType(), patch);
		anonymousUrls.put(RequestMethodEnum.DELETE.getType(), delete);
		anonymousUrls.put(RequestMethodEnum.ALL.getType(), all);
		return anonymousUrls;
	}


	/**
	 * cors过滤
	 *
	 * @return
	 * @since 2023/11/29
	 */
	@Bean
	public CorsFilter corsFilter() {
		//1. 添加 CORS配置信息
		CorsConfiguration config = new CorsConfiguration();
		//放行哪些原始域
//		config.addAllowedOrigin("*");
		config.addAllowedOriginPattern("*");
		//是否发送 Cookie
		config.setAllowCredentials(true);
		//放行哪些请求方式
		config.addAllowedMethod("*");
		//放行哪些原始请求头部信息
		config.addAllowedHeader("*");
		//暴露哪些头部信息
//		config.addExposedHeader("*");
		config.setMaxAge(18000L);
		//2. 添加映射路径
		UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
		corsConfigurationSource.registerCorsConfiguration("/**", config);
		//3. 返回新的CorsFilter
		log.error("开启cors");
		return new CorsFilter(corsConfigurationSource);
	}
}
