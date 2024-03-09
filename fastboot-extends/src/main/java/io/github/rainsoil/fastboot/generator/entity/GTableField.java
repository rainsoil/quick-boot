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

/**
 * <p>
 * 表字段实体类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("g_table_field")
@ApiModel(value = "GTableField对象", description = "表字段实体类")
public class GTableField extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 表id
	 */

	@ApiModelProperty("表id")
	@TableField("table_id")
	private Long tableId;
	/**
	 * 表名
	 */

	@ApiModelProperty("表名")
	@TableField("table_name")
	private String tableName;
	/**
	 * 字段名称
	 */

	@ApiModelProperty("字段名称")
	@TableField("column_name")

	@NotBlank(message = "字段名称不能为空", groups = ValidatorGroup1.class)
	private String columnName;
	/**
	 * 字段类型
	 */

	@ApiModelProperty("字段类型")
	@TableField("column_type")
	@NotBlank(message = "字段类型不能为空", groups = ValidatorGroup1.class)
	private String columnType;
	/**
	 * 字段注释
	 */

	@ApiModelProperty("字段注释")
	@TableField("column_comment")
	@NotBlank(message = "字段注释不能为空", groups = ValidatorGroup1.class)
	private String columnComment;
	/**
	 * 属性名
	 */

	@ApiModelProperty("属性名")
	@TableField("attr_name")
	@NotBlank(message = "属性名不能为空", groups = ValidatorGroup1.class)
	private String attrName;
	/**
	 * 属性类型
	 */

	@ApiModelProperty("属性类型")
	@TableField("attr_type")
	@NotBlank(message = "属性类型不能为空", groups = ValidatorGroup1.class)
	private String attrType = "string";
	/**
	 * 是否主键(0:是,1:否)
	 */

	@ApiModelProperty("是否主键(0:是,1:否)")
	@TableField("is_pk")
	@NotBlank(message = "是否包含主键开关不能为空", groups = ValidatorGroup1.class)
	private String isPk;
	/**
	 * 是否必填(0:是,1:否)
	 */

	@ApiModelProperty("是否必填(0:是,1:否)")
	@TableField("is_required")
	@NotBlank(message = "是否必填开关不能为空", groups = ValidatorGroup1.class)
	private String isRequired;
	/**
	 * 页面类型(PAGE_TYPE)
	 */

	@ApiModelProperty("页面类型(PAGE_TYPE)")
	@TableField("page_type")
	private String pageType;
	/**
	 * 字典type
	 */

	@ApiModelProperty("字典type")
	@TableField("dict_type")
	private String dictType;
	/**
	 * 是否为搜索项(0:是,1:否)
	 */

	@ApiModelProperty("是否为搜索项(0:是,1:否)")
	@TableField("is_search")
	private String isSearch ;

	/**
	 * 接口url
	 *
	 * @since 2024/01/23
	 */
	@ApiModelProperty(value = "接口url")
	private String dicUrl;


	/**
	 * 表单编辑的时候是否显示
	 *
	 * @since 2024/01/27
	 */
	@ApiModelProperty(value = "表单编辑的时候是否显示")
	private String editDisplay ;

	/**
	 * 表单新增的时候是否显示
	 *
	 * @since 2024/01/27
	 */
	@ApiModelProperty(value = "表单新增的时候是否显示")
	private String addDisplay ;


	/**
	 * 表单是否显示
	 *
	 * @since 2024/01/27
	 */
	@ApiModelProperty(value = "表单是否显示")
	private String display ;

	/**
	 * 列表是否显示
	 *
	 * @since 2024/01/27
	 */
	@ApiModelProperty(value = "列表是否显示")
	private String hide ;
}
