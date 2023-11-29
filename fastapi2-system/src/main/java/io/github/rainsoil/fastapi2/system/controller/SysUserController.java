package io.github.rainsoil.fastapi2.system.controller;

import io.github.rainsoil.fastapi2.common.core.PageInfo;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastapi2.system.entity.SysUser;
import io.github.rainsoil.fastapi2.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 系统用户表 前端控制器
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Api(value = "系统用户表", description = "系统用户表")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysUser")
public class SysUserController {

	private final ISysUserService iSysUserService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2023-11-29
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<SysUser> page(@RequestBody PageRequest<SysUser> pageRequest) {
		PageInfo<SysUser> pageInfo = iSysUserService.page(pageRequest, new PageHandler<SysUser>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param sysUser 实体类
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody SysUser sysUser) {

		return this.iSysUserService.save(sysUser);
	}

	/**
	 * 修改
	 *
	 * @param sysUser 实体类
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody SysUser sysUser) {
		return this.iSysUserService.updateById(sysUser);

	}

	/**
	 * 根据id删除
	 *
	 * @param id id
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@DeleteMapping()
	@ApiOperation(value = "根据id删除")
	public Boolean remove(@RequestParam(value = "id", required = true) Long id) {
		return this.iSysUserService.removeById(id);
	}

}
