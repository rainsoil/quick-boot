package io.github.rainsoil.fastboot.system.service;

import io.github.rainsoil.fastboot.system.entity.SysDict;
import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastboot.system.entity.SysDictItem;

import java.util.List;

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
public interface ISysDictService extends IBaseService<SysDict> {

	/**
	 * 根据字典类型查询字典项
	 *
	 * @param type  字典类型
	 * @param style 是否携带样式
	 * @return
	 * @since 2024/01/04
	 */
	List<SysDictItem> getByType(String type, Boolean style);

}
