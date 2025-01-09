package com.su60.quickboot.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.su60.quickboot.common.core.PageInfo;
import com.su60.quickboot.data.mybatisplus.PageVoHandler;
import com.su60.quickboot.system.dos.SysLogininforDo;
import com.su60.quickboot.system.entity.SysLogininforEntity;
import com.su60.quickboot.system.service.ISysLogininforService;
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
 * 系统访问记录
 *
 * @author luyanan
 * @since 2024/11/15
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/syslogininfor")

public class SysLogininforController {


	private final ISysLogininforService sysLogininforService;

	/**
	 * 分页查询
	 *
	 * @param sysLogininforDo 分页参数
	 * @return 分页结果
	 * @since 2024/11/15
	 */
	@PreAuthorize("hasAuthority('system:logininfor:list')")
	@GetMapping("list")
	public PageInfo<SysLogininforDo> page(SysLogininforDo sysLogininforDo) {

		return sysLogininforService.page(sysLogininforDo, new PageVoHandler<SysLogininforEntity, SysLogininforDo>() {
			@Override
			public void queryWrapperHandler(SysLogininforDo vo, SysLogininforEntity sysLogininforEntity, LambdaQueryWrapper<SysLogininforEntity> queryWrapper) {


				queryWrapper.like(StrUtil.isNotBlank(vo.getUserName()), SysLogininforEntity::getUserName, vo.getUserName());
				sysLogininforEntity.setUserName(null);
				queryWrapper.like(StrUtil.isNotBlank(vo.getIpaddr()), SysLogininforEntity::getIpaddr, vo.getIpaddr());
				sysLogininforEntity.setIpaddr(null);

				// 登录时间筛选
				List<String> loginTimes = vo.getLoginTimes();
				if (CollectionUtil.isNotEmpty(loginTimes) && loginTimes.size() == 2) {
					queryWrapper.between(SysLogininforEntity::getLoginTime,
							DateUtil.beginOfDay(DateUtil.parse(loginTimes.get(0), DatePattern.NORM_DATE_PATTERN)),
							DateUtil.endOfDay(DateUtil.parse(loginTimes.get(1), DatePattern.NORM_DATE_PATTERN)));
				}
			}


		});

	}


	/**
	 * 根据id查询
	 *
	 * @param id id
	 * @return 系统访问记录
	 * @since 2024/11/15
	 */
	@PreAuthorize("hasAuthority('system:logininfor:query')")
	@GetMapping("/{id}")
	public SysLogininforDo getById(@PathVariable("id") Long id) {
		return sysLogininforService.getVoById(id);
	}


}