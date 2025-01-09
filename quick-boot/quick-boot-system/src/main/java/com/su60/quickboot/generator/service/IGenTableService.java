package com.su60.quickboot.generator.service;

import com.su60.quickboot.common.core.PageInfo;
import com.su60.quickboot.generator.dos.GenTablePreviewVo;
import com.su60.quickboot.generator.entity.GenTableEntity;
import com.su60.quickboot.generator.dos.GenTableDo;
import com.su60.quickboot.data.mybatisplus.IBaseService2;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 代码生成业务表 服务类
 * </p>
 *
 * @author luyanan
 * @since 2024/10/15
 */

public interface IGenTableService extends IBaseService2<GenTableEntity, GenTableDo> {

	/**
	 * 根据sql生成表
	 *
	 * @param content sql
	 * @return 是否成功
	 * @since 2024/10/23
	 */
	Boolean createTable(String content);


	/**
	 * 数据库表
	 *
	 * @param tableDo 表条件
	 * @return 分页返回
	 * @since 2024/10/24
	 */
	PageInfo<GenTableDo> dbTables(GenTableDo tableDo);


	/**
	 * 保存
	 *
	 * @param tableNames 表名称
	 * @since 2024/10/24
	 */
	void save(List<String> tableNames);

	/**
	 * 表信息同步
	 *
	 * @param tableId 表id
	 * @since 2024/10/27
	 */
	void tableSyn(Long tableId);

	/**
	 * 代码生成
	 *
	 * @param tableId  表id
	 * @param response 响应类
	 * @param genType  生成类型
	 * @since 2024/10/27
	 */
	void generator(List<Long> tableId, HttpServletResponse response, String genType) throws IOException;

	/**
	 * 预览
	 *
	 * @param tableId 表id
	 * @return 代码生成结果
	 * @since 2024/10/30
	 */
	List<GenTablePreviewVo> preview(Long tableId);


}
