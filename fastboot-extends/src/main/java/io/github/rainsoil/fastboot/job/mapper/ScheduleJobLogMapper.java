package io.github.rainsoil.fastboot.job.mapper;

import io.github.rainsoil.fastboot.common.data.mybatis.BaseBaseMapper;
import io.github.rainsoil.fastboot.job.entity.ScheduleJobLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 定时任务日志表 Mapper 接口
 * </p>
 *
 * @author luyanan
 * @since 2024-01-31
 */
@Mapper
public interface ScheduleJobLogMapper extends BaseBaseMapper<ScheduleJobLog> {

}
