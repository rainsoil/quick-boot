package io.github.rainsoil.fastapi2.common.security.token;

import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.fastjson.JSON;
import io.github.rainsoil.fastapi2.common.security.config.SpringSecurityProperties;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * jwt token 抽象类
 *
 * @author luyanan
 * @since 2023/11/28
 **/
public class AbstractJwtTokenManage implements TokenManage {

	@Autowired
	private SpringSecurityProperties securityProperties;

	@Override
	public AccessToken createToken(UserDetails userDetails) {
		if (null == userDetails) {
			return null;
		}
		SpringSecurityProperties.TokenProperties tokenProperties = securityProperties.getToken();
		String token = Jwts.builder().setSubject(userDetails.getUsername())
				.claim("user", JSON.toJSONString(userDetails))
				.setExpiration(new Date(System.currentTimeMillis() + tokenProperties.getExpireTime()))
				.signWith(SignatureAlgorithm.HS512, tokenProperties.getSignKey())
				.compressWith(CompressionCodecs.GZIP).compact();
		AccessToken accessToken = new AccessToken();
		accessToken.setAccessToken(token);
		accessToken.setUser(userDetails);
		accessToken.setExpireTime(tokenProperties.getExpireTime());
		return accessToken;
	}

	@Override
	public UserDetails getUserDetails(String token) {
		if (StrUtil.isBlank(token)) {
			return null;
		}
		Object user = null;
		try {
			SpringSecurityProperties.TokenProperties tokenProperties = securityProperties.getToken();
			Jws<Claims> jws = Jwts.parser().setSigningKey(tokenProperties.getSignKey()).parseClaimsJws(token);
			user = jws.getBody().get("user");
			return JSON.parseObject(user.toString(), UserDetails.class);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UserDetails getUserDetails(HttpServletRequest request) {
		if (null == request) {
			return null;
		}
		SpringSecurityProperties.TokenProperties tokenProperties = securityProperties.getToken();

		String token = request.getHeader(tokenProperties.getHeaderKey());
		if (StrUtil.isBlank(token)) {
			token = Opt.ofNullable(ServletUtil.getCookie(request, tokenProperties.getHeaderKey())).map(a -> a.getValue()).orElse(null);
		}
		if (StrUtil.isBlank(token)) {
			token = request.getParameter(tokenProperties.getHeaderKey());
		}
		return this.getUserDetails(token);
	}
}
