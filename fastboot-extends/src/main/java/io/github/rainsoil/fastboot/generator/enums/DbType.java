package io.github.rainsoil.fastboot.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 数据库类型
 *
 * @author luyanan
 * @since 2024/01/16
 **/
@AllArgsConstructor
@Getter
public enum DbType {

	MySQL("com.mysql.cj.jdbc.Driver"),
	;

	private String driverClass;
}
