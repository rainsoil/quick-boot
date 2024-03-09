package io.github.rainsoil.fastboot.generator.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseBaseMapper;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastboot.common.exception.WarningException;
import io.github.rainsoil.fastboot.core.BaseEntity;
import io.github.rainsoil.fastboot.core.enums.CommonEnums;
import io.github.rainsoil.fastboot.core.vo.DictVo;
import io.github.rainsoil.fastboot.generator.config.CodeGeneratorProperties;
import io.github.rainsoil.fastboot.generator.entity.GDatasource;
import io.github.rainsoil.fastboot.generator.entity.GTableField;
import io.github.rainsoil.fastboot.generator.entity.GTableInfo;
import io.github.rainsoil.fastboot.generator.mapper.GTableInfoMapper;
import io.github.rainsoil.fastboot.generator.service.IGDatasourceService;
import io.github.rainsoil.fastboot.generator.service.IGTableFieldService;
import io.github.rainsoil.fastboot.generator.service.IGTableInfoService;
import io.github.rainsoil.fastboot.generator.utils.DbUtils;
import io.github.rainsoil.fastboot.generator.vo.LoadTableVo;
import io.github.rainsoil.fastboot.generator.vo.PreviewVo;
import io.github.rainsoil.fastboot.system.entity.SysMenu;
import io.github.rainsoil.fastboot.system.enums.SysMenuEnums;
import io.github.rainsoil.fastboot.system.service.ISysMenuService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 表信息 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
@RequiredArgsConstructor
@Service
public class GTableInfoServiceImpl extends BaseServiceImpl<GTableInfoMapper, GTableInfo> implements IGTableInfoService {

	private final IGDatasourceService igDatasourceService;

	private final CodeGeneratorProperties codeGeneratorProperties;

	private final IGTableFieldService tableFieldService;
	private final ISysMenuService sysMenuService;

	@Override
	public List<DictVo> listTableByDatabaseId(Long datasourceId) {
		GDatasource datasource = igDatasourceService.getById(datasourceId);
		if (null == datasource) {
			throw new WarningException(20002);
		}
		try {
			List<TableInfo> table = DbUtils.getTable(datasource.getConnUrl(), datasource.getUsername(), datasource.getPassword());
			return table.stream().map(a -> new DictVo()
					.setValue(a.getName())
					.setLabel(a.getComment())).collect(Collectors.toList());
		} catch (Exception e) {
			return new ArrayList<>();
		}

	}

