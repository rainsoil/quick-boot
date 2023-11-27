package io.github.rainsoil.fastapi2.system.service.impl;

import io.github.rainsoil.fastapi2.system.entity.SysRoleMenu;
import io.github.rainsoil.fastapi2.system.mapper.SysRoleMenuMapper;
import io.github.rainsoil.fastapi2.system.service.ISysRoleMenuService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
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

}
