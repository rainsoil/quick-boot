package io.github.rainsoil.fastapi2.system.vo;

import io.github.rainsoil.fastapi2.system.entity.SysUser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用户信息Vo类
 *
 * @author luyanan
 * @since 2023/11/30
 **/
@Data
@Accessors(chain = true)
public class UserInfoVo implements Serializable {

	/**
	 * 用户信息
	 *
	 * @since 2023/11/30
	 */

	private SysUser userInfo;


	/**
	 * 角色编码
	 *
	 * @since 2023/11/30
	 */

	private String roles;


	/**
	 * 权限列表
	 *
	 * @since 2023/11/30
	 */

	private List<String> permission;
}
