package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.rainsoil.fastapi2.core.user.LoginUser;
import io.github.rainsoil.fastapi2.core.user.LoginUserUtils;
import io.github.rainsoil.fastapi2.system.constants.Constants;
import io.github.rainsoil.fastapi2.system.entity.SysMenu;
import io.github.rainsoil.fastapi2.system.entity.SysRoleMenu;
import io.github.rainsoil.fastapi2.system.enums.SysMenuEnums;
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

		List<TreeNode<Long>> list = sysMenus.stream().filter(menuTypePredicate(type)).map(getNodeFunction2()).collect(Collectors.toList());
		Long parent = parentId == null ? Constants.MENU_ROOT_ID : parentId;
		return TreeUtil.build(list, parent);
	}

	@Override
	public List<Tree<Long>> treeMenu(boolean lazy, Long parentId) {
//		LoginUser user = LoginUserUtils.getUser();
//		List<Long> roles = user.getRoles();
//		if (CollectionUtil.isEmpty(roles)) {
//			return new ArrayList<>();
//		}
		List<SysMenu> sysMenus = this.list();

		if (!lazy) {
			List<TreeNode<Long>> collect = sysMenus.stream()
					.map(getNodeFunction()).collect(Collectors.toList());

			return TreeUtil.build(collect, Constants.MENU_ROOT_ID);
		}

		Long parent = parentId == null ? Constants.MENU_ROOT_ID : parentId;
		List<TreeNode<Long>> collect = baseMapper
				.selectList(
						Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parent)

								.in(SysMenu::getId,
										sysMenus.stream().map(a -> a.getId()).collect(Collectors.toList())
								).orderByAsc(SysMenu::getSort))
				.stream().map(getNodeFunction()).collect(Collectors.toList());

		return TreeUtil.build(collect, parent);
	}


	public Predicate<SysMenu> menuTypePredicate(String type) {
		return vo -> {
			// 这里只是查询左侧目录和菜单
			return vo
					.getType().equals(SysMenuEnums.MENU_TYPE_LEFT_MENU.getValue()) || vo.getType().equals(SysMenuEnums.MENU.getValue());
//			if (SysMenuEnums.MENU_TYPE_TOP_MENU.getValue().equals(type)) {
//				return SysMenuEnums.MENU_TYPE_TOP_MENU.getType().equals(vo.getType());
//			}
			// 其他查询 左侧 + 顶部
//			return !SysMenuEnums.MENU_TYPE_BUTTON.getValue().equals(vo.getType());
		};
	}


	private Function<SysMenu, TreeNode<Long>> getNodeFunction2() {
		return vo -> {
			TreeNode<Long> node = new TreeNode<>();
			node.setId(vo.getId());
			node.setParentId(vo.getParentId());
			node.setName(vo.getName());
			node.setWeight(vo.getSort());
			Map<String, Object> ext = new HashMap<>();


			ext.put("sort", vo.getSort());
			ext.put("icon", vo.getIcon());
			ext.put("path", vo.getPath());
			ext.put("type", vo.getType());
			ext.put("component", vo.getComponent());


			if (vo.getBlank().equals("1")) {
				// 不是外链
				ext.put("meta", new HashMap<String, Object>() {{
					put("i18n", vo.getName());
					put("keepAlive", vo.getKeepAlive().equals("1") ? false : true);
				}});

			} else {
				ext.put("href", vo.getPath());
				ext.put("meta", new HashMap<String, Object>() {{
					put("target", "_blank");
					put("title", vo.getName());
					put("keepAlive", vo.getKeepAlive().equals("1") ? false : true);
					put("i18n", vo.getName());
				}});
			}

			node.setExtra(ext);

			return node;
		};
	}

	private Function<SysMenu, TreeNode<Long>> getNodeFunction() {
		return vo -> {
			TreeNode<Long> node = new TreeNode<>();
			node.setId(vo.getId());
			node.setParentId(vo.getParentId());
			node.setName(vo.getName());
			node.setWeight(vo.getSort());
			Map<String, Object> ext = new HashMap<>();

			ext.put("sort", vo.getSort());
			ext.put("icon", vo.getIcon());
			ext.put("path", vo.getPath());
			ext.put("type", vo.getType());
			ext.put("component", vo.getComponent());
			ext.put("blank", vo.getBlank());
			ext.put("keepAlive", vo.getKeepAlive());
			ext.put("permission", vo.getPermission());
			if (vo.getBlank().equals("1")) {
				// 不是外链
				ext.put("meta", new HashMap<String, Object>() {{

					put("keepAlive", vo.getKeepAlive().equals("1") ? false : true);
				}});


			} else {
				ext.put("href", vo.getPath());
				ext.put("meta", new HashMap<String, Object>() {{
					put("target", "_blank");
					put("title", vo.getName());
					put("keepAlive", vo.getKeepAlive().equals("1") ? false : true);
				}});
			}

			node.setExtra(ext);

			return node;
		};
	}
}
