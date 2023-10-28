package io.github.rainsoil.fastapi2.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 *
 * @author luyanan
 * @since 2023/09/27
 **/
@Getter
@Setter
@Accessors(chain = true)
public class BaseEntity implements Serializable {


    /**
     * 创建人
     */
    @Hidden
    @Schema(description = "创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 创建时间
     */

    @Schema(description = "创建时间")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新人
     */
    @Hidden
    @Schema(description = "更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 删除标识
     */

    @Hidden
    @Schema(description = "删除标识")
    @TableField("del_flag")
    @TableLogic
    private String delFlag;
}
