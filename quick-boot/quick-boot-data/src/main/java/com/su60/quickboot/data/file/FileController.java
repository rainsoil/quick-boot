package com.su60.quickboot.data.file;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.su60.quickboot.common.core.R;
import com.su60.quickboot.data.file.utils.FileTypeDetector;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件控制层
 *
 * @author luyanan
 * @since 2024/05/29
 **/
@RequiredArgsConstructor
@Controller
@RequestMapping("file")
public class FileController {

	private final FileTemplate fileTemplate;

	/**
	 * 文件上传
	 *
	 * @param file     文件
	 * @param classify 分类
	 * @return 文件信息
	 * @since 2024/05/30
	 */

	@ResponseBody
	@PostMapping("upload/{classify}")
	public R<FileInfoVo> upload(@RequestParam("file") MultipartFile file, @PathVariable("classify") String classify) throws IOException {
		FileInfoVo upload = fileTemplate.upload(file.getBytes(), file.getOriginalFilename(), classify);
		return R.ok(upload);
	}


	/**
	 * 图片查看
	 *
	 * @param request  请求类
	 * @param response 响应类
	 * @param classify 分类
	 * @return
	 * @since 2024/06/15
	 */
	@GetMapping("view/{classify}/**")
	public void view(HttpServletRequest request, HttpServletResponse response, @PathVariable("classify") String classify) throws IOException {
		String requestURI = request.getRequestURI();
		byte[] download = fileTemplate.download(requestURI);
		String contentType = FileTypeDetector.detectFileType(requestURI, download);
		response.setContentType(contentType);
		response.getOutputStream().write(download);

	}


}
