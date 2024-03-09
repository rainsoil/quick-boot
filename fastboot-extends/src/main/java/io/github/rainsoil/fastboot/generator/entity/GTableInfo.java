package io.github.rainsoil.fastboot.generator.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.rainsoil.fastboot.common.validation.ValidatorGroup1;
import io.github.rainsoil.fastboot.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 表信息
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("g_table_info")
@ApiModel(value = "GTableInfo对象", description = "表信息")
public class GTableInfo extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@NotNull(message = "id不能为空", groups = ValidatorGroup1.class)
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 表名
	 */

	@ApiModelProperty("表名")
	@TableField("table_name")
	@NotBlank(message = "表名不能为空", groups = ValidatorGroup1.class)
	private String tableName;
	/**
	 * 实体类名称
	 */

	@ApiModelProperty("实体类名称")
	@TableField("entity_name")
	@NotBlank(message = "实体类名称不能为空", groups = ValidatorGroup1.class)
	private String entityName;
	/**
	 * 表注释
	 */

	@ApiModelProperty("表注释")
	@TableField("table_comment")
	@NotBlank(message = "注释不能为空", groups = ValidatorGroup1.class)
	private String tableComment;
	/**
	 * 包名称
	 */

	@ApiModelProperty("包名称")
	@TableField("package_name")
	@NotBlank(message = "包名称不能为空", groups = ValidatorGroup1.class)
	private String packageName;
	/**
	 * 作者
	 */

	@ApiModelProperty("作者")
	@TableField("auth")
	@NotBlank(message = "作者不能为空", groups = ValidatorGroup1.class)
	private String auth;
	/**
	 * 模块名称
	 */

	@ApiModelProperty("模块名称")
	@TableField("module_name")
	@NotBlank(message = "模块名称不能为空", groups = ValidatorGroup1.class)
	private String moduleName;
	/**
	 * 数据源id
	 */

	@ApiModelProperty("数据源id")
	@TableField("datasource_id")
	private Long datasourceId;

	/**
	 * 数据源名称
	 *
	 * @since 2024/01/21
	 */

	@TableField(exist = false)
	private String datasourceName;


	/**
	 * 表字段
	 *
	 * @since 2024/01/21
	 */
	@TableField(exist = false)

	@NotEmpty(message = "表字段不能为空", groups = ValidatorGroup1.class)
	private List<GTableField> tableFields;


	/**
	 * 是否有新增按钮
	 *
	 * @since 2024/01/27
	 */
	@ApiModelProperty(value = "是否有新增按钮")
	@NotBlank(message = "是否添加按钮开关不能为空", groups = ValidatorGroup1.class)
	private String addBtn ;


	/**
	 * 是否有编辑按钮
	 *
	 * @since 2024/01/27
	 */
	@NotBlank(message = "是否有编辑按钮开关不能为空", groups = ValidatorGroup1.class)
	@ApiModelProperty(value = "是否有编辑按钮")
	private String editBtn ;


	/**
	 * 是否有删除按钮
	 *
	 * @since 2024/01/27
	 */
	@NotBlank(message = "是否有删除按钮开关不能为空", groups = ValidatorGroup1.class)
	@ApiModelProperty(value = "是否有删除按钮")
	private String delBtn;


	/**
	 * 是否校验权限
	 *
	 * @since 2024/01/27
	 */
	@NotBlank(message = "是否权限校验的开关不能为空", groups = ValidatorGroup1.class)
	@ApiModelProperty(value = "是否校验权限")
	private String verifyPermissions;

	/**
	 * 接口前缀
	 *
	 * @since 2024/01/27
	 */
	@NotBlank(message = "接口前缀不能为空", groups = ValidatorGroup1.class)
	@ApiModelProperty(value = "接口前缀")
	private String interfacePrefix;
}
