package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastapi2.system.entity.SysRoleMenu;
import io.github.rainsoil.fastapi2.system.mapper.SysRoleMenuMapper;
import io.github.rainsoil.fastapi2.system.service.ISysRoleMenuService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统角色菜单关联表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysRoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements ISysRoleMenuService {

	@Override
	public void save(Long roleId, List<Long> menus) {
		// 先删除, 重新添加
		this.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
		List<SysRoleMenu> sysRoleMenus = new ArrayList<>();
		for (Long menu : menus) {
			SysRoleMenu sysRoleMenu = new SysRoleMenu()
					.setRoleId(roleId)
					.setMenuId(menu);
			sysRoleMenus.add(sysRoleMenu);

		}
		if (CollectionUtil.isNotEmpty(sysRoleMenus)) {
			this.saveBatch(sysRoleMenus);
		}

	}

}
