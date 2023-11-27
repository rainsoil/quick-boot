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
    * 字典项表
 * </p>
 *
 * @author luyanan
 * @since 2023-11-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_dict_item")
@Schema(name = "SysDictItem", description = "$!{table.comment}")
public class SysDictItem extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * 字典表id
    */

    @Schema(description = "字典表id")
    @TableField("dict_id")
    private Long dictId;
    /**
    * 字典值
    */

    @Schema(description = "字典值")
    @TableField("value")
    private String value;
    /**
    * 字典标签
    */

    @Schema(description = "字典标签")
    @TableField("label")
    private String label;
    /**
    * 字典类型
    */

    @Schema(description = "字典类型")
    @TableField("type")
    private String type;
    /**
    * 字典描述
    */

    @Schema(description = "字典描述")
    @TableField("description")
    private String description;
    /**
    * 排序
    */

    @Schema(description = "排序")
    @TableField("sort")
    private Integer sort;
    /**
    * 样式
    */

    @Schema(description = "样式")
    @TableField("style")
    private String style;
}
