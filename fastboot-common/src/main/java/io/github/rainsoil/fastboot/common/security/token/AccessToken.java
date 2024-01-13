package io.github.rainsoil.fastboot.common.security.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

/**
 * 访问的token
 *
 * @author luyanan
 * @since 2023/11/28
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

	private Long expireTime;

	/**
	 * 用户信息
	 *
	 * @since 2023/11/28
	 */

	private UserDetails user;


	/**
	 * 用户角色编码
	 *
	 * @since 2023/11/28
	 */

	private List<String> roleCodes;


	/**
	 * 角色id
	 *
	 * @since 2023/11/28
	 */

	private List<Long> roles;


	/**
	 * 授权
	 *
	 * @since 2023/11/28
	 */

	private List<String> auths;
}
