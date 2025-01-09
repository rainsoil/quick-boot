package com.su60.quickboot.security.client;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 授权客户端
 *
 * @author luyanan
 * @since 2024/07/17
 **/
@Data
@Accessors(chain = true)
public class OauthClient {

	/**
	 * 客户端id
	 *
	 * @since 2024/07/17
	 */

	private String clientId;

	/**
	 * 客户端密钥
	 *
	 * @since 2024/07/17
	 */

	private String clientSecret;


	/**
	 * 是否启用验证码
	 *
	 * @since 2024/07/17
	 */

	private Boolean verificationCode;
}
