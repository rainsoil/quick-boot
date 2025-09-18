package com.su60.quickboot.system.service;

import com.su60.quickboot.system.entity.SysOperLogEntity;
import com.su60.quickboot.system.dos.SysOperLogDo;
import com.su60.quickboot.data.mybatisplus.IBaseService2;

/**
 * <p>
 * 操作日志记录 服务类
 * </p>
 *
 * @author luyanan
 * @since 2024/11/15
 */

public interface ISysOperLogService extends IBaseService2<SysOperLogEntity, SysOperLogDo> {
	/**
	 * 保存日志
	 * @since 2025/9/17 
	 * @param sysOperLogDo 日志
	 * @return
	 */
	void saveLog(SysOperLogDo sysOperLogDo);
}
