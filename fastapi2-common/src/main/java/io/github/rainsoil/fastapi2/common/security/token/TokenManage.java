package io.github.rainsoil.fastapi2.common.security.token;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;

/**
 * token管理
 *
 * @author luyanan
 * @since 2023/11/28
 **/
public interface TokenManage {


    /**
     * 创建token
     *
     * @param userDetails 用户信息
     * @return token
     * @since 2023/11/28
     */
    AccessToken createToken(UserDetails userDetails);


    /**
     * 根据token解析出来用户信息
     *
     * @param token token
     * @return 用户信息
     * @since 2023/11/28
     */
    UserDetails getUserDetails(String token);


    /**
     * 从请求头 中获取token
     *
     * @param request 请求头
     * @return
     * @since 2023/11/28
     */
    UserDetails getUserDetails(HttpServletRequest request);
}
