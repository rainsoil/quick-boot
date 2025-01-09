package com.su60.quickboot.quartz.mapper;

import com.su60.quickboot.quartz.entity.SysJobLogEntity;
import com.su60.quickboot.data.mybatisplus.BaseBaseMapper;
import org.apache.ibatis.annotations.Mapper;
/**
 * <p>
 * 定时任务调度日志表 Mapper 接口
 * </p>
 *
 * @author luyanan
 * @since 2024/11/07
 */
@Mapper
public interface SysJobLogMapper extends BaseBaseMapper<SysJobLogEntity> {

}

