package io.github.rainsoil.fastapi2.system.service.impl;

import io.github.rainsoil.fastapi2.system.entity.SysMenu;
import io.github.rainsoil.fastapi2.system.mapper.SysMenuMapper;
import io.github.rainsoil.fastapi2.system.service.ISysMenuService;
import io.github.rainsoil.fastapi2.common.data.mybatis.BaseServiceImpl;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
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

}
