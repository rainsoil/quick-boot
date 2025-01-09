package com.su60.quickboot.data.file;

import com.su60.quickboot.data.file.strategy.LocalFileTemplateStorage;
import org.dromara.x.file.storage.core.aspect.FileStorageAspect;
import org.dromara.x.file.storage.spring.EnableFileStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 文件配置
 *
 * @author luyanan
 * @since 2024/05/30
 **/
@Configuration
@EnableFileStorage
@EnableConfigurationProperties(FileProperties.class)
@ComponentScan("com.su60.quickboot.data.file")
public class FileConfiguration {

	/*
	 *//**
	 * 文件分类处理类
	 *
	 * @param fileProperties 配置文件
	 * @return 文件分类处理器
	 * @since 2024/06/05
	 *//*
	@Bean
	public FileClassifyService fileClassifyService(FileProperties fileProperties) {
		return new FileClassifyService(fileProperties);
	}

	*//**
	 * 文件切面
	 *
	 * @return 文件存储切面
	 * @since 2024/05/30
	 *//*
	@Bean
	public FileStorageAspect fileStorageAspect() {
		return new CustomFileStorageAspect();
	}


	*/

	/**
	 * 文件处理模板
	 *
	 * @return 文件处理类
	 * @since 2024/06/15
	 *//*
	@Bean
	public FileTemplate fileTemplate() {
		return new XfileStorageFileTemplate();
	}*/
	@Bean
	@ConditionalOnProperty(prefix = "file", name = "type", havingValue = "local")
	public FileTemplate fileTemplate(FileProperties fileProperties) {
		return new LocalFileTemplateStorage(fileProperties);
	}



}
