package io.github.rainsoil.fastapi2.web;

import cn.hutool.core.util.StrUtil;
import io.github.rainsoil.fastapi2.common.security.ClientProperties;
import io.github.rainsoil.fastapi2.common.security.ClientUtils;
import io.github.rainsoil.fastapi2.core.user.LoginUser;
import io.github.rainsoil.fastapi2.system.entity.SysMenu;
import io.github.rainsoil.fastapi2.system.entity.SysRole;
import io.github.rainsoil.fastapi2.system.entity.SysUser;
import io.github.rainsoil.fastapi2.system.service.ISysMenuService;
import io.github.rainsoil.fastapi2.system.service.ISysRoleService;
import io.github.rainsoil.fastapi2.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息查询
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Value("${sm4.key}")
	private String sm4Key;
	private final ISysUserService sysUserService;

	private final ISysRoleService sysRoleService;

	private final ISysMenuService sysMenuService;

	@Autowired
	private ClientUtils clientUtils;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = clientUtils.paramHandler(username);
		ClientProperties.Client client = clientUtils.getClientId();
		SysUser sysUser = sysUserService.findByUserName(username);
		if (null == sysUser) {
			throw new UsernameNotFoundException("用户不存在");
		}
		// 获取角色
		List<SysRole> sysRoles = sysRoleService.listByUserId(sysUser.getId());
		// 获取菜单列表
		List<SysMenu> menuList = sysMenuService.listByRoleId(sysRoles.stream().map(a -> a.getId()).distinct().collect(Collectors.toList()));
		LoginUser loginUser = new LoginUser();
		loginUser.setUserId(sysUser.getId());
		loginUser.setPassword(sysUser.getPassword());
		loginUser.setUsername(sysUser.getUsername());
		loginUser.setNickName(sysUser.getName());
		loginUser.setClientId(client.getName());
		loginUser.setRoles(sysRoles.stream().map(a -> a.getId()).distinct().collect(Collectors.toList()));
		loginUser.setRoleCodes(sysRoles.stream().map(a -> a.getRoleCode()).distinct().collect(Collectors.toList()));
		loginUser.setAuths(menuList.stream().filter(a -> StrUtil.isNotBlank(a.getPermission())).map(a -> a.getPermission()).distinct().collect(Collectors.toList()));
		return loginUser;
	}
}
