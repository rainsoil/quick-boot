package com.su60.quickboot.system.service.impl;

import com.su60.quickboot.system.entity.SysUserEntity;
import com.su60.quickboot.system.dos.SysUserDo;
import com.su60.quickboot.system.mapper.SysUserMapper;
import com.su60.quickboot.system.service.ISysUserService;
import cn.t200.quickboot.data.mybatisplus.BaseServiceImpl2;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2025/09/18
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends BaseServiceImpl2<SysUserMapper, SysUserEntity, SysUserDo> implements ISysUserService {

}

