package io.github.rainsoil.fastboot.job.init;

import io.github.rainsoil.fastboot.job.entity.ScheduleJob;
import io.github.rainsoil.fastboot.job.service.IScheduleJobService;
import io.github.rainsoil.fastboot.job.utils.ScheduleUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 定时任务启动的时候加载
 *
 * @author luyanan
 * @since 2024/03/18
 **/
@RequiredArgsConstructor
@Component
public class JobCommandLineRunner implements CommandLineRunner {

	private final Scheduler scheduler;

	private final IScheduleJobService scheduleJobService;

	@Override
	public void run(String... args) throws Exception {

		for (ScheduleJob scheduleJob : scheduleJobService.list()) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getId());
			//如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}
}
