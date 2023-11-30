package io.github.rainsoil.fastapi2.system.service;

import io.github.rainsoil.fastapi2.system.entity.SysMenu;
import io.github.rainsoil.fastapi2.common.data.mybatis.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统菜单表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

	/**
	 * 根据角色id查询关联的菜单列表
	 *
	 * @param roleIds 角色id集合
	 * @return 菜单列表
	 * @since 2023/11/30
	 */
	List<SysMenu> listByRoleId(List<Long> roleIds);

}
