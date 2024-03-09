package io.github.rainsoil.fastboot.job.task;

/**
 * 定时任务接口类
 *
 * @author luyanan
 * @since 2024/02/03
 **/
public interface ITask {


	/**
	 * 定时任务执行方法
	 *
	 * @param params 参数
	 * @return
	 * @since 2024/02/03
	 */
	void run(String params);

}
