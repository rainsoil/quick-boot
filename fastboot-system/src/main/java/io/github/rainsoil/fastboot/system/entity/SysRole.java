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
 * 系统角色表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_role")
@ApiModel(value = "SysRole对象", description = "系统角色表")
public class SysRole extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 角色名称
	 */

	@ApiModelProperty("角色名称")
	@TableField(value = "role_name", condition = SqlCondition.LIKE)
	private String roleName;
	/**
	 * 角色编码
	 */

	@ApiModelProperty("角色编码")
	@TableField("role_code")
	private String roleCode;
	/**
	 * 备注
	 */

	@ApiModelProperty("备注")
	@TableField("remark")
	private String remark;


	/**
	 * 关联的菜单
	 *
	 * @since 2024/01/05
	 */

	@TableField(exist = false)
	private List<Long> menus;
}
