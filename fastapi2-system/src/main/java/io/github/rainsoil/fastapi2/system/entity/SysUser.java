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
    @TableField("username")
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
}
