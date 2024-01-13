package io.github.rainsoil.fastboot.system.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.common.exception.WarningException;
import io.github.rainsoil.fastboot.system.entity.SysUser;
import io.github.rainsoil.fastboot.system.entity.SysUserRole;
import io.github.rainsoil.fastboot.system.mapper.SysUserMapper;
import io.github.rainsoil.fastboot.system.service.ISysUserRoleService;
import io.github.rainsoil.fastboot.system.service.ISysUserService;
import io.github.rainsoil.fastboot.system.vo.ChangePasswordVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

	private final ISysUserRoleService sysUserRoleService;

	private final PasswordEncoder passwordEncoder;

	@Override
	public PageInfo<SysUser> page(PageRequest<SysUser> pageRequest) {
		return super.page(pageRequest, new PageHandler<SysUser>() {
			@Override
			public void recordsHandler(List<SysUser> records) {
				for (SysUser record : records) {
					record.setRoleIds(sysUserRoleService.list(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, record.getId())).stream().map(a -> a.getRoleId()).distinct().collect(Collectors.toList()));
				}

			}
		});
	}

	@Override
	public SysUser findByUserName(String username) {

		if (StrUtil.isBlank(username)) {
			return null;
		}
		SysUser sysUser = this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
		return sysUser;
	}

	@Override
	public void changePassword(ChangePasswordVo changePasswordVo) {
		if (StrUtil.isNotBlank(changePasswordVo.getNewpassword()) && StrUtil.isNotBlank(changePasswordVo.getNewpasswords()) && !changePasswordVo.getNewpassword().equals(changePasswordVo.getNewpasswords())) {
			throw new WarningException(10001);
		}
		SysUser sysUser = super.getById(changePasswordVo.getUserId());
		if (null == sysUser) {
			throw new WarningException(10002);
		}
		if (StrUtil.isNotBlank(changePasswordVo.getOldpassword())) {
			// 校验老密码是否一致
			boolean matches = passwordEncoder.matches(changePasswordVo.getOldpassword(), sysUser.getPassword());
			if (!matches) {
				throw new WarningException(10003);
			}
		}


		// 修改密码
		super.updateById(new SysUser()
				.setId(changePasswordVo.getUserId())
				.setPassword(passwordEncoder.encode(changePasswordVo.getNewpassword())));
	}

	@Override
	public void updateUserInfo(SysUser sysUser) {
		super.updateById(sysUser);
	}


	@Override
	public boolean save(SysUser entity) {
		// 这里对密码进行加密处理
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		boolean save = super.save(entity);
		sysUserRoleService.saveRole(entity.getId(), entity.getRoleIds());
		return save;

	}


	@Override
	public boolean updateById(SysUser entity) {
		// 不修改密码
		entity.setPassword(null);
		boolean b = super.updateById(entity);
		sysUserRoleService.saveRole(entity.getId(), entity.getRoleIds());

		return b;
	}


	@Override
	public boolean removeByIds(Collection<?> list) {
		boolean b = super.removeByIds(list);
		// 删除角色关联
		sysUserRoleService.remove(new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, list));
		return b;
	}


}
