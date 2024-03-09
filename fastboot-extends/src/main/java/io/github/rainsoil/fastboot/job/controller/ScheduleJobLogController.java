package io.github.rainsoil.fastboot.job.controller;

import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.job.entity.ScheduleJobLog;
import io.github.rainsoil.fastboot.job.service.IScheduleJobLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务日志表 前端控制器
 *
 * @author luyanan
 * @since 2024-01-31
 */
@Api(description = "定时任务日志表")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/job/scheduleJobLog")
public class ScheduleJobLogController {

	private final IScheduleJobLogService iScheduleJobLogService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2024-01-31
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<ScheduleJobLog> page(@RequestBody PageRequest<ScheduleJobLog> pageRequest) {
		PageInfo<ScheduleJobLog> pageInfo = iScheduleJobLogService.page(pageRequest, new PageHandler<ScheduleJobLog>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param scheduleJobLog 实体类
	 * @return 是否成功
	 * @since 2024-01-31
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody ScheduleJobLog scheduleJobLog) {

		return this.iScheduleJobLogService.save(scheduleJobLog);
	}

	/**
	 * 修改
	 *
	 * @param scheduleJobLog 实体类
	 * @return 是否成功
	 * @since 2024-01-31
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody ScheduleJobLog scheduleJobLog) {
		return this.iScheduleJobLogService.updateById(scheduleJobLog);

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
		return this.iScheduleJobLogService.removeById(id);
	}

}
