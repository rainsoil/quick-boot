package com.su60.quickboot.generator.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.su60.quickboot.common.core.PageInfo;
import com.su60.quickboot.common.core.PageRequest;
import com.su60.quickboot.common.exception.WarningException;
import com.su60.quickboot.data.generator.*;
import com.su60.quickboot.data.generator.query.GeneratorInterceptor;
import com.su60.quickboot.data.mybatisplus.BaseServiceImpl2;
import com.su60.quickboot.data.mybatisplus.PageUtils;
import com.su60.quickboot.generator.dos.GenTableDo;
import com.su60.quickboot.generator.dos.GenTablePreviewVo;
import com.su60.quickboot.generator.entity.GenTableColumnEntity;
import com.su60.quickboot.generator.entity.GenTableEntity;
import com.su60.quickboot.generator.mapper.GenTableMapper;
import com.su60.quickboot.generator.service.IGenTableColumnService;
import com.su60.quickboot.generator.service.IGenTableService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>
 * 代码生成业务表 服务实现类
 * </p>
 *
 * @author luyanan
 * @since 2024/10/15
 */
@RequiredArgsConstructor
@Service
public class GenTableServiceImpl extends BaseServiceImpl2<GenTableMapper, GenTableEntity, GenTableDo> implements IGenTableService {

	private final DataSource dataSource;

	private final IGenTableColumnService genTableColumnService;

