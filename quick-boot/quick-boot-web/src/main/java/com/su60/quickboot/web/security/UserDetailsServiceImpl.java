package com.su60.quickboot.web.security;

import com.su60.quickboot.common.bean.BeanConvertUtils;
import com.su60.quickboot.core.security.LoginUser;
import com.su60.quickboot.system.dos.SysRoleDo;
import com.su60.quickboot.system.dos.SysUserDo;
import com.su60.quickboot.system.service.ISysMenuService;
import com.su60.quickboot.system.service.ISysRoleService;
import com.su60.quickboot.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户信息获取接口实现类
 *
 * @author luyanan
 * @since 2024/06/30
 **/
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


	@Autowired
	@Lazy
	private ISysUserService sysUserService;

	@Lazy
	@Autowired
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysMenuService sysMenuService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("查询用户:{}", username);
		SysUserDo sysUserDo = sysUserService.findByUserName(username);
		if (null == sysUserDo) {
			throw new UsernameNotFoundException("用户不存在");
		}
		if (sysUserDo.getStatus().equals("1")) {
			throw new UsernameNotFoundException("用户被禁用");
		}

		LoginUser loginUser = BeanConvertUtils.convertTo(sysUserDo, LoginUser::new);
		loginUser.setUsername(sysUserDo.getUserName());
		loginUser.setNickname(sysUserDo.getNickName());
		loginUser.setGender(sysUserDo.getSex());
		loginUser.setAvatar(sysUserDo.getAvatar());
		loginUser.setStatus(sysUserDo.getStatus());
		// 查询角色
		List<SysRoleDo> sysRoleDos = sysRoleService.listByUserId(sysUserDo.getId());
		List<Long> roleIds = sysRoleDos.stream().map(SysRoleDo::getId).toList();
		// 角色编码
		List<String> roleKeys = sysRoleDos.stream().map(SysRoleDo::getRoleKey).toList();

		loginUser.setRoleIds(roleIds);
		loginUser.setRoles(roleKeys);
		// 查询菜单权限
		List<String> perms = sysMenuService.listPermsByRoleIds(roleIds);
//		if (loginUser.isAdmin()) {
//			perms.add("*:*:*");
//		}
		loginUser.setPerms(perms);
		return loginUser;
	}


}
