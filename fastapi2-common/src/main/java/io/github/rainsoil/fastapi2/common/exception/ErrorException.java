package io.github.rainsoil.fastapi2.common.exception;

/**
 * 错误提示
 *
 * @author luyanan
 * @since 2023/09/09
 **/
public class ErrorException extends BaseException {
    public ErrorException(String msg, Integer code, Object... args) {
        super(msg, code, args);
    }

    public ErrorException(Integer code, Object... args) {
        super(code, args);
    }

    public ErrorException(String msg, Object... args) {
        super(msg, args);
    }
}
