package io.github.rainsoil.fastapi2.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 安全配置类
 *
 * @author luyanan
 * @since 2023/11/28
 **/
@Data
@ConfigurationProperties(SpringSecurityProperties.PREFIX)
public class SpringSecurityProperties {


    public static final String PREFIX = "spring.security";


    /**
     * token配置
     *
     * @since 2023/11/28
     */

    private TokenProperties token;

    /**
     * token配置类
     *
     * @author luyanan
     * @since 2023/11/28
     */

    @Data
    public static class TokenProperties {

        /**
         * 过期时间
         *
         * @since 2023/11/28
         */

        private Long expireTime = 30 * 24 * 60 * 60 * 1000L;


        /**
         * 密钥
         *
         * @since 2023/11/28
         */

        private String signKey = "sdfsd12334";


        /**
         * header 的key
         *
         * @since 2023/11/28
         */

        private String headerKey = "token";
    }

}
