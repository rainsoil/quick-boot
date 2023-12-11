package io.github.rainsoil.fastapi2.core.dict;

/**
 * 字典枚举
 *
 * @author luyanan
 * @since 2023/12/11
 **/
public interface IDictEnums {


	/**
	 * 字典类型
	 *
	 * @since 2023/12/11
	 */

	IDictTypeEnums getType();


	/**
	 * 字典值
	 *
	 * @since 2023/12/11
	 */

	String getValue();


	/**
	 * 获取字典名称
	 *
	 * @since 2023/12/11
	 */

	String getLabel();
}
