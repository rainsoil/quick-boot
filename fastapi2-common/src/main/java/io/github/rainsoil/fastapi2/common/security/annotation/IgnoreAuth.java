package io.github.rainsoil.fastapi2.common.security.annotation;

import java.lang.annotation.*;

/**
 * 忽略授权注解
 *
 * @author luyanan
 * @since 2023/11/28
 **/
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface IgnoreAuth {
}
