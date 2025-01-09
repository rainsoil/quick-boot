package com.su60.quickboot.system.service.impl;

import com.su60.quickboot.system.entity.SysLogininforEntity;
import com.su60.quickboot.system.dos.SysLogininforDo;
import com.su60.quickboot.system.mapper.SysLogininforMapper;
import com.su60.quickboot.system.service.ISysLogininforService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 系统访问记录 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/11/15
 */
@RequiredArgsConstructor
@Service
public class SysLogininforServiceImpl extends BaseServiceImpl2<SysLogininforMapper, SysLogininforEntity, SysLogininforDo> implements ISysLogininforService {

	@Async
	@Override
	public Boolean saveVo(SysLogininforDo sysLogininforDo) {
		return super.saveVo(sysLogininforDo);
	}
}

