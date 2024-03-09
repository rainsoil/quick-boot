package io.github.rainsoil.fastboot.job.controller;

import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.job.entity.ScheduleJob;
import io.github.rainsoil.fastboot.job.service.IScheduleJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务表 前端控制器
 *
 * @author luyanan
 * @since 2024-01-31
 */
@Api(description = "定时任务表")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/job/scheduleJob")
public class ScheduleJobController {

	private final IScheduleJobService iScheduleJobService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2024-01-31
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<ScheduleJob> page(@RequestBody PageRequest<ScheduleJob> pageRequest) {
		PageInfo<ScheduleJob> pageInfo = iScheduleJobService.page(pageRequest, new PageHandler<ScheduleJob>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param scheduleJob 实体类
	 * @return 是否成功
	 * @since 2024-01-31
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody ScheduleJob scheduleJob) {

		return this.iScheduleJobService.save(scheduleJob);
	}

	/**
	 * 修改
	 *
	 * @param scheduleJob 实体类
	 * @return 是否成功
	 * @since 2024-01-31
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody ScheduleJob scheduleJob) {
		return this.iScheduleJobService.updateById(scheduleJob);

	}

	/**
	 * 根据id删除
	 *
	 * @param id id
	 * @return 是否成功
	 * @since 2024-01-31
	 */
	@DeleteMapping()
	@ApiOperation(value = "根据id删除")
	public Boolean remove(@RequestParam(value = "id", required = true) Long id) {
		return this.iScheduleJobService.removeById(id);
	}


	/**
	 * 立即执行
	 *
	 * @param id 任务id
	 * @return
	 * @since 2024/02/02
	 */
	@GetMapping("/run")
	@ApiOperation(value = "立即执行")
	public void run(Long id) {

		this.iScheduleJobService.run(id);
	}


	/**
	 * 暂停任务
	 *
	 * @param id 任务id
	 * @return
	 * @since 2024/02/02
	 */
	@ApiOperation(value = "暂停任务")
	@GetMapping("pause")
	public void pause(Long id) {

		this.iScheduleJobService.pause(id);
	}


	/**
	 * 恢复执行任务
	 *
	 * @param id 任务id
	 * @return
	 * @since 2024/02/02
	 */
	@ApiOperation(value = "恢复执行任务")
	@GetMapping("resume")
	public void resume(Long id) {

		this.iScheduleJobService.pause(id);
	}
}
