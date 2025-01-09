package com.su60.quickboot.system.service.impl;

import com.su60.quickboot.system.entity.SysOperLogEntity;
import com.su60.quickboot.system.dos.SysOperLogDo;
import com.su60.quickboot.system.mapper.SysOperLogMapper;
import com.su60.quickboot.system.service.ISysOperLogService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/11/15
 */
@RequiredArgsConstructor
@Service
public class SysOperLogServiceImpl extends BaseServiceImpl2<SysOperLogMapper, SysOperLogEntity, SysOperLogDo> implements ISysOperLogService {

	@Async
	@Override
	public Boolean saveVo(SysOperLogDo sysOperLogDo) {
		return super.saveVo(sysOperLogDo);
	}
}

