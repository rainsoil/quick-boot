package com.su60.quickboot.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.common.bean.BeanConvertUtils;
import com.su60.quickboot.common.exception.WarningException;
import com.su60.quickboot.security.password.PasswordEncoder;
import com.su60.quickboot.system.entity.SysUserEntity;
import com.su60.quickboot.system.dos.SysUserDo;
import com.su60.quickboot.system.entity.SysUserRoleEntity;
import com.su60.quickboot.system.mapper.SysUserMapper;
import com.su60.quickboot.system.service.ISysRoleService;
import com.su60.quickboot.system.service.ISysUserRoleService;
import com.su60.quickboot.system.service.ISysUserService;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/08/07
 */
@RequiredArgsConstructor
@Service
public class SysUserServiceImpl extends BaseServiceImpl2<SysUserMapper, SysUserEntity, SysUserDo> implements ISysUserService {

	@Autowired
	@Lazy
	private ISysRoleService sysRoleService;

	@Autowired
	private ISysUserRoleService sysUserRoleService;
	
	private final PasswordEncoder passwordEncoder;

	@Override
	public SysUserDo findByUserName(String username) {
		if (StrUtil.isBlank(username)) {
			return null;
		}
		SysUserEntity sysUserEntity = super.getOne(new LambdaQueryWrapper<SysUserEntity>()
				.eq(SysUserEntity::getUserName, username));
		return BeanConvertUtils.convertTo(sysUserEntity, SysUserDo.class);
	}

	@Override
	public Boolean saveUser(SysUserDo sysUserDo) {
		// 检验userName 唯一
		if (this.count(new LambdaQueryWrapper<SysUserEntity>()
				.eq(SysUserEntity::getUserName, sysUserDo.getUserName())) > 0) {
			throw new WarningException(100000, sysUserDo.getUserName());
		}
		// 密码加密
		sysUserDo.setPassword(passwordEncoder.encode(sysUserDo.getPassword()));
		Boolean b = super.saveVo(sysUserDo);
		// 保存用户和角色关联关系
		List<Long> roleIds =
				sysUserDo.getRoleIds();
		if (CollectionUtil.isNotEmpty(roleIds)) {
			sysRoleService.saveUserRoles(sysUserDo.getId(), sysUserDo.getRoleIds());
		}
		return b;
	}

	@Override
	public Boolean updateUser(SysUserDo sysUserDo) {
		// 检验userName 唯一
		if (this.count(new LambdaQueryWrapper<SysUserEntity>()
				.eq(SysUserEntity::getUserName, sysUserDo.getUserName())
				.ne(SysUserEntity::getId, sysUserDo.getId())) > 0) {
			throw new WarningException(100000, sysUserDo.getUserName());
		}
		// 密码加密
		if (StrUtil.isNotBlank(sysUserDo.getPassword())) {
			sysUserDo.setPassword(passwordEncoder.encode(sysUserDo.getPassword()));
		}
		Boolean b = this.updateVoById(sysUserDo);
		// 保存用户和角色关联关系
		List<Long> roleIds =
				sysUserDo.getRoleIds();
		if (CollectionUtil.isNotEmpty(roleIds)) {
			sysRoleService.saveUserRoles(sysUserDo.getId(), sysUserDo.getRoleIds());
		}
		return b;
	}

	@Override
	public Boolean deleteByIds(Collection<? extends Serializable> ids) {
		// 删除用户与角色的关联关系
		sysUserRoleService.deleteByUserIds(ids);
		return super.deleteByIds(ids);
	}

	@Override
	public SysUserDo getVoById(Serializable id) {
		SysUserDo aDo = super.getVoById(id);
		aDo.setPassword(null);
		// 查询关联关系
		List<SysUserRoleEntity> sysUserRoleEntities = sysUserRoleService.listByUserId(id);
		aDo.setRoleIds(sysUserRoleEntities.stream().map(SysUserRoleEntity::getRoleId).collect(Collectors.toList()));
		return aDo;
	}
}

