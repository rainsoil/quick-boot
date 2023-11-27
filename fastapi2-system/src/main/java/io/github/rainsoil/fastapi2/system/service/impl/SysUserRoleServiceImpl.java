package io.github.rainsoil.fastapi2.system.service.impl;

import io.github.rainsoil.fastapi2.system.entity.SysUserRole;
import io.github.rainsoil.fastapi2.system.mapper.SysUserRoleMapper;
import io.github.rainsoil.fastapi2.system.service.ISysUserRoleService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
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

}
