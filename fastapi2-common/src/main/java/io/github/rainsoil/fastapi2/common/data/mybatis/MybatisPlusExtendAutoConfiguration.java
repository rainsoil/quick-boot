package io.github.rainsoil.fastapi2.common.data.mybatis;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * mybatis plus 扩展类配置类
 *
 * @author luyanan
 * @since 2023/09/16
 **/
@Configuration
@ConditionalOnClass(MybatisPlusAutoConfiguration.class)
public class MybatisPlusExtendAutoConfiguration {

    /**
     * sql扩展注入
     *
     * @since 2023/09/16
     */
    @Bean
    @Primary
    public MySqlInjector mySqlInjector() {
        return new MySqlInjector();
    }

    /**
     * 自定义id生成器
     *
     * @since 2023/09/16
     */
    @Bean
    public CustomKeyGenerator customKeyGenerator() {
        return new CustomKeyGenerator();
    }
}
