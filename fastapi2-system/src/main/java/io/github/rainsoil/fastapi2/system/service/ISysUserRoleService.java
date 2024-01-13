package io.github.rainsoil.fastapi2.system.service;

import io.github.rainsoil.fastapi2.system.entity.SysUserRole;
import io.github.rainsoil.fastapi2.common.data.mybatis.IBaseService;

import java.util.List;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
public interface ISysUserRoleService extends IBaseService<SysUserRole> {


	/**
	 * 保存用户角色信息
	 *
	 * @param userId  用户id
	 * @param roleIds 角色列表
	 * @return
	 * @since 2024/01/06
	 */
	void saveRole(Long userId, List<Long> roleIds);
}
