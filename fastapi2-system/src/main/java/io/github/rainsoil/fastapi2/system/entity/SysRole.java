package io.github.rainsoil.fastapi2.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.rainsoil.fastapi2.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
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
    @TableField("role_name")
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
}