	@Override
	public void loadTable(LoadTableVo loadTableVo) {
		GDatasource datasource = igDatasourceService.getById(loadTableVo.getDatasourceId());
		if (null == datasource) {
			throw new WarningException(20002);
		}

		long count = this.count(new LambdaQueryWrapper<GTableInfo>().eq(GTableInfo::getTableName, loadTableVo.getTableName())
				.eq(GTableInfo::getDatasourceId, loadTableVo.getDatasourceId()));
		if (count > 0) {
			throw new WarningException(20003);
		}
		try {
			TableInfo tableInfo = DbUtils.getTable(datasource.getConnUrl(), datasource.getUsername(), datasource.getPassword())
					.stream().filter(a -> a.getName().equals(loadTableVo.getTableName())).findFirst().get();

			GTableInfo gTableInfo = new GTableInfo()
					.setTableComment(tableInfo.getComment())
					.setTableName(tableInfo.getName())
					.setAuth(codeGeneratorProperties.getAuth())
					.setDatasourceId(loadTableVo.getDatasourceId())
					.setEntityName(tableInfo.getEntityName())
					.setPackageName(codeGeneratorProperties.getParentModulePackage());
			this.save(gTableInfo);

			List<GTableField> tableFields = tableInfo.getFields().stream().map(a -> new GTableField()
							.setTableId(gTableInfo.getId())
							.setTableName(tableInfo.getName())
							.setColumnName(a.getColumnName())
							.setColumnComment(a.getComment())
							.setColumnType(a.getMetaInfo().getJdbcType().name())
							.setAttrName(a.getPropertyName())
							.setAttrType(a.getPropertyType())
							.setIsRequired(a.getMetaInfo().isNullable() ? "1" : "0")
							.setIsPk(a.isKeyFlag() ? "0" : "1"))
					.collect(Collectors.toList());
			tableFieldService.saveBatch(tableFields);

		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	@Override
	public GTableInfo info(Long id) {
		GTableInfo tableInfo = this.getById(id);
		List<GTableField> list = tableFieldService.list(new LambdaQueryWrapper<GTableField>()
				.eq(GTableField::getTableId, id));

		// 设置一系列默认值
		String ignoreColumns = codeGeneratorProperties.getIgnoreColumns();

		List<String> ignoreColumnList = Arrays.stream(ignoreColumns.split(",")).collect(Collectors.toList());

		list = list.stream().map(a -> {

			String attrName = a.getAttrName();

			if (ignoreColumns.contains(attrName)) {
				a.setHide(CommonEnums.FLAG_NO.getValue());
				a.setDisplay(CommonEnums.FLAG_NO.getValue());
			} else {
				a.setHide(CommonEnums.FLAG_YES.getValue());
				a.setDisplay(CommonEnums.FLAG_YES.getValue());
			}
			// 这里在设置一波默认值
			a.setIsSearch(CommonEnums.FLAG_NO.getValue());
			a.setAddDisplay(CommonEnums.FLAG_YES.getValue());
			a.setEditDisplay(CommonEnums.FLAG_YES.getValue());
			a.setPageType("input");
			return a;
		}).collect(Collectors.toList());
		tableInfo.setTableFields(list);
		tableInfo.setAddBtn(CommonEnums.FLAG_YES.getValue());
		tableInfo.setEditBtn(CommonEnums.FLAG_YES.getValue());
		tableInfo.setDelBtn(CommonEnums.FLAG_YES.getValue());
		tableInfo.setVerifyPermissions(CommonEnums.FLAG_YES.getValue());

		return tableInfo;
	}

	@Override
	public void updateTableInfo(GTableInfo gTableInfo) {
		this.updateById(gTableInfo);
		List<GTableField> tableFields = gTableInfo.getTableFields();
		for (GTableField tableField : tableFields) {
			tableField.setTableId(tableField.getTableId());
		}
		tableFieldService.saveOrUpdateBatch(tableFields);
	}

	@Override
	public void generator(Long id, HttpServletResponse response) {

		GTableInfo info = info(id);
		String filePath = generator(info);
		// 对文件进行压缩
		try {

			System.out.println(filePath);
			ZipUtil.zip(filePath, filePath + ".zip", false);
			ServletUtil.write(response, new File(filePath + ".zip"));
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename='" + info.getTableName() + ".zip'");
			FileUtil.del(filePath);
			FileUtil.del(filePath + ".zip");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 代码生成
	 *
	 * @param info 表信息
	 * @return
	 * @since 2024/02/03
	 */
	private String generator(GTableInfo info) {
		String filePath = System.getProperty("user.dir") + "/" + IdUtil.fastSimpleUUID();
		List<String> collect = Arrays.stream(codeGeneratorProperties.getIgnoreColumns().split(",")).collect(Collectors.toList());

		info.setTableFields(info.getTableFields().stream().filter(a -> !collect.contains(a.getAttrName())).collect(Collectors.toList()));
		// 再查询数据源
		GDatasource datasource = igDatasourceService.getById(info.getDatasourceId());

		FastAutoGenerator.create(datasource.getConnUrl(),
						datasource.getUsername(), datasource.getPassword())

				.strategyConfig(builder -> {
					builder.entityBuilder()
							.superClass(BaseEntity.class)
							.addIgnoreColumns("create_time", "update_time", "create_by", "update_by", "del_flag")
//                            .disableSerialVersionUID()
							.enableChainModel()
							.enableLombok()
							.enableTableFieldAnnotation()
							.enableTableFieldAnnotation()
//                            .enableRemoveIsPrefix()
							.enableTableFieldAnnotation()
							.versionColumnName(codeGeneratorProperties.getVersionColumnName())
							.versionPropertyName(codeGeneratorProperties.getVersionColumnName())
							.logicDeleteColumnName(codeGeneratorProperties.getLogicDeleteColumnName())

							.enableFileOverride()
							.columnNaming(NamingStrategy.underline_to_camel)
							.addTableFills(new Column("create_user", FieldFill.INSERT))
							.addTableFills(new Column("update_user", FieldFill.INSERT_UPDATE))
							.addTableFills(new Column("create_time", FieldFill.INSERT))
							.addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
							.idType(IdType.ASSIGN_ID)
							.controllerBuilder().enableRestStyle()
							.mapperBuilder().superClass(BaseBaseMapper.class).enableMapperAnnotation().mapperAnnotation(Mapper.class)
							.serviceBuilder().superServiceClass(IBaseService.class)
							.serviceBuilder().superServiceImplClass(BaseServiceImpl.class)

							.build();
				}).globalConfig(builder -> {
					builder.author(info.getAuth()) // 设置作者
//                            .enableSpringdoc()
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件
							.disableOpenDir()

							.outputDir(filePath); // 指定输出目录
				})

				.packageConfig(builder -> {
					builder.parent(info.getPackageName()) // 设置父包名
//							.moduleName("system") // 设置父包模块名
							.moduleName(info.getModuleName()) // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.xml, filePath)); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {


//					builder.likeTable(new LikeTable("sys_combo_quan"))
					builder.addInclude(info.getTableName()); // 设置需要生成的表名
//							.addTablePrefix("syssss_"); // 设置过滤表前缀
				})
				.templateConfig(builder -> {
							builder
//									.disable(TemplateType.ENTITY)
									.entity("/templates/entity.java")
									.service("/templates/service.java")
									.serviceImpl("/templates/serviceImpl.java")
									.mapper("/templates/mapper.java")
//                            .mapperXml("/templates/mapper.xml")
//									.controller("/templates/controller.java")
									// 生成vue文件
									.xml("/templates/avue.vue")
									.controller("/templates/controller2.java")
									.build();
						}

				)
				.injectionConfig(builder -> {
					HashMap<String, Object> map = new HashMap<>();
					map.put("tableInfo", info);
					builder.customMap(map)
							.customFile(b -> {
								b.filePath(filePath + "/avue/" + info.getModuleName() + "/" + StrUtil.toCamelCase(info.getTableName()) + "/").fileName("index.vue").enableFileOverride()
										.templatePath("/templates/avue.vue.ftl").build();
							});
				})
				.templateEngine(new FreemarkerTemplateEngine())

				.execute();
		return filePath;
	}

	@Override
	public List<PreviewVo> preview(Long id) {
		GTableInfo info = info(id);

		String filePath = generator(info);
		List<File> files = FileUtil.loopFiles(filePath);
		List<PreviewVo> collect = files.stream().map(a -> new PreviewVo()
						.setLabel(a.getName())
						.setName(a.getName())
						.setCode(FileUtil.readString(a, Charset.forName("utf-8"))))
				.collect(Collectors.toList());

		// 删除文件
		FileUtil.del(filePath);
		return collect;
	}

	@Override
	public void createMenu(Long id, SysMenu sysMenu) {
		GTableInfo info = super.getById(id);
		String component = "views/" + info.getModuleName() + "/" + info.getEntityName() + "/" + info.getEntityName() + "index.vue";
		sysMenu.setComponent(component);
		sysMenu.setType(SysMenuEnums.MENU.getValue());
		sysMenu.setKeepAlive("0");
		sysMenu.setBlank("1");

		sysMenuService.save(sysMenu);
		String verifyPermissions = info.getVerifyPermissions();
		if (verifyPermissions.equals(CommonEnums.FLAG_YES.getValue())) {
			// 增加按钮
			String perfix = info.getModuleName() + ":" + info.getEntityName() + ":";

			String[] pers = {"add:新增按钮", "edit:编辑按钮", "del:删除", "view:查看"};
			List<SysMenu> sysMenus = new ArrayList<>();
			for (String per : pers) {
				String[] split = per.split(":");
				SysMenu button = new SysMenu();
				button.setParentId(sysMenu.getId());
				button.setType(SysMenuEnums.MENU_TYPE_BUTTON.getValue());
				button.setName(split[1]);
				button.setPermission(perfix + split[0]);

				sysMenus.add(button);
			}
			sysMenuService.saveBatch(sysMenus);

		}

	}

	@Override
	public boolean removeById(Serializable id) {
		// 删除字段配置
		tableFieldService.remove(new LambdaQueryWrapper<GTableField>().eq(GTableField::getTableId, id));
		return super.removeById(id);
	}
}
