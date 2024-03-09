package io.github.rainsoil.fastboot.generator.entity;

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
 * 数据源管理
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("g_datasource")
@ApiModel(value = "GDatasource对象", description = "数据源管理")
public class GDatasource extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 数据库类型
	 */

	@ApiModelProperty("数据库类型")
	@TableField("db_type")
	private String dbType;
	/**
	 * 连接名
	 */

	@ApiModelProperty("连接名")
	@TableField("conn_name")
	private String connName;
	/**
	 * 连接的url
	 */

	@ApiModelProperty("连接的url")
	@TableField("conn_url")
	private String connUrl;
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
	 * 状态(0:启用,1:禁用)
	 */

	@ApiModelProperty("状态(0:启用,1:禁用)")
	@TableField("status")
	private String status;
}
