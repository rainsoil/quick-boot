package io.github.rainsoil.fastapi2.system.controller;

import cn.hutool.core.lang.Opt;
import io.github.rainsoil.fastapi2.common.core.PageInfo;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastapi2.common.validation.ValidatorGroup1;
import io.github.rainsoil.fastapi2.common.validation.ValidatorGroup2;
import io.github.rainsoil.fastapi2.core.user.LoginUser;
import io.github.rainsoil.fastapi2.core.user.LoginUserUtils;
import io.github.rainsoil.fastapi2.system.entity.SysUser;
import io.github.rainsoil.fastapi2.system.service.ISysUserService;
import io.github.rainsoil.fastapi2.system.vo.ChangePasswordVo;
import io.github.rainsoil.fastapi2.system.vo.UserInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
	 * 获取登录用户信息
	 *
	 * @return
	 * @since 2023/11/30
	 */
	@GetMapping("getUserInfo")
	public UserInfoVo getUserInfo() {
		LoginUser user = LoginUserUtils.getUser();

		SysUser sysUser = this.iSysUserService.getById(user.getUserId());

		return new UserInfoVo()
				.setUserInfo(new SysUser()
						.setId(sysUser.getId())
						.setUsername(sysUser.getUsername())
						.setPhone(sysUser.getPhone())

						.setName(sysUser.getName()))
				.setPermission(Opt.ofEmptyAble(user.getAuths()).orElse(new ArrayList<>()))
						.
				setRoles(Opt.ofEmptyAble(user.getRoleCodes()).map(a -> a.stream().collect(Collectors.joining(","))).orElse(null));
	}

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

	/**
	 * 修改个人信息
	 *
	 * @param sysUser 用户信息
	 * @return
	 * @since 2024/01/11
	 */
	@ApiOperation(value = "修改个人信息")
	@PostMapping("updateUserInfo")
	public void updateUserInfo(@RequestBody SysUser sysUser) {

		sysUser.setPassword(null);
		LoginUser user = LoginUserUtils.getUser();
		sysUser.setId(user.getUserId());
		iSysUserService.updateUserInfo(sysUser);
	}

	/**
	 * 修改用户密码
	 *
	 * @param changePasswordVo
	 * @return
	 * @since 2024/01/11
	 */
	@PostMapping("changePassword")
	@ApiOperation(value = "修改用户密码")
	public void changePassword(@RequestBody @Validated(ValidatorGroup1.class) ChangePasswordVo changePasswordVo) {
		iSysUserService.changePassword(changePasswordVo);
	}


	/**
	 * 修改当前用户密码
	 *
	 * @param changePasswordVo
	 * @return
	 * @since 2024/01/11
	 */
	@ApiOperation(value = "修改当前用户密码")
	@PostMapping("updatePassword")
	public void updatePassword(@RequestBody @Validated(ValidatorGroup2.class) ChangePasswordVo changePasswordVo) {
		changePasswordVo.setUserId(LoginUserUtils.getUser().getUserId());
		iSysUserService.changePassword(changePasswordVo);
	}

}
