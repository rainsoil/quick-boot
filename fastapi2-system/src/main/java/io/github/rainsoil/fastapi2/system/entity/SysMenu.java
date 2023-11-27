package io.github.rainsoil.fastapi2.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.rainsoil.fastapi2.core.BaseEntity;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
/**
* <p>
    * 系统菜单表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_menu")
@Schema(name = "SysMenu", description = "$!{table.comment}")
public class SysMenu extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 菜单名称
    */

    @Schema(description = "菜单名称")
    @TableField("menu_name")
    private String menuName;
    /**
    * 权限
    */

    @Schema(description = "权限")
    @TableField("permission")
    private String permission;
    /**
    * 菜单路径
    */

    @Schema(description = "菜单路径")
    @TableField("path")
    private String path;
    /**
    * 父菜单id
    */

    @Schema(description = "父菜单id")
    @TableField("parent_id")
    private Long parentId;
    /**
    * icon
    */

    @Schema(description = "icon")
    @TableField("icon")
    private String icon;
    /**
    * 排序
    */

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;
    /**
    * 保持活跃(1:不,0:保持)
    */

    @Schema(description = "保持活跃(1:不,0:保持)")
    @TableField("keep_alive")
    private String keepAlive;
    /**
    * 菜单类型(1:菜单,2:按钮)
    */

    @Schema(description = "菜单类型(1:菜单,2:按钮)")
    @TableField("type")
    private String type;
    /**
    * 是否外链(1:否,0:是)
    */

    @Schema(description = "是否外链(1:否,0:是)")
    @TableField("blank")
    private String blank;
}
