package io.github.rainsoil.fastboot.generator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 代码生成器的默认配置
 *
 * @author luyanan
 * @since 2024/01/21
 **/
@Data
@Component
@ConfigurationProperties("code.generator")
public class CodeGeneratorProperties {

	/**
	 * 默认的作者
	 *
	 * @since 2024/01/21
	 */

	private String auth;

	/**
	 * 忽略的字段
	 *
	 * @since 2024/01/21
	 */

	private String ignoreColumns;


	/**
	 * 逻辑删除字段
	 *
	 * @since 2024/01/21
	 */

	private String logicDeleteColumnName;


	/**
	 * 乐观锁数据库字段
	 *
	 * @since 2024/01/21
	 */

	private String versionColumnName;


	/**
	 * 父包名
	 *
	 * @since 2024/01/21
	 */

	private String parentModulePackage;

}
