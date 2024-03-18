package io.github.rainsoil.fastboot.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 测试定时任务
 *
 * @author luyanan
 * @since 2024/02/03
 **/
@Slf4j
@Component("testTask")
public class TestTask implements ITask {
	@Override
	public void run(String params) {

		log.debug("TestTask开始执行,参数为:{}", params);
	}
}
