package io.github.rainsoil.fastapi2.common.exception;

/**
 * 基础异常类
 *
 * @author luyanan
 * @since 2023/09/09
 **/
public class BaseException extends RuntimeException {

    /**
     * 状态码
     *
     * @since 2023/09/09
     */

    private Integer code;

    /**
     * 错误信息
     *
     * @since 2023/09/09
     */

    private String msg;

    /**
     * 参数
     *
     * @since 2023/09/09
     */

    private Object[] args;


    public BaseException(String msg, Integer code, Object... args) {
        super(msg);
        this.code = code;
        this.msg = msg;
        this.args = args;
    }


    public BaseException(Integer code, Object... args) {
        super(code.toString());
        this.code = code;
        this.args = args;
    }


    public BaseException(String msg, Object... args) {
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

    public Object[] getArgs() {
        return args;
    }
}
