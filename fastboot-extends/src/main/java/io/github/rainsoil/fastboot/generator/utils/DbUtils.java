package io.github.rainsoil.fastboot.generator.utils;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.query.DefaultQuery;
import lombok.experimental.UtilityClass;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * 数据库工具类
 *
 * @author luyanan
 * @since 2024/01/15
 **/
@UtilityClass
public class DbUtils {

	/**
	 * 获取连接
	 *
	 * @param dbType   数据库类型
	 * @param url      yrl
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 * @since 2024/01/17
	 */
	public Connection getConnection(String dbType, String url, String username, String password) throws SQLException, ClassNotFoundException {
		DriverManager.setLoginTimeout(6);
		Class.forName(dbType);
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}

	//	public static void main(String[] args) throws
//			InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//
//		DbUtils dbUtils = new DbUtils();
//		dbUtils.getTable();
//		;
//	}
//

	/**
	 * 获取表列表
	 *
	 * @param url      url
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 * @since 2024/01/21
	 */
	public List<TableInfo> getTable(String url, String username, String password) throws
			NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//		String url = "jdbc:mysql://fastMysql:13306/fastapi?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B8&allowMultiQueries=true&allowPublicKeyRetrieval=true";
		DataSourceConfig build = new DataSourceConfig.Builder(url, username, password).build();

		ConfigBuilder configBuilder = new ConfigBuilder(null, build, null, null, null, null);
		DefaultQuery defaultQuery = new DefaultQuery(configBuilder);
		return defaultQuery.queryTables();
	}
}
