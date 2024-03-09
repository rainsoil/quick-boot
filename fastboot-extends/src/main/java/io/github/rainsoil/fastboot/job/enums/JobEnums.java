package io.github.rainsoil.fastboot.job.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 定时任务枚举
 *
 * @author luyanan
 * @since 2024/02/01
 **/
@Getter
@AllArgsConstructor
public enum JobEnums {


	STATUS_PAUSE(Type.STATUS, "0", "暂停"),

	STATUS_NORMAL(Type.STATUS, "1", "正常");


	/**
	 * 类型
	 *
	 * @since 2024/02/01
	 */

	private Type type;


	/**
	 * 字典值
	 *
	 * @since 2024/02/01
	 */

	private String value;


	/**
	 * 标签
	 *
	 * @since 2024/02/01
	 */

	private String label;

	/**
	 * 字典type
	 *
	 * @author luyanan
	 * @since 2024/02/01
	 */
	public enum Type {
		/**
		 * 状态
		 *
		 * @since 2024/02/01
		 */

		STATUS;
	}
}