	@Override
	public Boolean createTable(String content) {
		if (StrUtil.isBlank(content)) {
			return false;
		}

		List<String> tableNames = new ArrayList<>();
		try {
			List<SQLStatement> sqlStatements = SQLUtils.parseStatements(content, DbType.mysql);
			for (SQLStatement sqlStatement : sqlStatements) {
				if (sqlStatement instanceof MySqlCreateTableStatement) {

					MySqlCreateTableStatement mySqlCreateTableStatement = (MySqlCreateTableStatement) sqlStatement;
					baseMapper.createTable(mySqlCreateTableStatement.toString());
					tableNames.add(mySqlCreateTableStatement.getTableName());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new WarningException(200010, e.getLocalizedMessage());
		}
		return true;
	}



	@Override
	public PageInfo<GenTableDo> dbTables(GenTableDo tableDo) {
		PageRequest<GenTableDo> page = PageUtils.getPage(tableDo, null);

		// 从数据库查询表信息
		List<TableInfo> tableInfo = CodeGenerator.create(dataSource)

				.getTableInfo();
		if (null != tableDo) {
			if (StrUtil.isNotBlank(tableDo.getTableName())) {
				tableInfo = tableInfo.stream().filter(a -> a.getTableName().contains(tableDo.getTableName())).collect(Collectors.toList());
			}
			if (StrUtil.isNotBlank(tableDo.getTableComment())) {
				tableInfo = tableInfo.stream().filter(a -> a.getTableComment().contains(tableDo.getTableComment())).collect(Collectors.toList());
			}
		}

		// 过滤掉导入的表
		List<String> importTables = this.list().stream().map(GenTableEntity::getTableName).distinct().toList();

		tableInfo = tableInfo.stream().filter(a -> !importTables.contains(a.getTableName())).collect(Collectors.toList());
		return PageUtils.ljPage(tableInfo, page.getCurrent(), page.getSize(),
				(t) -> new GenTableDo().setTableName(t.getTableName()).setTableComment(t.getTableComment()));
	}

	@Override
	public void save(List<String> tableNames) {
		if (CollectionUtil.isEmpty(tableNames)) {
			return;
		}
		// 根据表名称导入

		List<GenTableEntity> list = this.list(new LambdaQueryWrapper<GenTableEntity>()
				.in(GenTableEntity::getTableName, tableNames));
		if (CollectionUtil.isNotEmpty(list)) {
			throw new WarningException(200011, String.join(",", tableNames));
		}
		CodeGenerator codeGenerator = CodeGenerator.create(dataSource);
		for (String tableName : tableNames) {
			codeGenerator.addInclude(tableName);
		}
		List<TableInfo> tableInfo = codeGenerator.getTableInfo();
		for (TableInfo info : tableInfo) {
			List<TableField> tableFields = info.getTableFields();
			GenTableEntity genTableEntity = converTableInfo(info);
			this.save(genTableEntity);

			List<GenTableColumnEntity> tableColumnEntities = tableFields.stream().map(a -> converTableField(a, genTableEntity)).collect(Collectors.toList());
			genTableColumnService.saveBatch(tableColumnEntities);
		}

	}

	/**
	 * 转换为GenTableEntity
	 *
	 * @param tableInfo 表信息
	 * @return GenTableEntity
	 * @since 2024/11/12
	 */
	private GenTableEntity converTableInfo(TableInfo tableInfo) {

		return new GenTableEntity()
				.setTableName(tableInfo.getTableName())
				.setTableComment(tableInfo.getTableComment())
				.setClassName(tableInfo.getClassName())
				.setFunctionAuthor("luyanan")
				.setPackageName("com.su60.quickboot")
				.setGenType("1")
				.setGenPath(System.getProperty("user.dir") + "/generaotorcode");
	}

	/**
	 * TableField 转换为 GenTableColumnEntity
	 *
	 * @param tableField tableField
	 * @param genTableDo 表信息
	 * @return GenTableColumnEntity
	 * @since 2024/10/27
	 */
	private GenTableColumnEntity converTableField(TableField tableField, GenTableEntity genTableDo) {
		GenTableColumnEntity genTableColumnDo = new GenTableColumnEntity();
		genTableColumnDo.setTableId(genTableDo.getId());
		genTableColumnDo.setColumnName(tableField.getColumnName());
		genTableColumnDo.setColumnType(tableField.getColumnType());
		genTableColumnDo.setColumnComment(tableField.getColumnComment());
		genTableColumnDo.setJavaType(tableField.getAttrType());
		genTableColumnDo.setJavaField(tableField.getAttrName());
		genTableColumnDo.setIsPk(tableField.getIsPk().equals("1") ? "0" : "1");
		genTableColumnDo.setIsRequired(tableField.getIsRequired().equals("1") ? "0" : "1");
		// 默认文本输入
		genTableColumnDo.setHtmlType(getHtmlType(tableField.getAttrType(), tableField.getColumnName()));
		// 查询方式默认为=
		genTableColumnDo.setQueryType("EQ");
		// 这里做一些常规的转换
//				genTableColumnDo.setIsIncrement(tableField.getIsIncrement())

		return genTableColumnDo;
	}

	@Override
	public void tableSyn(Long tableId) {
		GenTableEntity tableEntity = getById(tableId);
		if (null == tableEntity) {
			throw new WarningException(200012);
		}


		CodeGenerator codeGenerator = CodeGenerator.create(dataSource);
		codeGenerator.addInclude(tableEntity.getTableName());

		List<TableInfo> tableInfos = codeGenerator.getTableInfo();
		if (CollectionUtil.isEmpty(tableInfos)) {
			throw new WarningException(200012);
		}
		TableInfo tableInfo = tableInfos.get(0);
		GenTableEntity genTableEntity = converTableInfo(tableInfo);
		genTableEntity.setId(tableId);
		this.updateById(genTableEntity);

		// 表字段信息
		List<GenTableColumnEntity> tableColumnEntities = tableInfo.getTableFields().stream().map(a -> converTableField(a, tableEntity)).collect(Collectors.toList());
		List<Long> tableIds = new ArrayList<>();
		tableIds.add(tableId);
		genTableColumnService.deleteByTables(tableIds);
		genTableColumnService.saveBatch(tableColumnEntities);
	}

	@Override
	public void generator(List<Long> tableId, HttpServletResponse response, String genType) throws IOException {
		if (CollectionUtil.isEmpty(tableId)) {
			return;
		}

		List<GenTableEntity> genTableEntities = super.listByIds(tableId);
		// 获取字段配置
		List<GenTableColumnEntity> tableColumnEntities = genTableColumnService.listByTableIds(tableId);
		Map<Long, List<GenTableColumnEntity>> tableColumnMap = tableColumnEntities.stream().collect(Collectors.groupingBy(GenTableColumnEntity::getTableId));


//		String genType = "0";
//		if (tableId.size() == 1) {
//			genType = genTableEntities.get(0).getGenType();
//		}

		if (genType.equals("0")) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ZipOutputStream zip = new ZipOutputStream(outputStream);

			for (GenTableEntity genTableEntity : genTableEntities) {
				CodeGenerator.ResultHandler generator = generator(genTableEntity, tableColumnMap.get(genTableEntity.getId()));
				for (CodeGenerator.SaveStrVo saveStrVo : generator.saveStr()) {
					zip.putNextEntry(new ZipEntry(saveStrVo.getSavePath() + "/" + saveStrVo.getFileName()));
					IOUtils.write(saveStrVo.getContent(), zip, "utf-8");

					zip.flush();
					zip.closeEntry();
				}
//				for (Map.Entry<String, String> entry : generator.saveStr().entrySet()) {
//
//				}

			}
			IOUtils.closeQuietly(zip);
			byte[] byteArray = outputStream.toByteArray();
			// zip
			response.reset();
			response.addHeader("Access-Control-Allow-Origin", "*");
			response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
			response.setHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
			response.addHeader("Content-Length", "" + byteArray.length);
			response.setContentType("application/octet-stream; charset=UTF-8");
			IOUtils.write(byteArray, response.getOutputStream());
		} else {
			// 生成
			for (GenTableEntity genTableEntity : genTableEntities) {
				CodeGenerator.ResultHandler generator = generator(genTableEntity, tableColumnMap.get(genTableEntity.getId()));
				generator.saveFile(genTableEntity.getGenPath());
			}
		}


	}

	@Override
	public List<GenTablePreviewVo> preview(Long tableId) {
		List<GenTablePreviewVo> previewVos = new ArrayList<>();
		GenTableEntity genTableEntity = super.getById(tableId);
		List<GenTableColumnEntity> tableColumnEntities = genTableColumnService.listByTableId(tableId);
		CodeGenerator.ResultHandler resultHandler = generator(genTableEntity, tableColumnEntities);
		for (CodeGenerator.SaveStrVo saveStrVo : resultHandler.saveStr()) {
			GenTablePreviewVo tablePreviewVo = new GenTablePreviewVo()
					.setContent(saveStrVo.getContent())
					.setName(saveStrVo.getFileName());
			previewVos.add(tablePreviewVo);
		}
//		for (Map.Entry<String, String> entry : resultHandler.saveStr().entrySet()) {
//			String key = entry.getKey();
//			String value = entry.getValue();
//
//		}
		return previewVos;
	}


	/**
	 * 代码生成
	 *
	 * @param tableEntity         表信息
	 * @param tableColumnEntities 表字段配置
	 * @return 生成结果类
	 * @since 2024/11/12
	 */
	private CodeGenerator.ResultHandler generator(GenTableEntity tableEntity, List<GenTableColumnEntity> tableColumnEntities) {

		return CodeGenerator
				.create(dataSource).author(tableEntity.getFunctionAuthor())
				.parentPackage(tableEntity.getPackageName())
				.addInclude(tableEntity.getTableName())
				.moduleName(tableEntity.getModuleName())
				.registerTemplate(list -> {
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/entity.java.ftl")
							.setSavePath("src/main/java")
							.setPackagePath("entity")
							.setFileName("%sEntity.java"));

					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/do.java.ftl")
							.setSavePath("src/main/java")
							.setPackagePath("dos")
							.setFileName("%sDo.java")
							.setInterceptor(new GeneratorInterceptor() {
								@Override
								public void execute(TableInfo tableInfo, DataMap dataMap) {
									List<TableField> tableFields = tableInfo.getTableFields();
									Set<String> requireFields = tableColumnEntities
											.stream()
											.filter(a -> a.getIsRequired().equals("1"))
											.map(GenTableColumnEntity::getJavaField)
											.collect(Collectors.toSet());
									for (TableField tableField : tableFields) {
										if (requireFields.contains(tableField.getAttrName())) {
											tableField.setIsRequired("0");
										}
									}
								}
							}));

					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/mapper.java.ftl")
							.setSavePath("src/main/java")
							.setPackagePath("mapper")
							.setFileName("%sMapper.java"));
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/mapper.xml.ftl")
							.setSavePath("resource/mapper/{moduleName}")
							.setFileName("%sMapper.xml"));
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/service.java.ftl")
							.setSavePath("src/main/java")
							.setPackagePath("service")
							.setFileName("I%sService.java"));

					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/serviceImpl.java.ftl")
							.setSavePath("src/main/java")
							.setPackagePath("service.impl")
							.setFileName("%sServiceImpl.java"));
