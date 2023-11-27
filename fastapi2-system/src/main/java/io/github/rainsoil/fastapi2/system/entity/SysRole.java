package io.github.rainsoil.fastapi2.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.rainsoil.fastapi2.core.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
/**
* <p>
    * 系统角色表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_role")
@Schema(name = "SysRole", description = "$!{table.comment}")
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 角色名称
    */

    @Schema(description = "角色名称")
    @TableField("role_name")
    private String roleName;
    /**
    * 角色编码
    */

    @Schema(description = "角色编码")
    @TableField("role_code")
    private String roleCode;
    /**
    * 备注
    */

    @Schema(description = "备注")
    @TableField("remark")
    private String remark;
}
