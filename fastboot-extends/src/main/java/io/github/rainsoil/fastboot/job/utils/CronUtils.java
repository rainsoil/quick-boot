package io.github.rainsoil.fastboot.job.utils;

import io.github.rainsoil.fastboot.job.exception.JobException;
import lombok.experimental.UtilityClass;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.util.Date;

/**
 * 定时任务表达式工具类
 *
 * @author luyanan
 * @since 2024/02/03
 **/
@UtilityClass
public class CronUtils {


	/**
	 * 校验表达式是否有效
	 *
	 * @param cronExpression 表达式
	 * @return
	 * @since 2024/02/03
	 */
	public static boolean isValid(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}


	/**
	 * 获取表达式的下一次执行时间
	 *
	 * @param cronExpression 定时任务表达式
	 * @return
	 * @since 2024/02/03
	 */
	public Date getNextExecution(String cronExpression) {
		CronExpression cron = null;
		try {
			cron = new CronExpression(cronExpression);
		} catch (ParseException e) {
			throw new JobException(e.getLocalizedMessage());
		}
		return cron.getNextValidTimeAfter(new Date(System.currentTimeMillis()));

	}
}
