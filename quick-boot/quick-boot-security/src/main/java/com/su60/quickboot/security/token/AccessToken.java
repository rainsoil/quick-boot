package com.su60.quickboot.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 访问的token
 *
 * @author luyanan
 * @since 2024/04/16
 **/
@Data
public class AccessToken implements Serializable {

	/**
	 * 刷新token
	 *
	 * @since 2023/11/28
	 */
	@JsonProperty("access_token")
	private String accessToken;


		@JsonProperty("token_type")
		private String tokenType;
	/**
	 * 过期时间
	 *
	 * @since 2023/11/28
	 */
	@JsonProperty("expire_time")
	private Long expireTime;


}
