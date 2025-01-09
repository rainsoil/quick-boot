package com.su60.quickboot.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.common.core.PageInfo;
import com.su60.quickboot.data.mybatisplus.PageVoHandler;
import com.su60.quickboot.system.dos.SysOperLogDo;
import com.su60.quickboot.system.entity.SysOperLogEntity;
import com.su60.quickboot.system.service.ISysOperLogService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 操作日志记录
 *
 * @author luyanan
 * @since 2024/11/15
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysoperlog")

public class SysOperLogController {


	private final ISysOperLogService sysOperLogService;

	/**
	 * 分页查询
	 *
	 * @param sysOperLogDo 分页参数
	 * @return 分页结果
	 * @since 2024/11/15
	 */
	@PreAuthorize("hasAuthority('system:operlog:list')")
	@GetMapping("list")
	public PageInfo<SysOperLogDo> page(SysOperLogDo sysOperLogDo) {

		return sysOperLogService.page(sysOperLogDo, new PageVoHandler<SysOperLogEntity, SysOperLogDo>() {
			@Override
			public void queryWrapperHandler(SysOperLogDo vo, SysOperLogEntity sysOperLogEntity, LambdaQueryWrapper<SysOperLogEntity> queryWrapper) {


				queryWrapper.like(StrUtil.isNotBlank(vo.getMethod()), SysOperLogEntity::getMethod, vo.getMethod());
				sysOperLogEntity.setMethod(null);
				queryWrapper.like(StrUtil.isNotBlank(vo.getRequestMethod()), SysOperLogEntity::getRequestMethod, vo.getRequestMethod());
				sysOperLogEntity.setRequestMethod(null);
				queryWrapper.like(StrUtil.isNotBlank(vo.getOperName()), SysOperLogEntity::getOperName, vo.getOperName());
				sysOperLogEntity.setOperName(null);
				queryWrapper.like(StrUtil.isNotBlank(vo.getOperUrl()), SysOperLogEntity::getOperUrl, vo.getOperUrl());
				sysOperLogEntity.setOperUrl(null);

				// 主机地址
				queryWrapper.like(StrUtil.isNotBlank(vo.getOperIp()), SysOperLogEntity::getOperIp, vo.getOperIp());
				sysOperLogEntity.setOperIp(null);
				queryWrapper.orderByDesc(SysOperLogEntity::getOperTime);
				Integer status = vo.getStatus();
				if (null != status) {
					if (status == 0) {
						queryWrapper.eq(SysOperLogEntity::getStatus, 200);
					} else {
						queryWrapper.ne(SysOperLogEntity::getStatus, 200);
					}
				}
				sysOperLogEntity.setStatus(null);

				// 操作时间区间筛选
				List<String> operTimes =
						vo.getOperTimes();

				if (CollectionUtil.isNotEmpty(operTimes) && operTimes.size() == 2) {
					queryWrapper.between(SysOperLogEntity::getOperTime,
							DateUtil.beginOfDay(DateUtil.parse(operTimes.get(0), DatePattern.NORM_DATE_PATTERN)),
							DateUtil.endOfDay(DateUtil.parse(operTimes.get(1), DatePattern.NORM_DATE_PATTERN)));
				}
			}


		});

	}


	/**
	 * 根据id查询
	 *
	 * @param id id
	 * @return 操作日志记录
	 * @since 2024/11/15
	 */
	@PreAuthorize("hasAuthority('system:operlog:query')")
	@GetMapping("/{id}")
	public SysOperLogDo getById(@PathVariable("id") Long id) {
		return sysOperLogService.getVoById(id);
	}


}