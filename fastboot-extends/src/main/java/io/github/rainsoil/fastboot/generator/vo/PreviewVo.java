package io.github.rainsoil.fastboot.generator.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 预览的vo类
 *
 * @author luyanan
 * @since 2024/01/28
 **/
@Data
@Accessors(chain = true)
public class PreviewVo implements Serializable {

	/**
	 * 标识
	 *
	 * @since 2024/01/28
	 */

	private String name;

	/**
	 * 代码
	 *
	 * @since 2024/01/28
	 */

	private String code;

	/**
	 * 标签名
	 *
	 * @since 2024/01/28
	 */

	private String label;
}
