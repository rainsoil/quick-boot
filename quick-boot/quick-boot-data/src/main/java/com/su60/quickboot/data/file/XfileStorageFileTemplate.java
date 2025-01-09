//package com.su60.quickboot.data.file;
//
//import cn.hutool.crypto.digest.MD5;
//import com.su60.quickboot.data.file.exception.FileExtLimitException;
//import com.su60.quickboot.data.file.exception.FileSizeLimitException;
//import lombok.RequiredArgsConstructor;
//import org.dromara.x.file.storage.core.Downloader;
//import org.dromara.x.file.storage.core.FileInfo;
//import org.dromara.x.file.storage.core.FileStorageProperties;
//import org.dromara.x.file.storage.core.FileStorageService;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.OutputStream;
//
///**
// * x-file-storage实现
// *
// * @author luyanan
// * @since 2024/06/15
// **/
//@RequiredArgsConstructor
//public class XfileStorageFileTemplate extends AbstractFileTemplate {
//
//	@Autowired
//	private FileStorageService fileStorageService;
//
//
//	@Override
//	public FileInfoVo upload(byte[] bytes, String fileName, String path) {
//		String md5 = MD5.create().digestHex(bytes);
//
//		FileInfo upload = null;
//		try {
//			upload = fileStorageService.of(bytes)
//					.setOriginalFilename(fileName)
//					.setPath(path)
//					.putAttr("md5", md5)
//					.upload();
//		} catch (FileExtLimitException e) {
//			System.out.println("文件格式不正确");
//		} catch (FileSizeLimitException e) {
//			System.out.println("文件太大了");
//		} catch (Exception e) {
//			System.out.println("文件上传失败");
//		}
//		return new FileInfoVo()
//				.setUrl(upload.getUrl())
//				.setSize(upload.getSize())
//				.setExt(upload.getExt())
//				.setContentType(upload.getContentType())
//				.setBasePath(upload.getBasePath())
//				.setFileName(upload.getFilename())
//				.setPath(upload.getPath());
//
//	}
//
//	@Override
//	public void download(OutputStream out, String fileName) {
//		FileStorageProperties properties = fileStorageService.getProperties();
//		FileInfo fileInfo = new FileInfo();
//		fileInfo.setPlatform(properties.getDefaultPlatform());
//		fileInfo.setFilename(fileName);
//		Downloader download = fileStorageService.download(fileInfo);
//		download.outputStream(out);
//	}
//}
