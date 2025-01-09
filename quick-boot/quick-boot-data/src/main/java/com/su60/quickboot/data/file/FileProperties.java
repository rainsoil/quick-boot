package com.su60.quickboot.data.file;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 文件配置
 *
 * @author luyanan
 * @since 2024/05/29
 **/

@Data
@ConfigurationProperties("file")
public class FileProperties {

	/**
	 * 文件前缀
	 *
	 * @since 2024/06/15
	 */

	private String domain;


	/**
	 * 基础路径
	 *
	 * @since 2025/1/7
	 */

	private String basePath;

	/**
	 * 默认的分类
	 *
	 * @since 2024/06/04
	 */

	private FileClassify defaultClassify;

	/**
	 * 文件分类配置
	 *
	 * @since 2024/06/04
	 */

	private List<FileClassify> fileClassifies;


	/**
	 * S3文件存储配置文件
	 *
	 * @since 2025/1/6
	 */

	private S3FileProperties s3;

	/**
	 * 本地存放的配置文件
	 *
	 * @since 2025/1/6
	 */

	private LocalFileProperties local;


	/**
	 * 文件上传的类型(local/minio)
	 *
	 * @since 2025/1/6
	 */

	private String type;

	/**
	 * 文件分类配置
	 *
	 * @since 2024/06/04
	 */

	@Data
	public static class FileClassify {


		/**
		 * 分类
		 *
		 * @since 2024/06/04
		 */

		private String classify;


		/**
		 * 限制文件后缀
		 *
		 * @since 2024/06/04
		 */

		private String limitExt;


		/**
		 * 限制文件大小(kb)
		 *
		 * @since 2024/06/04
		 */

		private Long limitSize;

	}


	/**
	 * 本地文件配置
	 *
	 * @author luyanan
	 * @since 2025/1/6
	 */
	@Data
	public static class LocalFileProperties {


		/**
		 * 本地文件存放路径
		 *
		 * @author luyanan
		 * @since 2025/1/6
		 */
		private String path;
	}

	/**
	 * S3 文件存储配置文件
	 *
	 * @author luyanan
	 * @since 2025/1/6
	 */
	public static class S3FileProperties {

	}
}
