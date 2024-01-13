package io.github.rainsoil.fastapi2.system.service;

import io.github.rainsoil.fastapi2.system.entity.SysRoleMenu;
import io.github.rainsoil.fastapi2.common.data.mybatis.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统角色菜单关联表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
public interface ISysRoleMenuService extends IBaseService<SysRoleMenu> {

	/**
	 * 保存
	 *
	 * @param roleId 角色id
	 * @param menus  菜单id
	 * @return
	 * @since 2024/01/05
	 */
	void save(Long roleId, List<Long> menus);
}
