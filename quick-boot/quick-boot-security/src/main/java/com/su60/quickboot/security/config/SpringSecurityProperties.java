package com.su60.quickboot.security.config;

import com.su60.quickboot.security.captcha.CaptchaTypeEnum;
import com.su60.quickboot.security.client.OauthClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 安全配置文件
 *
 * @author luyanan
 * @since 2024/04/17
 **/
@Data
@ConfigurationProperties(SpringSecurityProperties.PREFIX)
public class SpringSecurityProperties {
	public static final String PREFIX = "spring.security";


	/**
	 * 是否开启
	 *
	 * @since 2024/04/20
	 */

	private Boolean enable;


	/**
	 * token的配置
	 *
	 * @since 2024/04/17
	 */

	private TokenProperties token;


	/**
	 * 验证码的配置
	 *
	 * @since 2024/07/16
	 */

	private CaptchaProperties captcha;


	/**
	 * 忽略登录的文件
	 *
	 * @since 2024/04/20
	 */

	private List<String> ignoreUrls = new ArrayList<>();


	/**
	 * 授权客户端
	 *
	 * @since 2024/07/17
	 */

	private List<OauthClient> oauthClients;
	/**
	 * token的配置
	 *
	 * @author luyanan
	 * @since 2024/04/17
	 */
	@Data
	public static class TokenProperties {

		private Boolean enable;

		/**
		 * token的过期时间
		 *
		 * @since 2024/04/17
		 */

		private Long expireTime = 30 * 24 * 60 * 60 * 1000L;


		/**
		 * 密钥
		 *
		 * @since 2024/04/17
		 */

		private String signKey;


		private String headerKey = "Authorization";


		private String tokenType = "Bearer";


	}


	/**
	 * 验证码配置类
	 *
	 * @author luyanan
	 * @since 2024/07/16
	 */
	@Data
	public static class CaptchaProperties {

		/**
		 * 是否开启
		 *
		 * @since 2024/07/16
		 */

		private Boolean enable;


		/**
		 * 验证码的类型
		 *
		 * @since 2024/07/16
		 */

		private CaptchaTypeEnum type;


		/**
		 * 验证码的高度
		 *
		 * @since 2024/07/16
		 */

		private Integer height;


		/**
		 * 验证码的宽度
		 *
		 * @since 2024/07/16
		 */

		private Integer width;

		/**
		 * 字符个数
		 *
		 * @since 2024/07/16
		 */

		private Integer codeCount;


		/**
		 * 干扰线条数
		 *
		 * @since 2024/07/16
		 */

		private Integer lineCount;

		/**
		 * 过期时间
		 *
		 * @since 2024/07/17
		 */

		private Integer expireTime;
	}
}
