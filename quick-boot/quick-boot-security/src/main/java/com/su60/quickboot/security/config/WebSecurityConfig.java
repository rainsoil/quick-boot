//package com.su60.quickboot.security.config;
//
//import com.su60.quickboot.security.token.JwtAuthenticationTokenFilter;
//import jakarta.annotation.Resource;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.access.ConfigAttribute;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.authorization.AuthorizationDecision;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.util.PathMatcher;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//
///**
// * 安全配置
// *
// * @author luyanan
// * @since 2024/04/17
// **/
//@EnableWebSecurity
//@Slf4j
//@RequiredArgsConstructor
//public class WebSecurityConfig {
//	@Resource
//	private UserDetailsService userDetailsService;
//	@Resource
//	private JwtAuthenticationTokenFilter myAuthenticationFilter;
//
//	/**
//	 * 鉴权管理类
//	 */
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		return config.getAuthenticationManager();
//	}
//
//	public static final String[] WHITELIST = {"/login", "/test/publicV"};
//
//	/**
//	 * 加密类
//	 */
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	/**
//	 * Spring Security 过滤链
//	 */
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		return http
//				// 禁用明文验证
//				.httpBasic(AbstractHttpConfigurer::disable)
//				// 关闭csrf
//				.csrf(AbstractHttpConfigurer::disable)
//				// 禁用默认登录页
//				.formLogin(AbstractHttpConfigurer::disable)
//				// 禁用默认登出页
//				.logout(AbstractHttpConfigurer::disable)
//				// 禁用session
//				.sessionManagement(session -> session
//						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				// 配置拦截信息
//				.authorizeHttpRequests(authorization -> authorization
//								// 允许所有的OPTIONS请求
//								.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//								// 放行白名单
//								.requestMatchers(WHITELIST).permitAll()
//								// 根据权限进行放行
////						.anyRequest().access((authentication, object) -> {
////							// 获取当前的访问路径
////							String requestURI = object.getRequest().getRequestURI();
////							PathMatcher pathMatcher = new AntPathMatcher();
////							// 白名单请求直接放行
////							for (String url : WHITELIST) {
////								if (pathMatcher.match(url, requestURI)) {
////									return new AuthorizationDecision(true);
////								}
////							}
//////							// 获取访问该路径所需权限
//////							Map<String, ConfigAttribute> permissionMap = SecurityConstant.PERMISSION_MAP;
//////							List<ConfigAttribute> apiNeedPermissions = new ArrayList<>();
//////							for (Map.Entry<String, ConfigAttribute> config : permissionMap.entrySet()) {
//////								if (pathMatcher.match(config.getKey(), requestURI)) {
//////									apiNeedPermissions.add(config.getValue());
//////								}
//////							}
//////							// 如果接口没有配置权限则直接放行
//////							if (apiNeedPermissions.isEmpty()) {
//////								return new AuthorizationDecision(true);
//////							}
////							// 获取当前登录用户权限信息
////							Collection<? extends GrantedAuthority> authorities = authentication.get().getAuthorities();
////							// 判断当前用户是否有足够的权限访问
////							for (ConfigAttribute configAttribute : apiNeedPermissions) {
////								// 将访问所需资源和用户拥有资源进行比对
////								String needAuthority = configAttribute.getAttribute();
////								for (GrantedAuthority grantedAuthority : authorities) {
////									if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
////										// 权限匹配放行
////										return new AuthorizationDecision(true);
////									}
////								}
////							}
////							return new AuthorizationDecision(false);
////						})
//								.anyRequest().authenticated()
//				)
//				// 注册重写后的UserDetailsService实现
//				.userDetailsService(userDetailsService)
//				// 注册自定义拦截器
//				.addFilterBefore(myAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//				.build();
//	}
//}