package io.github.rainsoil.fastboot.core.mybatisplus;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseBaseMapper;
import io.github.rainsoil.fastboot.common.data.mybatis.BaseServiceImpl;
import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastboot.common.utils.YmlUtils;
import io.github.rainsoil.fastboot.core.BaseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;
import java.util.Map;

/**
 * 代码生成
 *
 * @author luyanan
 * @since 2023/09/19
 **/
public class CodeGenerator {


	/**
	 * 启动类
	 *
	 * @param args 参数
	 * @since 2023/09/27
	 */
	public static void main(String[] args) {

		String filePath = "E:/fastapi";
		String configPath = System.getProperty("user.dir") + "/fastboot-web/src/main/resources/application-dev.yml";
		Map<String, String> configs = YmlUtils.getYmlByFileName(configPath);

		String url = configs.get("spring.datasource.url");
		String username = configs.get("spring.datasource.username");
		String password = configs.get("spring.datasource.password");
		FastAutoGenerator.create(url,
						username,
						password)
				.strategyConfig(builder -> {
					builder

							.entityBuilder().superClass(BaseEntity.class).addIgnoreColumns("create_time", "update_time", "create_by", "update_by", "del_flag")
//                            .disableSerialVersionUID()
							.enableChainModel()
							.enableLombok()
							.enableTableFieldAnnotation()
							.enableTableFieldAnnotation()
//                            .enableRemoveIsPrefix()
							.enableTableFieldAnnotation()
//                            .enableActiveRecord()
							.versionColumnName("version")
							.versionPropertyName("version")
							.logicDeleteColumnName("del_flag")
							.logicDeletePropertyName("delFlag")
							.fileOverride()
//                            .naming(NamingStrategy.no_change)
							.columnNaming(NamingStrategy.underline_to_camel)
//                            .addTableFills(new Column("create_user", FieldFill.INSERT))
//                            .addTableFills(new Column("update_user", FieldFill.INSERT_UPDATE))
//                            .addTableFills(new Column("create_time", FieldFill.INSERT))
//                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
							.idType(IdType.ASSIGN_ID)
							.controllerBuilder().enableRestStyle()
							.mapperBuilder().superClass(BaseBaseMapper.class).enableMapperAnnotation().mapperAnnotation(Mapper.class)
							.serviceBuilder().superServiceClass(IBaseService.class)
							.serviceBuilder().superServiceImplClass(BaseServiceImpl.class)
							.build();
				})
				.globalConfig(builder -> {
					builder.author("luyanan") // 设置作者
//                            .enableSpringdoc()
							.enableSwagger() // 开启 swagger 模式
							.fileOverride() // 覆盖已生成文件

							.outputDir(filePath); // 指定输出目录
				})

				.packageConfig(builder -> {
					builder.parent("io.github.rainsoil.fastboot") // 设置父包名
//							.moduleName("system") // 设置父包模块名
							.moduleName("generator") // 设置父包模块名
							.pathInfo(Collections.singletonMap(OutputFile.xml, filePath)); // 设置mapperXml生成路径
				})
				.strategyConfig(builder -> {


//					builder.likeTable(new LikeTable("sys_combo_quan"))
					builder.addInclude("g_.*") // 设置需要生成的表名
							.addTablePrefix("syssss_"); // 设置过滤表前缀
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
					builder.customFile(b -> {
						b.filePath("vue").fileName("avue.vue").enableFileOverride()
								.templatePath("/templates/avue.vue.ftl").build();
					});
				})
				.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
				.execute();
	}
}
