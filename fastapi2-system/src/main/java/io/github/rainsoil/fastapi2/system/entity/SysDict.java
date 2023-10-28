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
 * 字段表
 * </p>
 *
 * @author luyanan
 * @since 2023-09-27
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_dict")
@Schema(name = "SysDict", description = "$!{table.comment}")
public class SysDict extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */

    @Schema(description = "id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
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
}
