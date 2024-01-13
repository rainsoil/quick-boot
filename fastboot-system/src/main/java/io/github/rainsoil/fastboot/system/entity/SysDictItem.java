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
 * 字典项表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_dict_item")
@ApiModel(value = "SysDictItem对象", description = "字典项表")
public class SysDictItem extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 字典表id
	 */

	@ApiModelProperty("字典表id")
	@TableField("dict_id")
	private Long dictId;
	/**
	 * 字典值
	 */

	@ApiModelProperty("字典值")
	@TableField("value")
	private String value;
	/**
	 * 字典标签
	 */

	@ApiModelProperty("字典标签")
	@TableField("label")
	private String label;
	/**
	 * 字典类型
	 */

	@ApiModelProperty("字典类型")
	@TableField("type")
	private String type;
	/**
	 * 字典描述
	 */

	@ApiModelProperty("字典描述")
	@TableField("description")
	private String description;
	/**
	 * 排序
	 */

	@ApiModelProperty("排序")
	@TableField("sort")
	private Integer sort;
	/**
	 * 样式
	 */

	@ApiModelProperty("样式")
	@TableField("style")
	private String style;
}
