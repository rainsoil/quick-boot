package io.github.rainsoil.fastboot.generator.controller;

import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.common.exception.WarningException;
import io.github.rainsoil.fastboot.core.vo.DictVo;
import io.github.rainsoil.fastboot.generator.entity.GDatasource;
import io.github.rainsoil.fastboot.generator.service.IGDatasourceService;
import io.github.rainsoil.fastboot.generator.utils.DbUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据源管理 前端控制器
 *
 * @author luyanan
 * @since 2024-01-16
 */
@Api(description = "数据源管理")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/generator/gDatasource")
public class GDatasourceController {

	private final IGDatasourceService iGDatasourceService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2024-01-16
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<GDatasource> page(@RequestBody PageRequest<GDatasource> pageRequest) {
		PageInfo<GDatasource> pageInfo = iGDatasourceService.page(pageRequest, new PageHandler<GDatasource>() {
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param gDatasource 实体类
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody GDatasource gDatasource) {

		return this.iGDatasourceService.save(gDatasource);
	}

	/**
	 * 修改
	 *
	 * @param gDatasource 实体类
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody GDatasource gDatasource) {
		return this.iGDatasourceService.updateById(gDatasource);

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
		return this.iGDatasourceService.removeById(id);
	}


	/**
	 * 连接测试
	 *
	 * @param id id
	 * @return
	 * @since 2024/01/16
	 */
	@ApiOperation(value = "连接测试")
	@GetMapping("connTest")
	public void connTest(Long id) {

		GDatasource datasource = this.iGDatasourceService.getById(id);
		try {
			DbUtils.getConnection(datasource.getDbType(), datasource.getConnUrl(), datasource.getUsername(), datasource.getPassword());
		} catch (Exception e) {
			throw new WarningException(20001);
		}
	}


	/**
	 * 数据源列表
	 *
	 * @return
	 * @since 2024/01/17
	 */
	@ApiOperation(value = "数据源列表")
	@GetMapping("datasourceList")
	public List<DictVo> datasourceList() {
		return this.iGDatasourceService.list().stream().map(a -> new DictVo()
				.setLabel(a.getConnName())
				.setValue(a.getId() + "")).collect(Collectors.toList());
	}


}
