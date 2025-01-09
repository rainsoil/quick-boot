package com.su60.quickboot.system.service;

import com.su60.quickboot.system.entity.SysUserEntity;
import com.su60.quickboot.system.dos.SysUserDo;
import com.su60.quickboot.data.mybatisplus.IBaseService2;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2024/08/07
 */

public interface ISysUserService extends IBaseService2<SysUserEntity, SysUserDo> {

	/**
	 * 根据用户名查询
	 *
	 * @param username 用户名
	 * @return 用户信息
	 * @since 2024/8/7
	 */
	SysUserDo findByUserName(String username);

	/**
	 * 用户信息保存
	 *
	 * @param sysUserDo 用户信息
	 * @return 是否成功
	 * @since 2024/10/14
	 */
	Boolean saveUser(SysUserDo sysUserDo);


	/**
	 * 修改用户信息
	 *
	 * @param sysUserDo 用户信息
	 * @return 是否成功
	 * @since 2024/10/14
	 */
	Boolean updateUser(SysUserDo sysUserDo);

}
