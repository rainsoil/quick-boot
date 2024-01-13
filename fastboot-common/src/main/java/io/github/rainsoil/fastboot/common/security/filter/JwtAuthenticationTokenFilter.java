package io.github.rainsoil.fastboot.common.security.filter;

import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastboot.common.security.token.TokenManage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤
 *
 * @author luyanan
 * @since 2023/11/28
 **/
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private TokenManage tokenManage;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {
			String userDetails = tokenManage.getUserDetails(request);

			if (null != userDetails && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails details = JSON.parseObject(userDetails, UserDetails.class);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, details.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.debug(String.format("Authenticated userDetail %s, setting security context", details.getUsername()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
//            e.printStackTrace();
			throw e;
		}
		filterChain.doFilter(request, response);
	}
}
