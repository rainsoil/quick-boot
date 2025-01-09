package com.su60.quickboot.data.file;

import lombok.extern.slf4j.Slf4j;
import org.dromara.x.file.storage.core.FileInfo;
import org.dromara.x.file.storage.core.UploadPretreatment;
import org.dromara.x.file.storage.core.aspect.FileStorageAspect;
import org.dromara.x.file.storage.core.aspect.UploadAspectChain;
import org.dromara.x.file.storage.core.platform.FileStorage;
import org.dromara.x.file.storage.core.recorder.FileRecorder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义文件上传切片
 *
 * @author luyanan
 * @since 2024/05/29
 **/
@Slf4j
public class CustomFileStorageAspect implements FileStorageAspect {

	/**
	 * 文件分类
	 *
	 * @since 2024/11/12
	 */

	@Autowired
	private FileClassifyService fileClassifyService;

	@Override
	public FileInfo uploadAround(UploadAspectChain chain, FileInfo fileInfo, UploadPretreatment pre, FileStorage fileStorage, FileRecorder fileRecorder) {
		String classify = fileInfo.getPath();
		fileClassifyService.limit(fileInfo.getExt(), fileInfo.getSize(), classify);
		String md5 = fileInfo.getAttr().get("md5").toString();
		fileInfo.setFilename(fileClassifyService.fileName(classify, md5, fileInfo.getExt()));
		return chain.next(fileInfo, pre, fileStorage, fileRecorder);
	}
}
