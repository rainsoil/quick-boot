package io.github.rainsoil.fastboot.system.service;

import io.github.rainsoil.fastboot.system.entity.SysRole;
import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;

import java.util.List;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
public interface ISysRoleService extends IBaseService<SysRole> {


	/**
	 * 根据用户id查询关联的角色列表
	 *
	 * @param userId 用户id
	 * @return 角色列表
	 * @since 2023/11/30
	 */
	List<SysRole> listByUserId(Long userId);
}
