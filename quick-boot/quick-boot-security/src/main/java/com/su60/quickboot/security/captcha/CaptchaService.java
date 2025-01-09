package com.su60.quickboot.security.captcha;

/**
 * 验证码Service
 *
 * @author luyanan
 * @since 2024/07/16
 **/

public interface CaptchaService {


	/**
	 * 生成验证码
	 *
	 * @return 验证码类
	 * @since 2024/07/16
	 */
	CaptchaVo create();


	/**
	 * 校验验证码
	 *
	 * @param key  key
	 * @param code 验证码
	 * @return 是否校验通过
	 * @since 2024/07/16
	 */
	boolean check(String key, String code);
}
