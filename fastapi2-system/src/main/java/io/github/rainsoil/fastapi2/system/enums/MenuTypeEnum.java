package io.github.rainsoil.fastapi2.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单枚举
 *
 * @author luyanan
 * @since 2023/11/30
 **/
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

	/**
	 * 左侧菜单
	 */
	LEFT_MENU("0", "left"),

	/**
	 * 顶部菜单
	 */
	TOP_MENU("2", "top"),

	/**
	 * 按钮
	 */
	BUTTON("1", "button");

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 描述
	 */
	private String description;

}