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
    * 用户角色关联表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user_role")
@Schema(name = "SysUserRole", description = "$!{table.comment}")
public class SysUserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 用户id
    */

    @Schema(description = "用户id")
    @TableField("user_id")
    private Long userId;
    /**
    * 角色id
    */

    @Schema(description = "角色id")
    @TableField("role_id")
    private Long roleId;
}
