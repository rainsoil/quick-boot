package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

		List<TreeNode<Long>> list = sysMenus.stream().filter(menuTypePredicate(type)).map(getNodeFunction()).collect(Collectors.toList());
		Long parent = parentId == null ? -1L : parentId;
		return TreeUtil.build(list, parent);
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

	private Function<SysMenu, TreeNode<Long>> getNodeFunction() {
		return vo -> {
			TreeNode<Long> node = new TreeNode<>();
			node.setId(vo.getId());
			node.setParentId(vo.getParentId());
			node.setName(vo.getMenuName());
			node.setWeight(vo.getSort());
			Map<String, Object> ext = new HashMap<>();


			ext.put("icon", vo.getIcon());
			ext.put("path", vo.getPath());
			ext.put("component", vo.getComponent());
			if (vo.getBlank().equals("1")) {
				// 不是外链
				ext.put("meta", new HashMap<String, Object>() {{

					put("keepAlive", vo.getKeepAlive().equals("1") ? false : true);
				}});


			} else {
				ext.put("href", vo.getPath());
				ext.put("meta", new HashMap<String, Object>() {{
					put("target", "_blank");
					put("title", vo.getMenuName());
					put("keepAlive", vo.getKeepAlive().equals("1") ? false : true);
				}});
			}

			node.setExtra(ext);

			return node;
		};
	}
}
