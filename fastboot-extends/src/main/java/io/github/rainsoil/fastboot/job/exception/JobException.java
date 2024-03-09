package io.github.rainsoil.fastboot.job.exception;

import io.github.rainsoil.fastboot.common.exception.BaseException;

/**
 * 定时任务异常
 *
 * @author luyanan
 * @since 2024/02/02
 **/
public class JobException extends BaseException {
	public JobException(Integer code, String msg, String... args) {
		super(code, msg, args);
	}

	public JobException(Integer code, String... args) {
		super(code, args);
	}

	public JobException(String msg, String... args) {
		super(msg, args);
	}
}
