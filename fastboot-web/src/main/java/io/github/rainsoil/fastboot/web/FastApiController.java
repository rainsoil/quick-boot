package io.github.rainsoil.fastboot.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动类
 *
 * @author luyanan
 * @since 2023/09/17
 **/
@EnableCaching
@MapperScan("io.github.rainsoil.fastboot.*.mapper")
@SpringBootApplication(scanBasePackages = {"io.github.rainsoil.fastboot.system",
		"io.github.rainsoil.fastboot.core",
		"io.github.rainsoil.fastboot.web",
		"io.github.rainsoil.fastboot.generator",
		"io.github.rainsoil.fastboot.job"})
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
