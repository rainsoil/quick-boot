package io.github.rainsoil.fastapi2.common.spring;

import io.github.rainsoil.fastapi2.common.spring.restful.RestFulAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * spring扩展自动配置类
 *
 * @author luyanan
 * @since 2023/09/10
 **/
@Configuration
@ConditionalOnProperty(prefix = SpringExtendProperties.PREFIX, value = "enable", matchIfMissing = false, havingValue = "true")
@Import(RestFulAutoConfiguration.class)
@EnableConfigurationProperties({SpringExtendProperties.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class SpringExtendAutoConfiguration {


}
