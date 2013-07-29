package org.longrm.forum.dao;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DAOFactory {

	private static DAOFactory instance = null;
	private static DataSource ds = null;
//	private final static String USER = "forum";
//	private final static String PASSWORD = "123";
//	private final static String DATABASE = "localhost:1521:ORCL";
	
	/**
	 * 单例模式，类方法声明为private
	 * 初始化DataSource
	 */
	private DAOFactory() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/oracle");
//			ds.setUser(USER);
//			ds.setPassword(PASSWORD);
//			ds.setURL("jdbc:oracle:thin:@" + DATABASE);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过getInstance()获得类实例对象
	 * @return instance
	 */
	public static synchronized DAOFactory getInstance() {
		if (instance==null) {
			instance = new DAOFactory();
		}
		return instance;
	}
	
	public DataSource getDataSource() {
		return ds;
	}
	
	/**
	 * 获取数据库连接
	 * @return connection
	 */
	public Connection getConnection() {
		try {
			return ds.getConnection();
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取properties文件获取数据库配置
	 * @return Connection
	 */
	public Connection getConnectionFromFile() throws Exception {
		Properties props = new Properties();
		String fileName = "C:\\Program Files\\Tomcat 5.5\\webapps\\chinalong\\database.properties";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);

		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		in.close();
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); // 装载数据库驱动
		return DriverManager.getConnection(url, username, password);
	}
}
