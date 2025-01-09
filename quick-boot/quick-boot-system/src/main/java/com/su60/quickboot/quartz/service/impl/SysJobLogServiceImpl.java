package com.su60.quickboot.quartz.service.impl;

import com.su60.quickboot.quartz.entity.SysJobLogEntity;
import com.su60.quickboot.quartz.dos.SysJobLogDo;
import com.su60.quickboot.quartz.mapper.SysJobLogMapper;
import com.su60.quickboot.quartz.service.ISysJobLogService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 定时任务调度日志表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/11/07
 */
@RequiredArgsConstructor
@Service
public class SysJobLogServiceImpl extends BaseServiceImpl2<SysJobLogMapper, SysJobLogEntity, SysJobLogDo> implements ISysJobLogService {

	@Override
	public void addJobLog(SysJobLogDo sysJobLog) {
		this.saveVo(sysJobLog);
	}

	@Override
	public Boolean clean(Long jobId) {
		return super.remove(new LambdaQueryWrapper<SysJobLogEntity>()
				.eq(null != jobId, SysJobLogEntity::getJobId, jobId));
	}
}

