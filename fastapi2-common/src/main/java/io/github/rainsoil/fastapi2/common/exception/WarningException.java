package io.github.rainsoil.fastapi2.common.exception;

/**
 * 警告异常
 *
 * @author luyanan
 * @since 2023/09/09
 **/
public class WarningException  extends  BaseException{
	public WarningException(Integer code, String msg, String... args) {
		super(code, msg, args);
	}

	public WarningException(Integer code, String... args) {
		super(code, args);
	}

	public WarningException(String msg, String... args) {
		super(msg, args);
	}
}
