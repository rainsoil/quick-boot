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
    * 系统用户表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_user")
@Schema(name = "SysUser", description = "$!{table.comment}")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 用户名
    */

    @Schema(description = "用户名")
    @TableField("username")
    private String username;
    /**
    * 密码
    */

    @Schema(description = "密码")
    @TableField("password")
    private String password;
    /**
    * 名称
    */

    @Schema(description = "名称")
    @TableField("name")
    private String name;
    /**
    * 手机号
    */

    @Schema(description = "手机号")
    @TableField("phone")
    private String phone;
}
