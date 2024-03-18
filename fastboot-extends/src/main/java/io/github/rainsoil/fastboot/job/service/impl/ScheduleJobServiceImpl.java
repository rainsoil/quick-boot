package io.github.rainsoil.fastboot.job.service.impl;

import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.job.entity.ScheduleJob;
import io.github.rainsoil.fastboot.job.enums.JobEnums;
import io.github.rainsoil.fastboot.job.exception.JobException;
import io.github.rainsoil.fastboot.job.mapper.ScheduleJobMapper;
import io.github.rainsoil.fastboot.job.service.IScheduleJobService;
import io.github.rainsoil.fastboot.job.utils.CronUtils;
import io.github.rainsoil.fastboot.job.utils.ScheduleUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 * 定时任务表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-31
 */
@RequiredArgsConstructor
@Service
public class ScheduleJobServiceImpl extends BaseServiceImpl<ScheduleJobMapper, ScheduleJob> implements IScheduleJobService {
	private final Scheduler scheduler;



	@Override
	public boolean save(ScheduleJob entity) {
		if (!CronUtils.isValid(entity.getCronExpression())) {
			throw new JobException(30007);
		}
		entity.setStatus(JobEnums.STATUS_NORMAL.getValue());
		boolean save = super.save(entity);
		try {
			ScheduleUtils.createScheduleJob(scheduler, entity);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new JobException(30001);
		}
		return save;
	}

	@Override
	public boolean updateById(ScheduleJob entity) {
		if (!CronUtils.isValid(entity.getCronExpression())) {
			throw new JobException(30007);
		}
		try {
			ScheduleUtils.updateScheduleJob(scheduler, entity);
		} catch (Exception e) {
			throw new JobException(30002);
		}
		return super.updateById(entity);
	}

	@Override
	public boolean removeById(Serializable id) {
		try {
			ScheduleUtils.deleteScheduleJob(scheduler, (Long) id);
		} catch (Exception e) {
			throw new JobException(30003);
		}
		return super.removeById(id);
	}

	@Override
	public void run(Long jobId) {
		ScheduleJob job = this.getById(jobId);
		try {
			ScheduleUtils.run(scheduler, job);
		} catch (Exception e) {
			throw new JobException(30004);
		}
	}

	@Override
	public void pause(Long jobId) {

		try {
			ScheduleUtils.pauseJob(scheduler, jobId);
		} catch (Exception e) {
			throw new JobException(30005);
		}
	}

	@Override
	public void resume(Long jobId) {

		try {
			ScheduleUtils.resumeJob(scheduler, jobId);
		} catch (Exception e) {
			throw new JobException(30006);
		}
	}
}
