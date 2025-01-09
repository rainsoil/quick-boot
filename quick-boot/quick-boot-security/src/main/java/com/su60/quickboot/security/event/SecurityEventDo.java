package com.su60.quickboot.security.event;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SecurityEventDo {




	/**
	 * 用户名
	 *
	 * @since 2024/11/15
	 */

	private String username;


	/**
	 * 访问时间
	 *
	 * @since 2024/11/15
	 */
	private Long loginTime;


	/**
	 * 登录IP地址
	 *
	 * @since 2024/11/15
	 */
	private String ipaddr;


	/**
	 * userAgent
	 *
	 * @since 2024/11/15
	 */
	private String userAgent;


}
