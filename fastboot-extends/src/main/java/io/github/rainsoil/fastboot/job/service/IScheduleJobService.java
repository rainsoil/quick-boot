package io.github.rainsoil.fastboot.job.service;

import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastboot.job.entity.ScheduleJob;

/**
 * <p>
 * 定时任务表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-31
 */
public interface IScheduleJobService extends IBaseService<ScheduleJob> {
	/**
	 * 立即执行
	 *
	 * @param jobId 任务id
	 * @return
	 * @since 2024/02/02
	 */
	void run(Long jobId);


	/**
	 * 暂停运行
	 *
	 * @param jobId 任务id
	 * @return
	 * @since 2024/02/02
	 */
	void pause(Long jobId);


	/**
	 * 恢复运行
	 *
	 * @param jobId 任务id
	 * @return
	 * @since 2024/02/02
	 */
	void resume(Long jobId);
}
