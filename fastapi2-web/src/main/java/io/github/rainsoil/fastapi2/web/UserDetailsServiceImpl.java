package io.github.rainsoil.fastapi2.web;

import io.github.rainsoil.fastapi2.core.user.LoginUser;
import io.github.rainsoil.fastapi2.system.entity.SysUser;
import io.github.rainsoil.fastapi2.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息查询
 *
 * @author luyanan
 * @since 2023/11/29
 **/
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final ISysUserService sysUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SysUser sysUser = sysUserService.findByUserName(username);
		if (null == sysUser) {
			throw new UsernameNotFoundException("用户不存在");
		}
		LoginUser loginUser = new LoginUser();
		loginUser.setUserId(sysUser.getId());
		loginUser.setPassword(sysUser.getPassword());
		loginUser.setUsername(sysUser.getUsername());
		return loginUser;
	}
}
