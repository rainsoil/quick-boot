package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastapi2.system.entity.SysRole;
import io.github.rainsoil.fastapi2.system.entity.SysUserRole;
import io.github.rainsoil.fastapi2.system.mapper.SysRoleMapper;
import io.github.rainsoil.fastapi2.system.service.ISysRoleService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastapi2.system.service.ISysUserRoleService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
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

	@Override
	public List<SysRole> listByUserId(Long userId) {

		List<SysUserRole> list = sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
		if (CollectionUtil.isEmpty(list)) {
			return new ArrayList<>();
		}
		return this.listByIds(list.stream().map(a -> a.getRoleId()).distinct().collect(Collectors.toList()));

	}
}
