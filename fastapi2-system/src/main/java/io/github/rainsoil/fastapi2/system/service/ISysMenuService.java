package io.github.rainsoil.fastapi2.system.service;

import cn.hutool.core.lang.tree.Tree;
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

	/**
	 * 获取用户的菜单
	 *
	 * @param type     类型
	 * @param userId   用户id
	 * @param parentId 父id
	 * @return
	 * @since 2023/11/30
	 */
	List<Tree<Long>> getMenu(String type, Long userId, Long parentId);

	/**
	 * 树形菜单
	 *
	 * @param lazy     是否懒加载
	 * @param parentId 父id
	 * @return
	 * @since 2023/12/10
	 */
	List<Tree<Long>> treeMenu(boolean lazy, Long parentId);
}
