package io.github.rainsoil.fastboot.core.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字段vo类
 *
 * @author luyanan
 * @since 2024/01/17
 **/
@Data
@Accessors(chain = true)
public class DictVo {


	/**
	 * 字典属性
	 *
	 * @since 2024/01/17
	 */

	private String label;


	/**
	 * 字典值
	 *
	 * @since 2024/01/17
	 */

	private String value;
}
