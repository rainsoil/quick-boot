package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastapi2.core.user.LoginUser;
import io.github.rainsoil.fastapi2.core.user.LoginUserUtils;
import io.github.rainsoil.fastapi2.system.entity.SysMenu;
import io.github.rainsoil.fastapi2.system.entity.SysRoleMenu;
import io.github.rainsoil.fastapi2.system.enums.MenuTypeEnum;
import io.github.rainsoil.fastapi2.system.mapper.SysMenuMapper;
import io.github.rainsoil.fastapi2.system.service.ISysMenuService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastapi2.system.service.ISysRoleMenuService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统菜单表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {


	private final ISysRoleMenuService sysRoleMenuService;


	@Override
	public List<SysMenu> listByRoleId(List<Long> roleIds) {
		if (CollectionUtil.isEmpty(roleIds)) {
			return new ArrayList<>();
		}
		List<SysRoleMenu> roleMenuList = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds));
		List<Long> menuIds = roleMenuList.stream().map(a -> a.getMenuId()).distinct().collect(Collectors.toList());
		return this.listByIds(menuIds);

	}

	@Override
	public List<Tree<Long>> getMenu(String type, Long userId, Long parentId) {

		LoginUser user = LoginUserUtils.getUser();
		if (null == user) {
			return new ArrayList<>();
		}
		List<Long> roleIds = user.getRoles();
		List<SysMenu> sysMenus = this.listByRoleId(roleIds);

		sysMenus.stream().filter(menuTypePredicate(type)).map());
	}


	public Predicate<SysMenu> menuTypePredicate(String type) {
		return vo -> {
			if (MenuTypeEnum.TOP_MENU.getDescription().equals(type)) {
				return MenuTypeEnum.TOP_MENU.getType().equals(vo.getType());
			}
			// 其他查询 左侧 + 顶部
			return !MenuTypeEnum.BUTTON.getType().equals(vo.getType());
		};
	}

	private  function<SysMenu, Tree<Long>> mapToTree() {
		return vo -> {
			Tree<Long> tree = new Tree<>();
			tree.setId(vo.getId());
			tree.setParentId(vo.getParentId());
			tree.setName(vo.getMenuName());
			tree.setWeight(vo.getSort());
			return tree;
		};
	}
}
