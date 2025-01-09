package com.su60.quickboot.security.token;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.common.utils.ServletUtil;
import com.su60.quickboot.security.config.SpringSecurityProperties;
import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

/**
 * 抽象类
 *
 * @author luyanan
 * @since 2024/04/17
 **/
public class AbstractTokenManage implements TokenManage {

	@Autowired
	private SpringSecurityProperties securityProperties;

	@Override
	public AccessToken createToken(UserDetails userDetails) {

		SpringSecurityProperties.TokenProperties tokenProperties = securityProperties.getToken();

		String token = Jwts.builder().setSubject(userDetails.getUsername())
				.claim("user", JSON.toJSONString(userDetails))
				.setExpiration(new Date(System.currentTimeMillis() + tokenProperties.getExpireTime()))
				.signWith(SignatureAlgorithm.HS512, tokenProperties.getSignKey())
				.compressWith(CompressionCodecs.GZIP).compact();


		AccessToken accessToken = new AccessToken();
		accessToken.setAccessToken(token);
		accessToken.setTokenType(tokenProperties.getTokenType());
		accessToken.setExpireTime(tokenProperties.getExpireTime() + System.currentTimeMillis());
		return accessToken;
	}

	@Override
	public String getUserDetails(String token) {
		if (StrUtil.isBlank(token)) {
			return null;
		}
		if (!token.startsWith(securityProperties.getToken().getTokenType())) {
			return null;
		}
		token = token.replace(securityProperties.getToken().getTokenType(), "").trim();
		Object user = null;
		try {
			SpringSecurityProperties.TokenProperties tokenProperties = securityProperties.getToken();
			Jws<Claims> jws = Jwts.parser().setSigningKey(tokenProperties.getSignKey()).parseClaimsJws(token);
//			user = jws.getBody().get("user");
			return jws.getBody().get("user", String.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getUserDetails(HttpServletRequest request) {

		if (null == request) {
			return null;
		}

		SpringSecurityProperties.TokenProperties properties = securityProperties.getToken();


		String token = request.getHeader(properties.getHeaderKey());
		if (StrUtil.isBlank(token)) {
			token = Opt.ofNullable(ServletUtil.getCookie(request, properties.getHeaderKey())).map(a -> a.getValue()).orElse(null);
			if (StrUtil.isNotBlank(token)) {
				token = securityProperties.getToken().getTokenType() + " " + token;
			}
		}
		if (StrUtil.isBlank(token)) {
			token = request.getParameter(properties.getHeaderKey());
		}
		return this.getUserDetails(token);
	}
}
