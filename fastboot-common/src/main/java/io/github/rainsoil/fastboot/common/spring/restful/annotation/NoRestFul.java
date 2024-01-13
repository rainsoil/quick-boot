package io.github.rainsoil.fastboot.common.spring.restful.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不进行结果的封装
 *
 * @author luyanan
 * @since 2023/09/09
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NoRestFul {
}
