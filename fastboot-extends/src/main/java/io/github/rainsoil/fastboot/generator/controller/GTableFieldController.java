package io.github.rainsoil.fastboot.generator.controller;

import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.generator.entity.GTableField;
import io.github.rainsoil.fastboot.generator.service.IGTableFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 表字段实体类 前端控制器
 *
 * @author luyanan
 * @since 2024-01-16
 */
@Api(description = "表字段实体类")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/generator/gTableField")
public class GTableFieldController {

	private final IGTableFieldService iGTableFieldService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2024-01-16
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<GTableField> page(@RequestBody PageRequest<GTableField> pageRequest) {
		PageInfo<GTableField> pageInfo = iGTableFieldService.page(pageRequest, new PageHandler<GTableField>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param gTableField 实体类
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody GTableField gTableField) {

		return this.iGTableFieldService.save(gTableField);
	}

	/**
	 * 修改
	 *
	 * @param gTableField 实体类
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody GTableField gTableField) {
		return this.iGTableFieldService.updateById(gTableField);

	}

	/**
	 * 根据id删除
	 *
	 * @param id id
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@DeleteMapping()
	@ApiOperation(value = "根据id删除")
	public Boolean remove(@RequestParam(value = "id", required = true) Long id) {
		return this.iGTableFieldService.removeById(id);
	}

}
