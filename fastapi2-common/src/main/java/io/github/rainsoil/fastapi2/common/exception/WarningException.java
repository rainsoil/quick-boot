package io.github.rainsoil.fastapi2.common.exception;

/**
 * 警告异常
 *
 * @author luyanan
 * @since 2023/09/09
 **/
public class WarningException  extends  BaseException{
    public WarningException(String msg, Integer code, Object... args) {
        super(msg, code, args);
    }

    public WarningException(Integer code, Object... args) {
        super(code, args);
    }

    public WarningException(String msg, Object... args) {
        super(msg, args);
    }
}
