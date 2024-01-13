package io.github.rainsoil.fastboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.system.entity.SysRole;
import io.github.rainsoil.fastboot.system.entity.SysRoleMenu;
import io.github.rainsoil.fastboot.system.entity.SysUserRole;
import io.github.rainsoil.fastboot.system.mapper.SysRoleMapper;
import io.github.rainsoil.fastboot.system.service.ISysRoleMenuService;
import io.github.rainsoil.fastboot.system.service.ISysRoleService;
import io.github.rainsoil.fastboot.system.service.ISysUserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

	private final ISysUserRoleService sysUserRoleService;

	private final ISysRoleMenuService sysRoleMenuService;


	@Override
	public PageInfo<SysRole> page(PageRequest<SysRole> pageRequest) {
		PageInfo<SysRole> page = super.page(pageRequest, new PageHandler<SysRole>() {
			@Override
			public void recordsHandler(List<SysRole> records) {
				for (SysRole record : records) {
					List<SysRoleMenu> list = sysRoleMenuService.list(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, record.getId()));
					record.setMenus(list.stream().map(a -> a.getMenuId()).distinct().collect(Collectors.toList()));
				}
			}
		});

		return page;
	}

	@Override
	public List<SysRole> listByUserId(Long userId) {

		List<SysUserRole> list = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
		if (CollectionUtil.isEmpty(list)) {
			return new ArrayList<>();
		}
		return this.listByIds(list.stream().map(a -> a.getRoleId()).distinct().collect(Collectors.toList()));

	}

	@Override
	public boolean save(SysRole entity) {
		List<Long> menus = entity.getMenus();

		boolean save = super.save(entity);
		if (CollectionUtil.isNotEmpty(menus)) {


			sysRoleMenuService.save(entity.getId(), menus);
		}
		return save;
	}

	@Override
	public boolean removeByIds(Collection<?> list) {

		if (CollectionUtil.isEmpty(list)) {
			return false;
		}
		sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, list));
		return super.removeByIds(list);
	}

	@Override
	public boolean removeById(Serializable id) {
		sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));

		return super.removeById(id);
	}

	@Override
	public boolean updateById(SysRole entity) {
		sysRoleMenuService.save(entity.getId(), entity.getMenus());
		return super.updateById(entity);
	}
}
