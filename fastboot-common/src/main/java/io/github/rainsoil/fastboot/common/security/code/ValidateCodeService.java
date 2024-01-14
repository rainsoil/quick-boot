package io.github.rainsoil.fastboot.common.security.code;

import javax.servlet.http.HttpServletResponse;

/**
 * 验证码Service类
 *
 * @author luyanan
 * @since 2024/01/13
 **/
public interface ValidateCodeService {


	/**
	 * 生成验证码
	 *
	 * @param uuid     uuid
	 * @param response 返回
	 * @return
	 * @since 2024/01/13
	 */
	String createCode(String uuid, HttpServletResponse response);


	/**
	 * 校验验证码
	 *
	 * @param code 验证码
	 * @param uuid uuid
	 * @return
	 * @since 2024/01/13
	 */
	void match(String code, String uuid);
}
