package com.su60.quickboot.security.client;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.Opt;
import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.security.config.SpringSecurityProperties;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

/**
 * 授权客户端service
 *
 * @author luyanan
 * @since 2024/07/17
 **/
@RequiredArgsConstructor
public class OauthClientService {

	private final SpringSecurityProperties securityProperties;

	/**
	 * 获取客户端
	 *
	 * @param clientId 客户端id
	 * @return 客户端
	 * @since 2024/07/17
	 */
	public OauthClient getClient(String clientId) {
		return Opt.ofNullable(securityProperties.getOauthClients()).map(a -> a.stream().filter(b -> b.getClientId().equals(clientId)).findFirst().orElse(null)).get();
	}

	/**
	 * 从请求头中解析出来客户端信息
	 *
	 * @param request 请求信息
	 * @return 客户端信息
	 * @since 2024/07/19
	 */

	public OauthClient getClient(HttpServletRequest request) {

		String authorization = request.getHeader("Authorization");
		if (StrUtil.isBlank(authorization)) {
			return null;
		}

		//  从Authorization 中解析出来 clientId和 clientSecret
		String s = Base64.decodeStr(authorization);
		String[] clientInfo = s.split(":");
		if (s.split(":").length != 2) {
			return null;
		}

		OauthClient client = getClient(clientInfo[0]);
		if (client == null) {
			return null;
		}
		if (client.getClientSecret().equals(clientInfo[1])) {
			return client;
		}
		return null;
	}


}
