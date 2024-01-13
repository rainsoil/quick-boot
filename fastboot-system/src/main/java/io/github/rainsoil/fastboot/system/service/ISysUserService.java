package io.github.rainsoil.fastboot.system.service;

import io.github.rainsoil.fastboot.system.entity.SysUser;
import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastboot.system.vo.ChangePasswordVo;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
public interface ISysUserService extends IBaseService<SysUser> {

	/**
	 * 根据用户名获取用户
	 *
	 * @param username 用户名
	 * @return 用户
	 * @since 2023/11/29
	 */
	SysUser findByUserName(String username);

	/**
	 * 修改密码
	 *
	 * @param changePasswordVo 密码vo类
	 * @return
	 * @since 2024/01/11
	 */
	void changePassword(ChangePasswordVo changePasswordVo);

	/**
	 * 修改个人信息
	 *
	 * @param sysUser 用户信息
	 * @return
	 * @since 2024/01/12
	 */
	void updateUserInfo(SysUser sysUser);
}
