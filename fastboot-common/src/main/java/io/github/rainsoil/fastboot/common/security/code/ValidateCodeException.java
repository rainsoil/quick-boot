package io.github.rainsoil.fastboot.common.security.code;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码异常类
 *
 * @author luyanan
 * @since 2024/01/13
 **/
public class ValidateCodeException extends AuthenticationException  {
	public ValidateCodeException(String msg) {
		super(msg);
	}
}
