package com.su60.quickboot.quartz.service;

import com.su60.quickboot.quartz.entity.SysJobEntity;
import com.su60.quickboot.quartz.dos.SysJobDo;
import com.su60.quickboot.data.mybatisplus.IBaseService2;

import java.util.List;

/**
 * <p>
 * 定时任务调度表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2024/11/07
 */

public interface ISysJobService extends IBaseService2<SysJobEntity, SysJobDo> {

	/**
	 * 查询所有的定时任务
	 *
	 * @return 任务集合
	 * @since 2024/11/12
	 */
	List<SysJobDo> listAll();


	/**
	 * 修改状态
	 *
	 * @param id     任务id
	 * @param status 状态
	 * @return 是否成功
	 * @since 2024/11/13
	 */
	Boolean changeStatus(Long id, String status);

	/**
	 * 立即执行
	 *
	 * @param id 任务id
	 * @return 是否成功
	 * @since 2024/11/13
	 */
	Boolean run(Long id);
}
