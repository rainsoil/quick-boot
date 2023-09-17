package io.github.rainsoil.fastapi2.common.core;

import io.github.rainsoil.fastapi2.common.exception.GlobalMsgCode;
import lombok.Data;

/**
 * 统一返回
 *
 * @param <T> 返回泛型
 * @author luyanan
 * @since 2023/09/09
 **/
@Data
public class R<T> {

    /**
     * 成功的返回状态码
     *
     * @since 2023/09/09
     */

    private static final Integer SUCCESS_STATUS = GlobalMsgCode.SUCCESS;


    /**
     * 默认失败的返回状态码
     *
     * @since 2023/09/09
     */

    private static final Integer ERROR_STATUS = GlobalMsgCode.INTERNAL_SERVER_ERROR;
    /**
     * 状态码
     *
     * @since 2023/09/09
     */

    private Integer status;


    /**
     * 提示消息
     *
     * @since 2023/09/09
     */

    private String msg;


    /**
     * 返回数据
     *
     * @since 2023/09/09
     */

    private T data;

    /**
     * 成功的返回
     *
     * @param <T>  返回泛型
     * @param data 数据
     * @return R对象
     * @since 2023/09/09
     */
    public static <T> R<T> ok(T data) {
        return restResult(SUCCESS_STATUS, null, data);
    }

    /**
     * 成功的返回
     *
     * @param <T> 返回泛型
     * @return R对象
     * @since 2023/09/09
     */
    public static <T> R<T> ok() {
        return ok(null);
    }


    /**
     * 失败的返回
     *
     * @param status 状态码
     * @param msg    提示消息
     * @return R
     * @since 2023/09/09
     */
    public static R failed(Integer status, String msg) {

        return restResult(status, msg, null);
    }

    /**
     * 失败的返回
     *
     * @param msg 提示消息
     * @return R对象
     * @since 2023/09/09
     */
    public static R failed(String msg) {
        return failed(ERROR_STATUS, msg);
    }

    /**
     * 失败的返回
     *
     * @return R对象
     * @since 2023/09/09
     */
    public static R failed() {
        return failed(null);
    }

    /**
     * 设置R对象
     *
     * @param <T>    返回泛型
     * @param status 状态码
     * @param msg    提示消息
     * @param data   数据
     * @return R对象
     * @since 2023/09/09
     */
    private static <T> R<T> restResult(Integer status, String msg, T data) {
        R<T> r = new R<>();
        r.setStatus(status);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
}
