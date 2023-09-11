package io.github.rainsoil.fastapi2.common.bean;

/**
 * bean转换回调
 *
 * @param <T> 目标对象泛型
 * @param <S> 源对象泛型
 * @author luyanan
 * @since 2023/09/09
 **/
@FunctionalInterface
public interface ConvertCallBack<S, T> {


    /**
     * 回调函数
     *
     * @param s 源对象
     * @param t 目标对象
     * @since 2023/09/09
     */
    void callBack(S s, T t);
}
