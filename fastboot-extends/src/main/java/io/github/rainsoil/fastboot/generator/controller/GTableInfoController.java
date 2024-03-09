package io.github.rainsoil.fastboot.generator.controller;

import io.github.rainsoil.fastboot.common.core.PageInfo;
import io.github.rainsoil.fastboot.common.core.PageRequest;
import io.github.rainsoil.fastboot.common.data.mybatis.PageHandler;
import io.github.rainsoil.fastboot.common.validation.AddGroup;
import io.github.rainsoil.fastboot.common.validation.ValidatorGroup1;
import io.github.rainsoil.fastboot.core.vo.DictVo;
import io.github.rainsoil.fastboot.generator.entity.GTableInfo;
import io.github.rainsoil.fastboot.generator.service.IGDatasourceService;
import io.github.rainsoil.fastboot.generator.service.IGTableInfoService;
import io.github.rainsoil.fastboot.generator.vo.LoadTableVo;
import io.github.rainsoil.fastboot.generator.vo.PreviewVo;
import io.github.rainsoil.fastboot.system.entity.SysMenu;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 表信息 前端控制器
 *
 * @author luyanan
 * @since 2024-01-16
 */
@Api(description = "表信息")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/generator/gTableInfo")
public class GTableInfoController {

	private final IGTableInfoService iGTableInfoService;

	private final IGDatasourceService igDatasourceService;

	/**
	 * 分页
	 *
	 * @param pageRequest 分页参数
	 * @return 分页返回
	 * @since 2024-01-16
	 */
	@ApiOperation(value = "分页")
	@PostMapping("page")
	public PageInfo<GTableInfo> page(@RequestBody PageRequest<GTableInfo> pageRequest) {
		PageInfo<GTableInfo> pageInfo = iGTableInfoService.page(pageRequest, new PageHandler<GTableInfo>() {


			@Override
			public void recordsHandler(List<GTableInfo> records) {
				List<Long> datasourceIds = records.stream().map(a -> a.getDatasourceId()).distinct().collect(Collectors.toList());
				Map<Long, String> map = igDatasourceService.listByIds(datasourceIds).stream().collect(Collectors.toMap(k -> k.getId(), v -> v.getConnName()));
				for (GTableInfo record : records) {
					record.setDatasourceName(map.getOrDefault(record.getDatasourceId(), "未知"));
				}

			}
		});
		return pageInfo;
	}

	/**
	 * 保存
	 *
	 * @param gTableInfo 实体类
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@PostMapping("")
	@ApiOperation(value = "保存")
	public Boolean save(@RequestBody GTableInfo gTableInfo) {

		return this.iGTableInfoService.save(gTableInfo);
	}

	/**
	 * 修改
	 *
	 * @param gTableInfo 实体类
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@ApiOperation(value = "修改")
	@PutMapping("")
	public Boolean update(@RequestBody GTableInfo gTableInfo) {
		return this.iGTableInfoService.updateById(gTableInfo);

	}

	/**
	 * 根据id删除
	 *
	 * @param id id
	 * @return 是否成功
	 * @since 2024-01-16
	 */
	@DeleteMapping("{id}")
	@ApiOperation(value = "根据id删除")
	public Boolean remove(@PathVariable(value = "id", required = true) Long id) {
		return this.iGTableInfoService.removeById(id);
	}


	/**
	 * 根据数据源id 查询表
	 *
	 * @param datasourceId 数据源id
	 * @return
	 * @since 2024/01/21
	 */
	@GetMapping("listTableByDatabaseId")
	public List<DictVo> listTableByDatabaseId(@RequestParam(value = "datasourceId", required = true) Long datasourceId) {

		List<DictVo> tables = iGTableInfoService.listTableByDatabaseId(datasourceId);
		return tables;
	}


	/**
	 * 加载表
	 *
	 * @param loadTableVo vo类
	 * @return
	 * @since 2024/01/21
	 */
	@ApiOperation(value = "加载表")
	@PostMapping("loadTable")
	public void loadTable(@RequestBody @Validated(AddGroup.class) LoadTableVo loadTableVo) {

		iGTableInfoService.loadTable(loadTableVo);
	}


	/**
	 * 表信息
	 *
	 * @param id 表id
	 * @return
	 * @since 2024/01/21
	 */
	@GetMapping("{id}")
	@ApiOperation(value = "表信息")
	public GTableInfo info(@PathVariable("id") Long id) {

		return iGTableInfoService.info(id);
	}

	/**
	 * 修改表的信息
	 *
	 * @param gTableInfo 表信息
	 * @return
	 * @since 2024/01/27
	 */
	@ApiOperation(value = "修改表的信息")
	@PostMapping("updateTableInfo")
	public void updateTableInfo(@RequestBody @Validated(ValidatorGroup1.class) GTableInfo gTableInfo) {
		this.iGTableInfoService.updateTableInfo(gTableInfo);
	}


	/**
	 * 代码预览
	 *
	 * @param id 表信息id
	 * @return
	 * @since 2024/01/28
	 */
	@ApiOperation(value = "preview")
	@GetMapping("preview")
	public List<PreviewVo> preview(Long id) {

		List<PreviewVo> previewVos = iGTableInfoService.preview(id);
		return previewVos;
	}

	/**
	 * 生成菜单
	 *
	 * @param id      表信息
	 * @param sysMenu 菜单信息
	 * @return
	 * @since 2024/01/29
	 */
	@ApiOperation(value = "生成菜单")
	@PostMapping("createMenu/{id}")
	public void createMenu(@PathVariable("id") Long id, @RequestBody SysMenu sysMenu) {

		iGTableInfoService.createMenu(id, sysMenu);

	}

}
