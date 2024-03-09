package io.github.rainsoil.fastboot.job.entity;

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
    * 定时任务表
 * </p>
 *
 * @author luyanan
 * @since 2024-01-31
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_job")
@ApiModel(value = "ScheduleJob对象", description = "定时任务表")
public class ScheduleJob extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /**
    * id
    */

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
    * spring bean的名称
    */

    @ApiModelProperty("spring bean的名称")
    @TableField("bean_name")
    private String beanName;
    /**
    * 定时任务的参数
    */

    @ApiModelProperty("定时任务的参数")
    @TableField("params")
    private String params;
    /**
    * 定时任务表达式
    */

    @ApiModelProperty("定时任务表达式")
    @TableField("cron_expression")
    private String cronExpression;
    /**
    * 状态(0:暂停,1:正常)
    */

    @ApiModelProperty("状态(0:暂停,1:正常)")
    @TableField("status")
    private String status;
    /**
    * 备注
    */

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
}
