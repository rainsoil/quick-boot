package io.github.rainsoil.fastapi2.system.controller;

import io.github.rainsoil.fastapi2.common.core.PageInfo;
import io.github.rainsoil.fastapi2.common.core.PageRequest;
import io.github.rainsoil.fastapi2.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastapi2.system.entity.SysDict;
import io.github.rainsoil.fastapi2.system.entity.SysDictItem;
import io.github.rainsoil.fastapi2.system.service.ISysDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典表 前端控制器
 *
 * @author luyanan
 * @since 2023-11-29
 */
@Api(value = "字典表", description = "字典表")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/sysDict")
public class SysDictController {

	private final ISysDictService iSysDictService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2023-11-29
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<SysDict> page(@RequestBody PageRequest<SysDict> pageRequest) {
		PageInfo<SysDict> pageInfo = iSysDictService.page(pageRequest, new PageHandler<SysDict>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param sysDict 实体类
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody SysDict sysDict) {

		return this.iSysDictService.save(sysDict);
	}

	/**
	 * 修改
	 *
	 * @param sysDict 实体类
	 * @return 是否成功
	 * @since 2023-11-29
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody SysDict sysDict) {
		return this.iSysDictService.updateById(sysDict);

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
		return this.iSysDictService.removeById(id);
	}


	/**
	 * 根据字典类型查询
	 *
	 * @param type  字典类型
	 * @param style 是否携带样式
	 * @return
	 * @since 2024/01/04
	 */
	@GetMapping("dict/{type}")
	@ApiOperation(value = "根据字典类型查询")
	public List<SysDictItem> getByType(@PathVariable("type") String type, @RequestParam(value = "style", required = false, defaultValue = "false") Boolean style) {

		return iSysDictService.getByType(type, style);

	}
}
