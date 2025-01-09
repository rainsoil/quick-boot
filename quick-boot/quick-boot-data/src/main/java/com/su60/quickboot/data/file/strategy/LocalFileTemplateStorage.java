package com.su60.quickboot.data.file.strategy;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import com.su60.quickboot.common.exception.Assert;
import com.su60.quickboot.data.file.AbstractFileTemplate;
import com.su60.quickboot.data.file.FileInfoVo;
import com.su60.quickboot.data.file.FileProperties;
import com.su60.quickboot.data.file.utils.FileTypeDetector;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 本地存储
 *
 * @author luyanan
 * @since 2025/1/1
 */
@RequiredArgsConstructor
public class LocalFileTemplateStorage extends AbstractFileTemplate {

	private final FileProperties fileProperties;

	@Override
	protected void doUpload(byte[] bytes, FileInfoVo fileInfoVo) {
		FileProperties.LocalFileProperties local = fileProperties.getLocal();
		Assert.notNull(local, "local配置文件不能为空");
		FileUtil.writeBytes(bytes, local.getPath() + "/" + fileInfoVo.getPath() + "/" + fileInfoVo.getFileName());
	}

	@Override
	protected byte[] doDownload(String fileName) {
		FileProperties.LocalFileProperties local = fileProperties.getLocal();
		Assert.notNull(local, "local配置文件不能为空");
		return FileUtil.readBytes(local.getPath() + "/" + fileName);
	}


}
