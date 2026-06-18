package util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DButil {
	/*
	 JDBC_URLの差異に対応する
	 */
	private static String jdbcUrl;

	/*db.propertiesからJDBC_URLを取得し、実行する*/
	static {
		try {
			Properties prop = new Properties();
			InputStream is = DButil.class.getClassLoader().getResourceAsStream("db.properties");
			prop.load(is);
			jdbcUrl = prop.getProperty("jdbc.url");
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/*DB接続を取得する*/
	public static Connection getConnection() throws Exception {
		return DriverManager.getConnection(jdbcUrl);
	}
}