package com.su60.quickboot.system.controller;

import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.common.core.PageInfo;
import com.su60.quickboot.common.validation.AddGroup;
import com.su60.quickboot.common.validation.UpdateGroup;
import com.su60.quickboot.data.mybatisplus.PageVoHandler;
import com.su60.quickboot.system.dos.SysUserDo;
import com.su60.quickboot.system.entity.SysUserEntity;
import com.su60.quickboot.system.service.ISysRoleService;
import com.su60.quickboot.system.service.ISysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统用户
 *
 * @author luyanan
 * @since 2024/8/7
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("sys/user")
@RestController
public class SysUserController {


	private final ISysUserService sysUserService;


	private final ISysRoleService sysRoleService;

	/**
	 * 用户分页
	 *
	 * @param sysUserDo 系统用户
	 * @return 分页信息
	 * @since 2024/10/14
	 */
	@PreAuthorize("hasAuthority('system:user:list')")
	@GetMapping("page")
	public PageInfo<SysUserDo> page(SysUserDo sysUserDo) {

		return sysUserService.page(sysUserDo, new PageVoHandler<SysUserEntity, SysUserDo>() {
			@Override
			public void queryWrapperHandler(SysUserDo vo, SysUserEntity sysUserEntity, LambdaQueryWrapper<SysUserEntity> queryWrapper) {
				queryWrapper.orderByDesc(SysUserEntity::getCreateTime);
				//  用户名搜索

				queryWrapper.like(StrUtil.isNotBlank(vo.getUserName()), SysUserEntity::getUserName, vo.getUserName());
				sysUserEntity.setUserName(null);
				// 姓名搜索
				queryWrapper.like(StrUtil.isNotBlank(vo.getNickName()), SysUserEntity::getNickName, vo.getNickName());
				sysUserEntity.setNickName(null);


			}

			@Override
			public void recordsHandler(List<SysUserEntity> sysUserEntities, List<SysUserDo> sysUserDos) {
				Map<Long, List<Long>> userRoleIdsMap = new HashMap<>();
				Set<Long> allRoleIds = new HashSet<>();
				for (SysUserDo userDo : sysUserDos) {
					userDo.setPassword(null);
					// 查询关联的角色
					List<Long> roleIds = sysRoleService.listRoleIdUserId(userDo.getId());
					allRoleIds.addAll(roleIds);
					userRoleIdsMap.put(userDo.getId(), roleIds);
				}

				Map<Long, String> roleMap = sysRoleService.getVoByIds(allRoleIds).stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getRoleName()));
				for (SysUserDo userDo : sysUserDos) {
					List<Long> roleIds = userRoleIdsMap.get(userDo.getId());
					// 设置角色名称
					userDo.setRoleIds(roleIds);
					userDo.setRoleNames(roleIds.stream().map(roleMap::get).collect(Collectors.joining(",")));
				}

			}
		});

	}


	/**
	 * 用户信息保存
	 *
	 * @param sysUserDo 用户 信息
	 * @return 是否成功
	 * @since 2024/10/14
	 */
	@PreAuthorize("hasAuthority('system:user:add')")
	@PostMapping()
	public Boolean save(@RequestBody @Validated(AddGroup.class) SysUserDo sysUserDo) {

		return sysUserService.saveUser(sysUserDo);
	}

	/**
	 * 根据id修改
	 *
	 * @param sysUserDo 用户信息表
	 * @return 是否成功
	 * @since 2024/06/29
	 */
	@PreAuthorize("hasAuthority('system:user:edit')")
	@PutMapping
	public Boolean updateById(@RequestBody @Validated(UpdateGroup.class) SysUserDo sysUserDo) {
		return sysUserService.updateUser(sysUserDo);
	}


	/**
	 * 根据id查询
	 *
	 * @param id id
	 * @return 用户信息
	 * @since 2024/10/31
	 */
	@PreAuthorize("hasAuthority('system:user:query')")
	@GetMapping("/{id}")
	public SysUserDo getById(@PathVariable("id") Long id) {
		return sysUserService.getVoById(id);
	}


	/**
	 * 根据ids 删除
	 *
	 * @param ids 多个以英文逗号(,)分割
	 * @return 是否成功
	 * @since 2024/10/31
	 */
	@PreAuthorize("hasAuthority('system:user:remove')")
	@DeleteMapping()
	public Boolean deleteByIds(@RequestBody List<Long> ids) {
		return sysUserService.deleteByIds(ids);
	}
}
