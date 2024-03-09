package io.github.rainsoil.fastboot.generator.service;

import io.github.rainsoil.fastboot.common.data.mybatis.IBaseService;
import io.github.rainsoil.fastboot.core.vo.DictVo;
import io.github.rainsoil.fastboot.generator.entity.GTableInfo;
import io.github.rainsoil.fastboot.generator.vo.LoadTableVo;
import io.github.rainsoil.fastboot.generator.vo.PreviewVo;
import io.github.rainsoil.fastboot.system.entity.SysMenu;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 表信息 服务类
 * </p>
 *
 * @author luyanan
 * @since 2024-01-16
 */
public interface IGTableInfoService extends IBaseService<GTableInfo> {

	/**
	 * 根据数据源id获取表
	 *
	 * @param datasourceId 数据源id
	 * @return
	 * @since 2024/01/21
	 */
	List<DictVo> listTableByDatabaseId(Long datasourceId);

	/**
	 * 加载表
	 *
	 * @param loadTableVo 加载表的vo类
	 * @return
	 * @since 2024/01/21
	 */
	void loadTable(LoadTableVo loadTableVo);

	/**
	 * 查询表详情
	 *
	 * @param id 表id
	 * @return
	 * @since 2024/01/21
	 */
	GTableInfo info(Long id);

	/**
	 * 修改表的信息
	 *
	 * @param gTableInfo 表信息
	 * @return
	 * @since 2024/01/27
	 */
	void updateTableInfo(GTableInfo gTableInfo);

	/**
	 * 代码生成
	 *
	 * @param id       id
	 * @param response 响应
	 * @return
	 * @since 2024/01/28
	 */
	void generator(Long id, HttpServletResponse response);

	/**
	 * 代码预览
	 *
	 * @param id 表信息id
	 * @return
	 * @since 2024/01/28
	 */
	List<PreviewVo> preview(Long id);

	/**
	 * 生成菜单
	 *
	 * @param id      表信息
	 * @param sysMenu 菜单信息
	 * @return
	 * @since 2024/01/29
	 */
	void createMenu(Long id, SysMenu sysMenu);

}
