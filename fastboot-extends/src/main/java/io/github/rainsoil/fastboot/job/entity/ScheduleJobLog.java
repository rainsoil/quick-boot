package io.github.rainsoil.fastboot.job.entity;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.rainsoil.fastboot.core.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * <p>
 * 定时任务日志表
 * </p>
 *
 * @author luyanan
 * @since 2024-01-31
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("schedule_job_log")
@ApiModel(value = "ScheduleJobLog对象", description = "定时任务日志表")
public class ScheduleJobLog extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/**
	 * id
	 */

	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 定时任务的id
	 */

	@ApiModelProperty("定时任务的id")
	@TableField("job_id")
	private Long jobId;
	/**
	 * spring bean的名称
	 */

	@ApiModelProperty("spring bean的名称")
	@TableField("bean_name")
	private String beanName;
	/**
	 * 参数
	 */

	@ApiModelProperty("参数")
	@TableField("params")
	private String params;
	/**
	 * 状态(0：失败  1：成功)
	 */

	@ApiModelProperty("状态(0：失败  1：成功)")
	@TableField("status")
	private String status;
	/**
	 * 错误信息
	 */

	@ApiModelProperty("错误信息")
	@TableField("error")
	private String error;
	/**
	 * 耗时
	 */

	@ApiModelProperty("耗时")
	@TableField("consuming")
	private Integer consuming;


	/**
	 * 任务开始执行时间
	 *
	 * @since 2024/03/18
	 */
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_MINUTE_PATTERN)
	@ApiModelProperty(value = "任务开始执行时间")
	private Date startTime;


	/**
	 * 任务结束执行时间
	 *
	 * @since 2024/03/18
	 */
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_MINUTE_PATTERN)
	@ApiModelProperty(value = "任务结束执行时间")
	private Date endTime;
}