//
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/controller.java.ftl")
							.setSavePath("src/main/java")
							.setPackagePath("controller")
							.setFileName("%sController.java")
							.setInterceptor(new GeneratorInterceptor() {
								@Override
								public void execute(TableInfo tableInfo, DataMap dataMap) {
									List<TableField> tableFields = dataMap.getTableFields();
									// 找出来需要搜索的字段
									List<GenTableColumnEntity> searchFields = tableColumnEntities
											.stream()
											.filter(a -> StrUtil.isNotBlank(a.getIsQuery())
													&& a.getIsQuery().equals("1"))
											.collect(Collectors.toList());
									dataMap.put("searchFields", searchFields);
									dataMap.put("tableEntity", tableEntity);
								}
							}));
//
					// index.vue
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/index.vue.ftl")
							.setSavePath("views/{moduleName}/{classNameLower}")
							.setFileName("index.vue")
							.setInterceptor(new GeneratorInterceptor() {
								@Override
								public void execute(TableInfo tableInfo, DataMap dataMap) {
									// 搜索
									List<GenTableColumnEntity> searchFields = tableColumnEntities
											.stream()
											.filter(a -> StrUtil.isNotBlank(a.getIsQuery()) && a.getIsQuery().equals("1"))
											.collect(Collectors.toList());
									// 列表
									List<GenTableColumnEntity> listFields = tableColumnEntities
											.stream()
											.filter(a -> StrUtil.isNotBlank(a.getIsList()) && a.getIsList().equals("1"))
											.collect(Collectors.toList());
									dataMap.put("searchFields", searchFields);
									dataMap.put("listFields", listFields);
									dataMap.put("tableEntity", tableEntity);
								}
							}));


					// add-or-update.vue
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/add-or-update.vue.ftl")
							.setSavePath("views/{moduleName}/{classNameLower}")
							.setFileName("add-or-update.vue")
							.setInterceptor(new GeneratorInterceptor() {
								@Override
								public void execute(TableInfo tableInfo, DataMap dataMap) {
									List<GenTableColumnEntity> addOrUpdateFields = tableColumnEntities
											.stream()
											.filter(a -> StrUtil.isNotBlank(a.getIsEdit()) && a.getIsEdit().equals("1"))
											.collect(Collectors.toList());
									dataMap.put("addOrUpdateFields", addOrUpdateFields);

								}
							}));

					//  sql
					list.add(new CodeTemplate()
							.setTemplateFilePath("template2/sql.ftl")
							.setSavePath("sql/{moduleName}")
							.setFileName("%s.sql").setInterceptor((tableInfo, dataMap) -> dataMap.put("tableEntity", tableEntity)));


				})

				.execute();


	}

	/**
	 * 获取html类型
	 *
	 * @param javaType   java类型
	 * @param columnName 字段名称
	 * @return html类型
	 * @since 2024/11/12
	 */
	private String getHtmlType(String javaType, String columnName) {
		if (javaType.equals("Date")) {
			return "datetime";
		}
		if (columnName.endsWith("_path")) {
			return "imageUpload";
		}

		return "input";
	}

	@Override
	public Boolean deleteByIds(Collection<? extends Serializable> ids) {
		// 删除字典
		genTableColumnService.deleteByTables(ids);
		return super.deleteByIds(ids);
	}
}

