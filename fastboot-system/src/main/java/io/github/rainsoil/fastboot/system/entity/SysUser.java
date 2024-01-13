package io.github.rainsoil.fastboot.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.github.rainsoil.fastboot.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "系统用户表")
public class SysUser extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 用户名
	 */

	@ApiModelProperty("用户名")
	@TableField(value = "username", condition = SqlCondition.LIKE)
	private String username;
	/**
	 * 密码
	 */

	@ApiModelProperty("密码")
	@TableField("password")
	private String password;
	/**
	 * 名称
	 */

	@ApiModelProperty("名称")
	@TableField("name")
	private String name;
	/**
	 * 手机号
	 */

	@ApiModelProperty("手机号")
	@TableField("phone")
	private String phone;

	/**
	 * 角色id
	 *
	 * @since 2024/01/06
	 */
	@TableField(exist = false)
	@ApiModelProperty(value = "角色id")
	private List<Long> roleIds;

}
