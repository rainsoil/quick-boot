package io.github.rainsoil.fastboot.system.controller;

import cn.hutool.core.lang.tree.Tree;
import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.core.user.LoginUserUtils;
import io.github.rainsoil.fastboot.system.entity.SysMenu;
import io.github.rainsoil.fastboot.system.enums.SysMenuEnums;
import io.github.rainsoil.fastboot.system.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 系统菜单表 前端控制器
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Api(value = "系统菜单表", description = "系统菜单表")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysMenu")
public class SysMenuController {

	private final ISysMenuService iSysMenuService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2023-11-29
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<SysMenu> page(@RequestBody PageRequest<SysMenu> pageRequest) {
		PageInfo<SysMenu> pageInfo = iSysMenuService.page(pageRequest, new PageHandler<SysMenu>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param sysMenu 实体类
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody SysMenu sysMenu) {

		return this.iSysMenuService.save(sysMenu);
	}

	/**
	 * 修改
	 *
	 * @param sysMenu 实体类
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody SysMenu sysMenu) {
		return this.iSysMenuService.updateById(sysMenu);

	}

	/**
	 * 根据id删除
	 *
	 * @param id id
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@DeleteMapping("/{id}")
	@ApiOperation(value = "根据id删除")
	public Boolean remove(@PathVariable(value = "id") Long id) {
		return this.iSysMenuService.removeById(id);
	}


	/**
	 * 获取当前用户菜单
	 *
	 * @param type     菜单类型
	 * @param parentId 父id
	 * @return
	 * @since 2023/11/30
	 */
	@ApiOperation(value = "获取当前用户菜单")
	@GetMapping("getMenu")
	public List<Tree<Long>> getMenu(String type, @RequestParam(value = "parentId", required = false) Long parentId) {
		return this.iSysMenuService.getMenu(type, LoginUserUtils.getUser().getUserId(), parentId);
	}

	/**
	 * 获取顶部菜单
	 *
	 * @param parentId 父id
	 * @return
	 * @since 2023/11/30
	 */
	@ApiOperation(value = "获取顶部菜单")
	@GetMapping("getTopMenu")
	public List<Tree<Long>> getTopMenu(@RequestParam(value = "parentId", required = false) Long parentId) {
		return this.iSysMenuService.getMenu(SysMenuEnums.MENU_TYPE_TOP_MENU.getValue(), LoginUserUtils.getUser().getUserId(), parentId);
	}


	/**
	 * 树形菜单
	 *
	 * @param lazy     是否 懒加载
	 * @param parentId 父id
	 * @return
	 * @since 2023/12/10
	 */
	@ApiOperation(value = "树形菜单")
	@PostMapping("tree")
	public List<Tree<Long>> tree(boolean lazy, Long parentId) {
		return iSysMenuService.treeMenu(lazy, parentId);
	}


	/**
	 * 所有的菜单
	 *
	 * @return
	 * @since 2024/01/16
	 */
	@ApiOperation(value = "所有的菜单")
	@GetMapping("allTree")
	public List<Tree<Long>> allTree() {
		List<Tree<Long>> trees = iSysMenuService.treeMenu(false, null);
		Tree<Long> root = new Tree<>();
		root.setChildren(trees);
		root.setName("根菜单");
		root.setId(-1L);
		return Arrays.asList(root);
	}
}
