package io.github.rainsoil.fastboot.common.security;

import io.github.rainsoil.fastboot.common.security.config.SpringSecurityProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 客户端配置
 *
 * @author luyanan
 * @since 2023/11/30
 **/
@Data
@ConfigurationProperties(SpringSecurityProperties.PREFIX + ".client")
public class ClientProperties {


	/**
	 * 客户端配置
	 *
	 * @since 2023/11/30
	 */

	private List<Client> clients;

	/**
	 * 客户端
	 *
	 * @author luyanan
	 * @since 2023/11/30
	 */
	@Data
	public static class Client {
		/**
		 * 客户端名称
		 *
		 * @since 2023/11/30
		 */

		private String name;

		/**
		 * 密钥
		 *
		 * @since 2023/11/30
		 */

		private String secret;


		private Boolean encryption;

		private Boolean verificationcode;

	}


	/**
	 * 配置的客户端
	 *
	 * @return
	 * @since 2023/11/30
	 */
	public Map<String, Client> getClient() {
		return this.clients.stream().collect(Collectors.toMap(k -> k.name, v -> v));
	}
}
