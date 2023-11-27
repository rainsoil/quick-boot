package io.github.rainsoil.fastapi2.system.service.impl;

import io.github.rainsoil.fastapi2.system.entity.SysRole;
import io.github.rainsoil.fastapi2.system.mapper.SysRoleMapper;
import io.github.rainsoil.fastapi2.system.service.ISysRoleService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
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

}
