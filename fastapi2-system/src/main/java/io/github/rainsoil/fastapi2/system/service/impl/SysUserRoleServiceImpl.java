package io.github.rainsoil.fastapi2.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastapi2.system.entity.SysUserRole;
import io.github.rainsoil.fastapi2.system.mapper.SysUserRoleMapper;
import io.github.rainsoil.fastapi2.system.service.ISysUserRoleService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysUserRoleServiceImpl extends BaseServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

	@Override
	public void saveRole(Long userId, List<Long> roleIds) {

		this.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
		if (CollectionUtil.isNotEmpty(roleIds)) {
			List<SysUserRole> sysUserRoles = roleIds.stream().map(a -> {
				return new SysUserRole()
						.setRoleId(a)
						.setUserId(userId);
			}).collect(Collectors.toList());

			this.saveBatch(sysUserRoles);
		}
	}
}
