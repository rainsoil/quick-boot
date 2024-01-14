package io.github.rainsoil.fastboot.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全配置类
 *
 * @author luyanan
 * @since 2023/11/28
 **/
@Data
@ConfigurationProperties(SpringSecurityProperties.PREFIX)
public class SpringSecurityProperties {


	public static final String PREFIX = "spring.security";


	/**
	 * 忽略授权登录的url
	 *
	 * @since 2023/11/28
	 */

	private List<String> ignoreUrls = new ArrayList<>();

	/**
	 * token配置
	 *
	 * @since 2023/11/28
	 */

	private TokenProperties token = new TokenProperties();


	/**
	 * 验证码的配置
	 *
	 * @since 2024/01/13
	 */

	private ValidateCode validatecode = new ValidateCode();

	/**
	 * token配置类
	 *
	 * @author luyanan
	 * @since 2023/11/28
	 */

	@Data
	public  class TokenProperties {

		/**
		 * 过期时间
		 *
		 * @since 2023/11/28
		 */

		private Long expireTime = 30 * 24 * 60 * 60 * 1000L;


		/**
		 * 密钥
		 *
		 * @since 2023/11/28
		 */

		private String signKey = "sdfsd12334";


		/**
		 * header 的key
		 *
		 * @since 2023/11/28
		 */

		private String headerKey = "Authorization";


		private String tokenType = "Bearer";
	}


	/**
	 * 验证码配置
	 *
	 * @author luyanan
	 * @since 2024/01/13
	 */
	@Data
	public class ValidateCode {

		/**
		 * 是否开启
		 *
		 * @since 2024/01/13
		 */

		private boolean enable;
		/**
		 * 匹配的url
		 *
		 * @since 2024/01/13
		 */

		private String matchurl;


		/**
		 * 有效时间
		 *
		 * @since 2024/01/13
		 */

		private Integer effectiveTime = 5;
	}

}
