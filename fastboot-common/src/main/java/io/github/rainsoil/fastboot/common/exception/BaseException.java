package io.github.rainsoil.fastboot.common.exception;

/**
 * 基础异常类
 *
 * @author luyanan
 * @since 2023/09/08
 **/
public class BaseException extends RuntimeException {


	/**
	 * 错误编码
	 *
	 * @since 2023/09/08
	 */

	private Integer code;


	/**
	 * 错误信息
	 *
	 * @since 2023/09/08
	 */

	private String msg;
	private String[] args;

	public BaseException(Integer code, String msg, String... args) {
		super(msg);
		this.code = code;
		this.msg = msg;
		this.args = args;
	}


	public BaseException(Integer code, String... args) {
		super(code.toString());
		this.code = code;
		this.args = args;
	}


	public BaseException(String msg, String... args) {
		this.code = GlobalMsgCode.INTERNAL_SERVER_ERROR;
		this.msg = msg;
		this.args = args;
	}

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public String[] getArgs() {
		return args;
	}
}
