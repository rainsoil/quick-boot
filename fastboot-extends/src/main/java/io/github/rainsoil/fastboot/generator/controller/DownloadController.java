package io.github.rainsoil.fastboot.generator.controller;

import io.github.rainsoil.fastboot.generator.service.IGTableInfoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载的controller
 *
 * @author luyanan
 * @since 2024/01/28
 **/
@Controller
@RequiredArgsConstructor
@RequestMapping("/generator/gTableInfo")
public class DownloadController {

	private final IGTableInfoService igTableInfoService;

	/**
	 * 代码生成,并达成zip包
	 *
	 * @param id       id
	 * @param response 响应
	 * @return
	 * @since 2024/01/28
	 */
	@ApiOperation(value = "代码生成")
	@GetMapping("generator")
	public void generator(Long id, HttpServletResponse response) {
		igTableInfoService.generator(id, response);
	}
}
