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
    * 系统菜单表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_menu")
@ApiModel(value = "SysMenu对象", description = "系统菜单表")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 菜单名称
    */

    @ApiModelProperty("菜单名称")
    @TableField("menu_name")
    private String menuName;
    /**
    * 权限
    */

    @ApiModelProperty("权限")
    @TableField("permission")
    private String permission;
    /**
    * 菜单路径
    */

    @ApiModelProperty("菜单路径")
    @TableField("path")
    private String path;
    /**
    * 父菜单id
    */

    @ApiModelProperty("父菜单id")
    @TableField("parent_id")
    private Long parentId;
    /**
    * icon
    */

    @ApiModelProperty("icon")
    @TableField("icon")
    private String icon;
    /**
    * 排序
    */

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;
    /**
    * 保持活跃(1:不,0:保持)
    */

    @ApiModelProperty("保持活跃(1:不,0:保持)")
    @TableField("keep_alive")
    private String keepAlive;
    /**
    * 菜单类型(1:菜单,2:按钮)
    */

    @ApiModelProperty("菜单类型(1:菜单,2:按钮)")
    @TableField("type")
    private String type;
    /**
    * 是否外链(1:否,0:是)
    */

    @ApiModelProperty("是否外链(1:否,0:是)")
    @TableField("blank")
    private String blank;
}
