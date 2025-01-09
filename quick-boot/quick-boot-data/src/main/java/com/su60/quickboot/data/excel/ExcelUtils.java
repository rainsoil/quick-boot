package com.su60.quickboot.data.excel;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.converters.longconverter.LongStringConverter;
import com.alibaba.excel.read.listener.ReadListener;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * excel工具类
 *
 * @author luyanan
 * @since 2024/06/16
 **/
@UtilityClass
public class ExcelUtils {
	/**************************导出***************************/


	/**
	 * 导出到excel
	 *
	 * @param response  响应类
	 * @param fileName  文件名
	 * @param sheetName sheetName
	 * @param datas     数据
	 * @param pojoClass 对象class
	 * @since 2024/06/16
	 */
	public void exportExcel(HttpServletResponse response, String fileName,
							String sheetName, List<?> datas, Class<?> pojoClass) throws IOException {

		if (StrUtil.isBlank(fileName)) {
			fileName = DateUtil.date().toString();
		}
		response.setContentType("application/vnd.ms-excel");
		response.setCharacterEncoding("UTF-8");

		fileName = URLUtil.encode(fileName, StandardCharsets.UTF_8);
		response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
		response.setHeader("Content-filename", "filename=" + fileName + ".xlsx");
		EasyExcel.write(response.getOutputStream(), pojoClass).registerConverter(new LongStringConverter()).sheet(sheetName).doWrite(datas);
	}


	/******************读取excel***********************/

	/**
	 * 读取excel
	 *
	 * @param file        文件
	 * @param eClass      excel的class类
	 * @param lineHandler 行处理器
	 * @param <E>         execl 类的泛型
	 * @return
	 * @since 2024/06/28
	 */
	public <E> void read(MultipartFile file, Class<E> eClass, LineHandler<E> lineHandler) throws IOException {

		EasyExcel.read(file.getInputStream(), eClass, new ReadListener<E>() {
			@Override
			public void invoke(E data, AnalysisContext context) {
				lineHandler.handler(data);
			}

			@Override
			public void doAfterAllAnalysed(AnalysisContext context) {

			}
		}).sheet().doRead();

	}


	/**
	 * 行处理器
	 *
	 * @param <E> excel的泛型
	 * @author luyanan
	 * @since 2024/06/28
	 */
	public interface LineHandler<E> {

		/**
		 * 行处理
		 *
		 * @param e excel的泛型
		 * @return
		 * @since 2024/06/16
		 */
		void handler(E e);

	}

}
