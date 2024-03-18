package io.github.rainsoil.fastboot.job.utils;

import io.github.rainsoil.fastboot.common.spring.SpringContextHolder;
import io.github.rainsoil.fastboot.core.enums.CommonEnums;
import io.github.rainsoil.fastboot.job.entity.ScheduleJob;
import io.github.rainsoil.fastboot.job.entity.ScheduleJobLog;
import io.github.rainsoil.fastboot.job.service.IScheduleJobLogService;
import io.github.rainsoil.fastboot.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 定时任务
 *
 * @author luyanan
 * @since 2024/02/01
 **/
@Slf4j
public class ScheduleJobBean extends QuartzJobBean {
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		ScheduleJob scheduleJob = (ScheduleJob) context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);

		// 执行数据库日志保存
		ScheduleJobLog scheduleJobLog = new ScheduleJobLog();
		scheduleJobLog.setJobId(scheduleJobLog.getJobId());
		scheduleJobLog.setBeanName(scheduleJob.getBeanName());
		scheduleJobLog.setParams(scheduleJob.getParam());
		scheduleJobLog.setCreateTime(new Date());
		scheduleJobLog.setStartTime(new Date());

		//任务开始
		long startTime =
				System.currentTimeMillis();

		try {
			log.debug("任务开始执行,任务ID:{}", scheduleJob.getId());

			Object bean = SpringContextHolder.getBean(scheduleJob.getBeanName());
			if (!(bean instanceof ITask)) {
				throw new JobExecutionException("bean must implements ITask");
			}
			ITask task = (ITask) bean;
			task.run(scheduleJob.getParam());
//			Method method = bean.getClass().getDeclaredMethod("run", String.class);
//			method.invoke(method, scheduleJob.getParams());

			long times = System.currentTimeMillis() - startTime;
			scheduleJobLog.setConsuming((int) times);
			//任务状态
			scheduleJobLog.setStatus(CommonEnums.FLAG_YES.getValue());
			log.debug("定时任务执行结束,任务id:{},总共耗时:{}", scheduleJob.getId(), times);
		} catch (Exception e) {

			log.error("定时任务执行失败,任务id:{}", scheduleJob.getId(), e);
			long times = System.currentTimeMillis() - startTime;
			scheduleJobLog.setConsuming((int) times);

			scheduleJobLog.setStatus(CommonEnums.FLAG_NO.getValue());
			scheduleJobLog.setError(e.getLocalizedMessage());
		} finally {
			scheduleJobLog.setEndTime(new Date());
			IScheduleJobLogService scheduleJobLogService = SpringContextHolder.getBean(IScheduleJobLogService.class);
			scheduleJobLogService.save(scheduleJobLog);
		}


	}
}
