package com.su60.quickboot.security.token;

import com.su60.quickboot.security.user.UserDetails;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * token过滤器
 *
 * @author luyanan
 * @since 2024/04/20
 **/
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenManage tokenManage;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		log.info("JwtAuthenticationTokenFilter");
		String userDetails = tokenManage.getUserDetails(request);
		if (null != userDetails) {
			UserDetails details = JSON.parseObject(userDetails, UserDetails.class);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, details.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//			logger.debug(String.format("Authenticated userDetail %s, setting security context", details.getUsername()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);

	}
}
