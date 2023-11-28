package io.github.rainsoil.fastapi2.common.security.filter;

import io.github.rainsoil.fastapi2.common.security.token.TokenManage;
import lombok.RequiredArgsConstructor;
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


    private TokenManage tokenManage;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            UserDetails userDetails = tokenManage.getUserDetails(request);
            if (null != userDetails && SecurityContextHolder.getContext().getAuthentication() == null) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.debug(String.format("Authenticated userDetail %s, setting security context", userDetails.getUsername()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        filterChain.doFilter(request, response);
    }
}
