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
	 * ����ģʽ���෽������Ϊprivate
	 * ��ʼ��DataSource
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
	 * ͨ��getInstance()�����ʵ������
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
	 * ��ȡ���ݿ�����
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
	 * ��ȡproperties�ļ���ȡ���ݿ�����
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
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver"); // װ�����ݿ�����
		return DriverManager.getConnection(url, username, password);
	}
}
