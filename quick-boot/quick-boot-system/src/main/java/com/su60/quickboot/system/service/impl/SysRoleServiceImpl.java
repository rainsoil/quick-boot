package com.su60.quickboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.su60.quickboot.common.bean.BeanConvertUtils;
import com.su60.quickboot.common.exception.Assert;
import com.su60.quickboot.system.entity.SysRoleEntity;
import com.su60.quickboot.system.dos.SysRoleDo;
import com.su60.quickboot.system.entity.SysUserRoleEntity;
import com.su60.quickboot.system.mapper.SysRoleMapper;
import com.su60.quickboot.system.service.ISysRoleMenuService;
import com.su60.quickboot.system.service.ISysRoleService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import com.su60.quickboot.system.service.ISysUserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/08/07
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysRoleServiceImpl extends BaseServiceImpl2<SysRoleMapper, SysRoleEntity, SysRoleDo> implements ISysRoleService {

	private final ISysUserRoleService sysUserRoleService;
	private final ISysRoleMenuService sysRoleMenuService;

	@Override
	public List<SysRoleDo> listByUserId(Long userId) {

		List<SysUserRoleEntity> userRoleEntities = sysUserRoleService.list(new SysUserRoleEntity().setUserId(userId));
		if (CollectionUtil.isEmpty(userRoleEntities)) {
			return new ArrayList<>();
		}
		// 角色id集合
		return BeanConvertUtils.convertListTo(this.listByIds(userRoleEntities.stream().map(SysUserRoleEntity::getRoleId).toList()), SysRoleDo::new);
	}

	@Override
	public boolean saveRole(SysRoleDo sysRoleDo) {
		// 角色名称唯一
		Assert.isTrue(!this.exists(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getRoleName, sysRoleDo.getRoleName())), 100007);
		// 角色权限字符串唯一
		Assert.isTrue(!this.exists(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getRoleKey, sysRoleDo.getRoleKey())), 100008);

		// 保存角色信息
		SysRoleEntity sysRoleEntity = BeanConvertUtils.convertTo(sysRoleDo, SysRoleEntity::new);

		super.save(sysRoleEntity);
		// 保存角色菜单关联关系

		sysRoleMenuService.save(sysRoleEntity.getId(), sysRoleDo.getMenuIds());
		return true;
	}

	@Override
	public boolean delete(List<Long> ids) {
		// 删除角色跟菜单的关联关系
		sysRoleMenuService.delete(ids);
		return this.removeByIds(ids);
	}

	@Override
	public List<SysRoleEntity> list(SysRoleEntity sysRoleEntity) {
		return this.list(new LambdaQueryWrapper<>(sysRoleEntity));
	}

	@Override
	public List<Long> checkedKeys(Long roleId) {
		SysRoleEntity sysRole = super.getById(roleId);

		return this.baseMapper.selectMenuListByRoleId(sysRole.getId(), sysRole.getMenuCheckStrictly());
	}

	@Override
	public boolean updateRole(SysRoleDo sysRoleDo) {
		// 判断角色名称唯一

		Assert.isTrue(!this.exists(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getRoleName, sysRoleDo.getRoleName()).ne(SysRoleEntity::getId, sysRoleDo.getId())), 100007);
		// 判断角色权限唯一
		Assert.isTrue(!this.exists(new LambdaQueryWrapper<SysRoleEntity>().eq(SysRoleEntity::getRoleKey, sysRoleDo.getRoleKey()).ne(SysRoleEntity::getId, sysRoleDo.getId())), 100008);

		SysRoleEntity sysRoleEntity = BeanConvertUtils.convertTo(sysRoleDo, SysRoleEntity::new);
		// 角色关联的菜单
		sysRoleMenuService.save(sysRoleEntity.getId(), sysRoleDo.getMenuIds());
		return super.updateById(sysRoleEntity);

	}

	@Override
	public List<Long> listRoleIdUserId(Long userId) {
		return sysUserRoleService.list(new SysUserRoleEntity().setUserId(userId)).stream().map(a -> a.getRoleId()).distinct().toList();
	}

	@Override
	public void saveUserRoles(Long userId, List<Long> roleIds) {
		sysUserRoleService.saveUserRoles(userId, roleIds);
	}

	@Override
	public List<SysRoleDo> listAll(SysRoleDo sysRoleDo) {
		List<SysRoleEntity> list = super.list(new LambdaQueryWrapper<SysRoleEntity>(BeanConvertUtils.convertTo(sysRoleDo, SysRoleEntity.class)));
		return BeanConvertUtils.convertListTo(list, SysRoleDo::new);
	}
}

