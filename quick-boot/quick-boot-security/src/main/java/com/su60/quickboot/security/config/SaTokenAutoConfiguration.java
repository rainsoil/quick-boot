package com.su60.quickboot.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Sa-Token 自动配置类
 * 用于替换Spring Security的自动配置
 *
 * @author luyanan
 * @since 2024/12/19
 */
@Configuration
@ConditionalOnProperty(prefix = "sa-token", name = "enable", havingValue = "true", matchIfMissing = true)
@Import({SaTokenConfiguration.class})
public class SaTokenAutoConfiguration {


}
