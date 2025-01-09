package com.su60.quickboot.security.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.security.captcha.LoginFilter;
import com.su60.quickboot.security.enuns.RequestMethodEnum;
import com.su60.quickboot.security.provider.SpringSecurityHandler;
import com.su60.quickboot.security.token.JwtAuthenticationTokenFilter;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.*;

/**
 * 安全配置
 *
 * @author luyanan
 * @since 2024/04/17
 **/
@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig2 {

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SpringSecurityProperties securityProperties;

	@Autowired(required = false)
	private SpringSecurityHandler springSecurityHandler;

	@Autowired(required = false)
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;


	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoginFilter loginFilter;


	/**
	 * 密码编码器
	 *
	 * @return 密码编码器
	 * @since 2024/07/07
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	/**
	 * authManager
	 *
	 * @return authManager
	 * @since 2024/07/07
	 */
	@Bean
	public AuthenticationManager authManager() {

		var authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
//		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}

	/**
	 * filterChain
	 *
	 * @param httpSecurity httpSecurity
	 * @return filterChain
	 * @since 2024/06/29
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

//
//		String[] paths = {
//				"/login",
//				"/test/publicV"
//		};
//		return httpSecurity.csrf(csrf -> {
//					csrf.disable();
//				}).cors(cors -> cors.disable())
//				.authorizeHttpRequests(auth -> {
//					auth.requestMatchers("/login").permitAll();
//					auth.requestMatchers("/test/publicV").permitAll();
//					auth.anyRequest().authenticated();
//				}).sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).build();
//		return httpSecurity.authorizeHttpRequests(conf -> conf.requestMatchers(paths).permitAll()
//						.anyRequest().authenticated())
//				.formLogin(conf -> conf.loginProcessingUrl("/login"))
//				.csrf(AbstractHttpConfigurer::disable)
//				.sessionManagement(conf -> conf.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
		RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
		Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = requestMappingHandlerMapping.getHandlerMethods();
		Map<String, Set<String>> anonymousUrls = getAnonymousUrl(handlerMethodMap);
		if (null != springSecurityHandler) {
			httpSecurity.exceptionHandling(ex -> {
				ex.accessDeniedHandler(new AccessDeniedHandler() {
					@Override
					public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
						springSecurityHandler.accessDeniedHandler(request, response, accessDeniedException);
					}
				}).authenticationEntryPoint(new AuthenticationEntryPoint() {
					@Override
					public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
						springSecurityHandler.jwtAuthenticationEntryPoint(request, response, authException);
					}
				});
			});
		}
		SessionCreationPolicy sessionCreationPolicy = SessionCreationPolicy.ALWAYS;
		if (null != securityProperties.getToken() && null != securityProperties.getToken().getEnable() && securityProperties.getToken().getEnable()) {
			sessionCreationPolicy = SessionCreationPolicy.NEVER;
		}
//
		httpSecurity.cors(cors -> {
			cors.configurationSource(corsConfigurationSource());
		}).csrf(csrf -> csrf.disable()).sessionManagement(sessionManagement -> sessionManagement.disable()).authorizeRequests(author -> {

			author.requestMatchers(HttpMethod.GET, "/*.html", "/*/*.html", "/*/*.css", "/*/*.js", "/webSocket/**").permitAll().requestMatchers("/login").permitAll()



					// 自定义匿名访问所有url放行：允许匿名和带Token访问，细腻化到每个 Request 类型
					// GET
					.requestMatchers(HttpMethod.GET, anonymousUrls.get(RequestMethodEnum.GET.getType()).toArray(new String[0])).permitAll()
					// POST
					.requestMatchers(HttpMethod.POST, anonymousUrls.get(RequestMethodEnum.POST.getType()).toArray(new String[0])).anonymous()
					// PUT
					.requestMatchers(HttpMethod.PUT, anonymousUrls.get(RequestMethodEnum.PUT.getType()).toArray(new String[0])).anonymous()
					// PATCH
					.requestMatchers(HttpMethod.PATCH, anonymousUrls.get(RequestMethodEnum.PATCH.getType()).toArray(new String[0])).anonymous()
					// DELETE
					.requestMatchers(HttpMethod.DELETE, anonymousUrls.get(RequestMethodEnum.DELETE.getType()).toArray(new String[0])).anonymous()
					// 所有类型的接口都放行
					.requestMatchers(anonymousUrls.get(RequestMethodEnum.ALL.getType()).toArray(new String[0])).permitAll()
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

					.anyRequest().authenticated();

			//静态资源

		});
		if (null != springSecurityHandler) {
			httpSecurity.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
				@Override
				public void customize(FormLoginConfigurer<HttpSecurity> httpSecurityFormLoginConfigurer) {
					httpSecurityFormLoginConfigurer.successHandler(new AuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request,
															HttpServletResponse response,
															Authentication authentication) throws IOException, ServletException {
							springSecurityHandler.authenticationSuccess(request, response, authentication);
						}
					}).failureHandler(new AuthenticationFailureHandler() {
						@Override
						public void onAuthenticationFailure(HttpServletRequest request,
															HttpServletResponse response,
															AuthenticationException exception) throws IOException, ServletException {
							springSecurityHandler.authenticationFailure(request, response, exception);
						}
					});
				}
			}).logout(new Customizer<LogoutConfigurer<HttpSecurity>>() {
				@Override
				public void customize(LogoutConfigurer<HttpSecurity> httpSecurityLogoutConfigurer) {
					httpSecurityLogoutConfigurer.logoutSuccessHandler(new LogoutSuccessHandler() {
						@Override
						public void onLogoutSuccess(HttpServletRequest request,
													HttpServletResponse response,
													Authentication authentication) throws IOException, ServletException {
							springSecurityHandler.logout(request, response, authentication);
						}
					});
				}
			});
		}

		httpSecurity.addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class);

		if (null != jwtAuthenticationTokenFilter) {
//			httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
			httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, LogoutFilter.class);
		}
//
		return httpSecurity.build();

	}
//

	/**
	 * 获取可以匿名登录的接口
	 *
	 * @param handlerMethodMap handlerMethodMap
	 * @return 匿名登录的接口
	 * @since 2024/04/19
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
			PermitAll anonymousAccess = handlerMethod.getMethodAnnotation(PermitAll.class);
			if (null != anonymousAccess) {
				List<RequestMethod> requestMethods = new ArrayList<>(infoEntry.getKey().getMethodsCondition().getMethods());
				RequestMethodEnum request = RequestMethodEnum.find(requestMethods.size() == 0 ? RequestMethodEnum.ALL.getType() : requestMethods.get(0).name());
				switch (Objects.requireNonNull(request)) {
					case GET:
						get.addAll(infoEntry.getKey().getPatternValues());
						break;
					case POST:
						post.addAll(infoEntry.getKey().getPatternValues());
						break;
					case PUT:
						put.addAll(infoEntry.getKey().getPatternValues());
						break;
					case PATCH:
						patch.addAll(infoEntry.getKey().getPatternValues());
						break;
					case DELETE:
						delete.addAll(infoEntry.getKey().getPatternValues());
						break;
					default:
						all.addAll(infoEntry.getKey().getPatternValues());
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
	 * cors配置
	 *
	 * @return cors配置
	 * @since 2024/07/07
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedMethods(Arrays.asList("*"));
//		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedOriginPatterns(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("*"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // 路径模式
		return source;
	}
}
