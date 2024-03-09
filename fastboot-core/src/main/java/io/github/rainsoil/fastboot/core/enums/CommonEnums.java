package io.github.rainsoil.fastboot.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用枚举
 *
 * @author luyanan
 * @since 2024/01/28
 **/
@AllArgsConstructor
@Getter
public enum CommonEnums {
	FLAG_YES(Type.FLAG, "0", "是"),

	FLAG_NO(Type.FLAG, "1", "否"),


	;
	/**
	 * 字典值的type
	 *
	 * @since 2024/01/28
	 */

	private Type type;

	/**
	 * 字典值
	 *
	 * @since 2024/01/28
	 */

	private String value;

	/**
	 * 字典名称
	 *
	 * @since 2024/01/28
	 */

	private String label;

	/**
	 * 类型
	 *
	 * @author luyanan
	 * @since 2024/01/28
	 */
	public enum Type {

		/**
		 * 开关
		 *
		 * @since 2024/01/28
		 */

		FLAG;
	}
}
