package io.github.rainsoil.fastboot.job.service.impl;

import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.job.entity.ScheduleJobLog;
import io.github.rainsoil.fastboot.job.mapper.ScheduleJobLogMapper;
import io.github.rainsoil.fastboot.job.service.IScheduleJobLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
/**
 * <p>
 * 定时任务日志表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-31
 */
@RequiredArgsConstructor
@Service
public class ScheduleJobLogServiceImpl extends BaseServiceImpl<ScheduleJobLogMapper, ScheduleJobLog> implements IScheduleJobLogService {

}
