package io.github.rainsoil.fastapi2.system.enums;

import io.github.rainsoil.fastapi2.core.dict.IDictEnums;
import io.github.rainsoil.fastapi2.core.dict.IDictTypeEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单枚举类
 *
 * @author luyanan
 * @since 2023/12/11
 **/
@Getter
@AllArgsConstructor
public enum SysMenuEnums implements IDictEnums {


	/**
	 * 左侧目录
	 */
	MENU_TYPE_LEFT_MENU(DictType.MENU_TYPE, "left", "0"),

	/**
	 * 顶部目录
	 */
	MENU_TYPE_TOP_MENU(DictType.MENU_TYPE, "top", "1"),

	/**
	 * 菜单
	 */
	MENU(DictType.MENU_TYPE, "menu", "2"),
	/**
	 * 按钮
	 */
	MENU_TYPE_BUTTON(DictType.MENU_TYPE, "button", "3");;

	private DictType type;

	private String label;

	private String value;


	public static enum DictType implements IDictTypeEnums {

		// 菜单类型
		MENU_TYPE;
	}
}
