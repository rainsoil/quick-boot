package io.github.rainsoil.fastboot.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.rainsoil.fastboot.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
/**
* <p>
    * 系统角色菜单关联表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_role_menu")
@ApiModel(value = "SysRoleMenu对象", description = "系统角色菜单关联表")
public class SysRoleMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 角色id
    */

    @ApiModelProperty("角色id")
    @TableField("role_id")
    private Long roleId;
    /**
    * 菜单id
    */

    @ApiModelProperty("菜单id")
    @TableField("menu_Id")
    private Long menuId;
}
