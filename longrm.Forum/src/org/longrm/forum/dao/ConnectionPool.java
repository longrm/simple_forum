package org.longrm.forum.dao;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private String jdbcDriver = ""; // ���ݿ�����
	private String dbUrl = ""; // Database URL
	private String username = "";
	private String password = "";

	private String testTable = ""; // ���������Ƿ���õĲ��Ա�����Ĭ��û�в��Ա�

	private int initConnections = 10; // ���ӳس�ʼ��С
	private int incrementalConnections = 5; // ���ӳ��Զ����ӵĴ�С
	private int maxConnections = 50; // ���ӳ�����������

	@SuppressWarnings("unchecked")
	private Vector connections = null; // ������ӳ������ݿ����ӵ�Vector , ��ʼʱΪ null

	/**
	 * Ĭ�Ϲ��캯��
	 */
	public ConnectionPool() {
	}

	/**
	 * �Զ��幹�캯��
	 * 
	 * @param jdbcDriver
	 *            ����
	 * @param dbUrl
	 *            ���ݿ�URL
	 * @param username
	 *            �û���
	 * @param password
	 *            ����
	 */
	public ConnectionPool(String jdbcDriver, String dbUrl, String username,
			String password) {
		this.jdbcDriver = jdbcDriver;
		this.dbUrl = dbUrl;
		this.username = username;
		this.password = password;
	}

	/**
	 * ��ȡ�������ݿ�������
	 * 
	 * @return
	 */
	public String getTestTable() {
		return testTable;
	}

	/**
	 * ���ò������ݿ�������
	 * 
	 * @param testTable
	 */
	public void setTestTable(String testTable) {
		this.testTable = testTable;
	}

	/**
	 * ��ȡ���ӳصĳ�ʼ��С
	 * 
	 * @return ��ʼ���ӳ��пɻ�õ���������
	 */
	public int getInitConnections() {
		return initConnections;
	}

	/**
	 * �������ӳصĳ�ʼ��С
	 * 
	 * @param initConnections
	 *            �������ó�ʼ���ӳ������ӵ�����
	 */
	public void setInitConnections(int initConnections) {
		this.initConnections = initConnections;
	}

	/**
	 * ��ȡ���ӳ��Զ����ӵĴ�С
	 * 
	 * @return ���ӳ��Զ����ӵĴ�С
	 */
	public int getIncrementalConnections() {
		return incrementalConnections;
	}

	/**
	 * �������ӳ��Զ����ӵĴ�С
	 * 
	 * @param incrementalConnections
	 *            ���ӳ��Զ����ӵĴ�С
	 */
	public void setIncrementalConnections(int incrementalConnections) {
		this.incrementalConnections = incrementalConnections;
	}

	/**
	 * ��ȡ���ӳ������Ŀ�����������
	 * 
	 * @return ���ӳ������Ŀ�����������
	 */
	public int getMaxConnections() {
		return maxConnections;
	}

	/**
	 * �������ӳ��������õ���������
	 * 
	 * @param maxConnections
	 *            �������ӳ��������õ���������ֵ
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	/**
	 * ʹ����ȴ������ĺ�����
	 * 
	 * @param mSeconds
	 *            �����ĺ�����
	 */
	private void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * ˢ�����ӳ������е����Ӷ���
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public synchronized void refreshConnections() throws SQLException {
		// ȷ�����ӳؼ����´���
		if (connections == null) {
			System.out.println(" ���ӳز����ڣ��޷�ˢ�� !");
			return;
		}

		PooledConnection pooledConn = null;
		Enumeration enu = connections.elements();
		while (enu.hasMoreElements()) {
			pooledConn = (PooledConnection) enu.nextElement();// ���һ�����Ӷ���

			// �������æ��� 5 �� ,5 ���ֱ��ˢ��
			if (pooledConn.isBusy()) {
				wait(5000);
			}

			// �رմ����ӣ���һ���µ����Ӵ�������
			closeConnection(pooledConn.getConn());
			pooledConn.setConn(newConnection());
			pooledConn.setBusy(false);
		}
	}

	/**
	 * �ر����ӳ������е����ӣ���������ӳء�
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public synchronized void closeConnectionPool() throws SQLException {
		// ȷ�����ӳش��ڣ���������ڣ�����
		if (connections == null) {
			System.out.println(" ���ӳز����ڣ��޷��ر� !");
			return;
		}

		PooledConnection pooledConn = null;
		Enumeration enu = connections.elements();
		while (enu.hasMoreElements()) {
			pooledConn = (PooledConnection) enu.nextElement();// ���һ�����Ӷ���

			// �������æ��� 5 �� ,5 ���ֱ��ˢ��
			if (pooledConn.isBusy()) {
				wait(5000);
			}

			// 5 ���ֱ�ӹر���
			closeConnection(pooledConn.getConn());

			// �����ӳ�������ɾ����
			connections.removeElement(pooledConn);

			// �������ӳ�Ϊ��
			connections = null;
		}
	}

	/**
	 * �ر�һ�����ݿ�����
	 * 
	 * @param conn
	 *            ��Ҫ�رյ����ݿ�����
	 */
	private void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println(" �ر����ݿ����ӳ��� " + e.getMessage());
		}
	}

	/**
	 * ����һ���µ����ݿ����Ӳ�������
	 * 
	 * @return ����һ���´��������ݿ�����
	 * @throws SQLException
	 */
	private Connection newConnection() throws SQLException {
		Connection conn = DriverManager
				.getConnection(dbUrl, username, password);// ����һ�����ݿ�����

		// ������ǵ�һ�δ������ݿ����ӣ���������ݿ⣬��ô����ݿ�����֧�ֵ����ͻ�������Ŀ
		// connections.size()==0 ��ʾĿǰû�����Ӽ�������

		if (connections.size() == 0) {
			DatabaseMetaData metadata = conn.getMetaData();

			// DatabaseMetaData�ӿ����������ݿ�Ԫ���ݵġ����ǿ��Դ���ȡ�����ݿ�ı���ͼ����Ϣ��

			int driverMaxConnections = metadata.getMaxConnections();

			// ���ݿⷵ�ص� driverMaxConnections ��Ϊ 0 ����ʾ�����ݿ�û�����
			// �������ƣ������ݿ������������Ʋ�֪��
			// driverMaxConnections Ϊ���ص�һ����������ʾ�����ݿ�����ͻ����ӵ���Ŀ
			// ������ӳ������õ�������������������ݿ������������Ŀ , �������ӳص����������ĿΪ���ݿ�����������Ŀ

			if (driverMaxConnections > 0
					&& this.maxConnections > driverMaxConnections) {
				this.maxConnections = driverMaxConnections;
			}
		}

		return conn;
	}

	/**
	 * ������ numConnections ָ����Ŀ�����ݿ����� , ������Щ���� ���� connections ������
	 * 
	 * @param numConnections
	 *            Ҫ���������ݿ����ӵ���Ŀ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void createConnections(int numConnections) throws SQLException {
		for (int i = 0; i < numConnections; i++) { // ѭ������ָ����Ŀ�����ݿ�����
			// �Ƿ����ӳ��е����ݿ����ӵ����������ﵽ������ֵ�����Ա maxConnectionsָ��
			// ��� maxConnections Ϊ 0 ��������ʾ��������û�����ơ�
			// ��������������ﵽ��󣬼��˳���
			if (this.maxConnections > 0
					&& this.connections.size() >= this.maxConnections) {
				break;
			}

			// add a new PooledConnection object to connections vector
			// ����һ�����ӵ����ӳ��У����� connections �У�

			try {
				connections.addElement(new PooledConnection(newConnection()));
			} catch (SQLException e) {
				System.out.println(" �������ݿ�����ʧ�ܣ� " + e.getMessage());
				throw new SQLException();
			}

			System.out.println(" ���ݿ����Ӽ����� ");
		}
	}

	/**
	 * ����һ�����ݿ����ӳأ����ӳ��еĿ������ӵ������������ԱinitConnections �����õ�ֵ
	 * 
	 * @throws Exception
	 * 
	 * Java synchronized ����
	 * 
	 * һ�������������̷߳���ͬһ������object�е����synchronized(this)ͬ�������ʱ��
	 * һ��ʱ����ֻ����һ���̵߳õ�ִ�С���һ���̱߳���ȴ���ǰ�߳�ִ�������������Ժ����ִ�иô���顣
	 * ����Ȼ������һ���̷߳���object��һ��synchronized(this)ͬ�������ʱ��
	 * ��һ���߳���Ȼ���Է��ʸ�object�еķ�synchronized(this)ͬ������顣
	 * ��������ؼ����ǣ���һ���̷߳���object��һ��synchronized(this)ͬ�������ʱ��
	 * �����̶߳�object����������synchronized(this)ͬ�������ķ��ʽ���������
	 * �ġ�����������ͬ����������ͬ������顣Ҳ����˵����һ���̷߳���object��һ��synchronized(this)ͬ�������ʱ��
	 * ���ͻ�������object�Ķ�����������������̶߳Ը�object��������ͬ�����벿�ֵķ��ʶ�����ʱ������ �塢���Ϲ��������������ͬ������
	 */
	@SuppressWarnings("unchecked")
	public synchronized void createPool() throws Exception {
		// ȷ�����ӳ�û�д���
		// ������ӳؼ��������ˣ��������ӵ�Vector�� connections ����Ϊ��

		if (connections != null) {
			return; // ����Ѿ��������򷵻�
		}

		Driver driver = (Driver) (Class.forName(this.jdbcDriver)).newInstance();
		// ʵ���� JDBC Driver ��ָ����������ʵ��

		DriverManager.registerDriver(driver);// ע��jdbc����

		connections = new Vector();// �����������ӵ����� , ��ʼʱ�� 0 ��Ԫ��
		// ���� initialConnections �����õ�ֵ���������ӡ�
		createConnections(this.initConnections);
		System.out.println("���ݿ����ӳش����ɹ�!");
	}

	/**
	 * �˺�������һ�����ݿ����ӵ����ӳ��У����Ѵ�������Ϊ���� ����ʹ�����ӳػ�õ����ݿ����Ӿ�Ӧ�ڲ�ʹ�ô�����ʱ������
	 * 
	 * @param conn
	 *            �践�ص����ӳ��е����Ӷ���
	 */
	@SuppressWarnings("unchecked")
	public void returnConnection(Connection conn) {
		// ȷ�����ӳش��ڣ��������û�д����������ڣ���ֱ�ӷ���
		if (connections == null) {
			System.out.println("���ӳز����ڣ��޷����ش����ӵ����ӳ��� !");
			return;
		}
		PooledConnection pooledConn = null;
		Enumeration enmu = connections.elements();// �������ӳ��е��������ӣ��ҵ����Ҫ���ص����Ӷ���
		while (enmu.hasMoreElements()) {
			pooledConn = (PooledConnection) enmu.nextElement();
			// ���ҵ����ӳ��е�Ҫ���ص����Ӷ���

			if (conn == pooledConn.getConn()) {// �ҵ��� , ���ô�����Ϊ����״̬
				pooledConn.setBusy(false);
				break;
			}
		}
	}

	/**
	 * ����һ�������Ƿ���ã���������ã��ص��������� false ������÷��� true
	 * 
	 * @param conn
	 *            ��Ҫ���Ե����ݿ�����
	 * @return ���� true ��ʾ�����ӿ��ã� false ��ʾ������
	 */
	private boolean testConnection(Connection conn) {
		try {
			// �жϲ��Ա��Ƿ����
			if (testTable.equals("")) {
				// ������Ա�Ϊ�գ�����ʹ�ô����ӵ� setAutoCommit() ����
				// ���ж����ӷ���ã��˷���ֻ�ڲ������ݿ���ã���������� ,
				// �׳��쳣����ע�⣺ʹ�ò��Ա�ķ������ɿ�

				conn.setAutoCommit(true);
			} else {// �в��Ա��ʱ��ʹ�ò��Ա����
				Statement sta = conn.createStatement();
				sta.execute("select count(*) from " + testTable);
			}
		} catch (SQLException e) {
			// �����׳��쳣�������Ӽ������ã��ر����������� false;
			closeConnection(conn);
			return false;
		}
		// ���ӿ��ã����� true
		return true;
	}

	/**
	 * �������ӳ������е����ӣ�����һ�����õ����ݿ����� ���û�п��õ����ӣ����� null
	 * 
	 * @return ����һ�����õ����ݿ�����
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private Connection findFreeConnection() throws SQLException {
		Connection conn = null;
		PooledConnection pooledConn = null;

		// ������ӳ����������еĶ���
		Enumeration enu = connections.elements();
		// �������еĶ��󣬿��Ƿ��п��õ�����
		while (enu.hasMoreElements()) {
			pooledConn = (PooledConnection) enu.nextElement();
			if (!pooledConn.isBusy()) {
				// ����˶���æ�������������ݿ����Ӳ�������Ϊæ
				conn = pooledConn.getConn();
				pooledConn.setBusy(true);

				// ���Դ������Ƿ����
				if (!testConnection(conn)) {
					// ��������Ӳ��������ˣ��򴴽�һ���µ����ӣ�
					// ���滻�˲����õ����Ӷ����������ʧ�ܣ����� null
					try {
						conn = newConnection();
					} catch (SQLException e) {
						System.out.println(" �������ݿ�����ʧ�ܣ� " + e.getMessage());
						return null;
					}
					pooledConn.setConn(conn);
				}
				break;// �����ҵ�һ�����õ����ӣ��˳�
			}
		}
		return conn;// �����ҵ����Ŀ�������
	}

	/**
	 * �����������ӳ����� connections �з���һ�����õĵ����ݿ����ӣ�
	 * �����ǰû�п��õ����ݿ����ӣ������������incrementalConnections���õ�ֵ�����������ݿ����ӣ�
	 * ���������ӳ��С� ������������е������Զ���ʹ���У��򷵻� null
	 * 
	 * @return ����һ�����õ����ݿ�����
	 * @throws SQLException
	 */
	private Connection getFreeConnection() throws SQLException {

		// �����ӳ��л��һ�����õ����ݿ�����
		Connection conn = findFreeConnection();

		if (conn == null) {
			// ���Ŀǰ���ӳ���û�п��õ�����
			// ����һЩ����
			createConnections(incrementalConnections);

			// ���´ӳ��в����Ƿ��п�������
			conn = findFreeConnection();
			if (conn == null) {
				// ����������Ӻ��Ի�ò������õ����ӣ��򷵻� null
				return null;
			}
		}

		return conn;
	}

	/**
	 * ͨ������ getFreeConnection() ��������һ�����õ����ݿ����� �����ǰû�п��õ����ݿ����ӣ�
	 * ���Ҹ�������ݿ����Ӳ��ܴ����������ӳش�С�����ƣ����˺����ȴ�һ���ٳ��Ի�ȡ��
	 * 
	 * @return ����һ�����õ����ݿ����Ӷ���
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {

		// ȷ�����ӳؼ�������
		if (connections == null) {
			return null; // ���ӳػ�û�������򷵻� null
		}

		Connection conn = getFreeConnection();// ���һ�����õ����ݿ�����

		while (conn == null) {// ���Ŀǰû�п���ʹ�õ����ӣ������е����Ӷ���ʹ����
			wait(250);// ��һ������

			conn = getFreeConnection();
		}
		return conn;
	}
}
