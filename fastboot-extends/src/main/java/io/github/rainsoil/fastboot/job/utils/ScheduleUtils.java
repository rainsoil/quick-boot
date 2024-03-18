package io.github.rainsoil.fastboot.job.utils;

import io.github.rainsoil.fastboot.job.entity.ScheduleJob;
import io.github.rainsoil.fastboot.job.enums.JobEnums;
import lombok.experimental.UtilityClass;
import org.quartz.*;

/**
 * 定时任务的工具类
 *
 * @author luyanan
 * @since 2024/01/31
 **/
@UtilityClass
public class ScheduleUtils {

	/**
	 * 任务名称前缀
	 *
	 * @since 2024/01/31
	 */

	private static final String JOB_NAME = "TASK_";


	/**
	 * 任务调度参数key
	 *
	 * @since 2024/01/31
	 */

	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";


	/**
	 * 获取触发器的key
	 *
	 * @param jobId 任务id
	 * @return
	 * @since 2024/01/31
	 */
	public TriggerKey getTriggerKey(Long jobId) {
		return TriggerKey.triggerKey(JOB_NAME + jobId);
	}


	/**
	 * 获取jobKey
	 *
	 * @param jobId 任务id
	 * @return
	 * @since 2024/01/31
	 */
	public JobKey getJobKey(Long jobId) {
		return JobKey.jobKey(JOB_NAME + jobId);
	}

	/**
	 * 获取表达式触发器
	 *
	 * @param scheduler 定时任务执行器
	 * @param jobId     任务id
	 * @return
	 * @since 2024/01/31
	 */
	public CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) throws SchedulerException {
		return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
	}

	/**
	 * 创建定时任务
	 *
	 * @param scheduler   定时任务执行器
	 * @param scheduleJob 任务信息
	 * @return
	 * @since 2024/01/31
	 */
	public void createScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {


		JobDetail jobDetail = JobBuilder.newJob(ScheduleJobBean.class)
				.withIdentity(getJobKey(scheduleJob.getId())).build();

		//表达式调度构建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
				.withMisfireHandlingInstructionDoNothing();

		CronTrigger cronTrigger = TriggerBuilder
				.newTrigger().withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(cronScheduleBuilder)
				.build();
		// 放入参数
		jobDetail.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);

		scheduler.scheduleJob(jobDetail, cronTrigger);

		// 如果为暂停
		if (scheduleJob.getStatus().equals(JobEnums.STATUS_PAUSE.getValue())) {

			pauseJob(scheduler, scheduleJob.getId());
		}
	}

	/**
	 * 暂停任务
	 *
	 * @param scheduler 任务执行器
	 * @param jobId     任务id
	 * @return
	 * @since 2024/02/01
	 */
	public void pauseJob(Scheduler scheduler, Long jobId) throws SchedulerException {
		scheduler.pauseJob(getJobKey(jobId));
	}


	/**
	 * 立即执行任务
	 *
	 * @param scheduler   任务执行器
	 * @param scheduleJob 定时任务信息
	 * @return
	 * @since 2024/02/01
	 */
	public void run(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {

		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put(JOB_PARAM_KEY, scheduleJob);
		scheduler.triggerJob(getJobKey(scheduleJob.getId()));
	}


	/**
	 * 恢复任务
	 *
	 * @param scheduler 任务执行器
	 * @param jobId     任务id
	 * @return
	 * @since 2024/02/02
	 */
	public void resumeJob(Scheduler scheduler, Long jobId) throws SchedulerException {
		scheduler.resumeJob(getJobKey(jobId));
	}

	/**
	 * 删除定时任务
	 *
	 * @param scheduler 任务执行器
	 * @param jobId     任务id
	 * @return
	 * @since 2024/02/02
	 */
	public void deleteScheduleJob(Scheduler scheduler, Long jobId) throws SchedulerException {
		scheduler.deleteJob(getJobKey(jobId));
	}

	/**
	 * 修改定时任务
	 *
	 * @param scheduler   任务执行器
	 * @param scheduleJob 任务
	 * @return
	 * @since 2024/02/03
	 */
	public void updateScheduleJob(Scheduler scheduler, ScheduleJob scheduleJob) throws SchedulerException {
		TriggerKey triggerKey =
				getTriggerKey(scheduleJob.getId());
		//表达式调度构建器
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
				.withMisfireHandlingInstructionDoNothing();

		CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());

		//按新的cronExpression表达式重新构建trigger
		trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

		//参数
		trigger.getJobDataMap().put(JOB_PARAM_KEY, scheduleJob);

		scheduler.rescheduleJob(triggerKey, trigger);

		//暂停任务
		if (scheduleJob.getStatus() == JobEnums.STATUS_PAUSE.getValue()) {
			pauseJob(scheduler, scheduleJob.getId());
		}


	}

}
