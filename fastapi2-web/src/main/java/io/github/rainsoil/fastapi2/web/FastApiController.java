package io.github.rainsoil.fastapi2.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author luyanan
 * @since 2023/09/17
 **/
@MapperScan("io.github.rainsoil.fastapi2.system.mapper")
@SpringBootApplication(scanBasePackages = {"io.github.rainsoil.fastapi2.system", "io.github.rainsoil.fastapi2.core", "io.github.rainsoil.fastapi2.web"})
public class FastApiController {


    /**
     * 启动类
     *
     * @param args 参数
     * @since 2023/09/27
     */
    public static void main(String[] args) {
        SpringApplication.run(FastApiController.class, args);
    }
}
