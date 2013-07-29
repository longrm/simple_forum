package org.longrm.forum.dao;

import java.sql.*;
import java.util.*;

public class ConnectionPool {

	private String jdbcDriver = ""; // 数据库驱动
	private String dbUrl = ""; // Database URL
	private String username = "";
	private String password = "";

	private String testTable = ""; // 测试连接是否可用的测试表名，默认没有测试表

	private int initConnections = 10; // 连接池初始大小
	private int incrementalConnections = 5; // 连接池自动增加的大小
	private int maxConnections = 50; // 连接池连接数上限

	@SuppressWarnings("unchecked")
	private Vector connections = null; // 存放连接池中数据库连接的Vector , 初始时为 null

	/**
	 * 默认构造函数
	 */
	public ConnectionPool() {
	}

	/**
	 * 自定义构造函数
	 * 
	 * @param jdbcDriver
	 *            驱动
	 * @param dbUrl
	 *            数据库URL
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	public ConnectionPool(String jdbcDriver, String dbUrl, String username,
			String password) {
		this.jdbcDriver = jdbcDriver;
		this.dbUrl = dbUrl;
		this.username = username;
		this.password = password;
	}

	/**
	 * 获取测试数据库表的名字
	 * 
	 * @return
	 */
	public String getTestTable() {
		return testTable;
	}

	/**
	 * 设置测试数据库表的名字
	 * 
	 * @param testTable
	 */
	public void setTestTable(String testTable) {
		this.testTable = testTable;
	}

	/**
	 * 获取连接池的初始大小
	 * 
	 * @return 初始连接池中可获得的连接数量
	 */
	public int getInitConnections() {
		return initConnections;
	}

	/**
	 * 设置连接池的初始大小
	 * 
	 * @param initConnections
	 *            用于设置初始连接池中连接的数量
	 */
	public void setInitConnections(int initConnections) {
		this.initConnections = initConnections;
	}

	/**
	 * 获取连接池自动增加的大小
	 * 
	 * @return 连接池自动增加的大小
	 */
	public int getIncrementalConnections() {
		return incrementalConnections;
	}

	/**
	 * 设置连接池自动增加的大小
	 * 
	 * @param incrementalConnections
	 *            连接池自动增加的大小
	 */
	public void setIncrementalConnections(int incrementalConnections) {
		this.incrementalConnections = incrementalConnections;
	}

	/**
	 * 获取连接池中最大的可用连接数量
	 * 
	 * @return 连接池中最大的可用连接数量
	 */
	public int getMaxConnections() {
		return maxConnections;
	}

	/**
	 * 设置连接池中最大可用的连接数量
	 * 
	 * @param maxConnections
	 *            设置连接池中最大可用的连接数量值
	 */
	public void setMaxConnections(int maxConnections) {
		this.maxConnections = maxConnections;
	}

	/**
	 * 使程序等待给定的毫秒数
	 * 
	 * @param mSeconds
	 *            给定的毫秒数
	 */
	private void wait(int mSeconds) {
		try {
			Thread.sleep(mSeconds);
		} catch (InterruptedException e) {
		}
	}

	/**
	 * 刷新连接池中所有的连接对象
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public synchronized void refreshConnections() throws SQLException {
		// 确保连接池己创新存在
		if (connections == null) {
			System.out.println(" 连接池不存在，无法刷新 !");
			return;
		}

		PooledConnection pooledConn = null;
		Enumeration enu = connections.elements();
		while (enu.hasMoreElements()) {
			pooledConn = (PooledConnection) enu.nextElement();// 获得一个连接对象

			// 如果对象忙则等 5 秒 ,5 秒后直接刷新
			if (pooledConn.isBusy()) {
				wait(5000);
			}

			// 关闭此连接，用一个新的连接代替它。
			closeConnection(pooledConn.getConn());
			pooledConn.setConn(newConnection());
			pooledConn.setBusy(false);
		}
	}

	/**
	 * 关闭连接池中所有的连接，并清空连接池。
	 * 
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public synchronized void closeConnectionPool() throws SQLException {
		// 确保连接池存在，如果不存在，返回
		if (connections == null) {
			System.out.println(" 连接池不存在，无法关闭 !");
			return;
		}

		PooledConnection pooledConn = null;
		Enumeration enu = connections.elements();
		while (enu.hasMoreElements()) {
			pooledConn = (PooledConnection) enu.nextElement();// 获得一个连接对象

			// 如果对象忙则等 5 秒 ,5 秒后直接刷新
			if (pooledConn.isBusy()) {
				wait(5000);
			}

			// 5 秒后直接关闭它
			closeConnection(pooledConn.getConn());

			// 从连接池向量中删除它
			connections.removeElement(pooledConn);

			// 设置连接池为空
			connections = null;
		}
	}

	/**
	 * 关闭一个数据库连接
	 * 
	 * @param conn
	 *            需要关闭的数据库连接
	 */
	private void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println(" 关闭数据库连接出错： " + e.getMessage());
		}
	}

	/**
	 * 创建一个新的数据库连接并返回它
	 * 
	 * @return 返回一个新创建的数据库连接
	 * @throws SQLException
	 */
	private Connection newConnection() throws SQLException {
		Connection conn = DriverManager
				.getConnection(dbUrl, username, password);// 创建一个数据库连接

		// 如果这是第一次创建数据库连接，即检查数据库，获得此数据库允许支持的最大客户连接数目
		// connections.size()==0 表示目前没有连接己被创建

		if (connections.size() == 0) {
			DatabaseMetaData metadata = conn.getMetaData();

			// DatabaseMetaData接口是描述数据库元数据的。我们可以从中取得数据库的表、视图等信息。

			int driverMaxConnections = metadata.getMaxConnections();

			// 数据库返回的 driverMaxConnections 若为 0 ，表示此数据库没有最大
			// 连接限制，或数据库的最大连接限制不知道
			// driverMaxConnections 为返回的一个整数，表示此数据库允许客户连接的数目
			// 如果连接池中设置的最大连接数量大于数据库允许的连接数目 , 则置连接池的最大连接数目为数据库允许的最大数目

			if (driverMaxConnections > 0
					&& this.maxConnections > driverMaxConnections) {
				this.maxConnections = driverMaxConnections;
			}
		}

		return conn;
	}

	/**
	 * 创建由 numConnections 指定数目的数据库连接 , 并把这些连接 放入 connections 向量中
	 * 
	 * @param numConnections
	 *            要创建的数据库连接的数目
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private void createConnections(int numConnections) throws SQLException {
		for (int i = 0; i < numConnections; i++) { // 循环创建指定数目的数据库连接
			// 是否连接池中的数据库连接的数量己经达到最大？最大值由类成员 maxConnections指出
			// 如果 maxConnections 为 0 或负数，表示连接数量没有限制。
			// 如果连接数己经达到最大，即退出。
			if (this.maxConnections > 0
					&& this.connections.size() >= this.maxConnections) {
				break;
			}

			// add a new PooledConnection object to connections vector
			// 增加一个连接到连接池中（向量 connections 中）

			try {
				connections.addElement(new PooledConnection(newConnection()));
			} catch (SQLException e) {
				System.out.println(" 创建数据库连接失败！ " + e.getMessage());
				throw new SQLException();
			}

			System.out.println(" 数据库连接己创建 ");
		}
	}

	/**
	 * 创建一个数据库连接池，连接池中的可用连接的数量采用类成员initConnections 中设置的值
	 * 
	 * @throws Exception
	 * 
	 * Java synchronized 解析
	 * 
	 * 一、当两个并发线程访问同一个对象object中的这个synchronized(this)同步代码块时，
	 * 一个时间内只能有一个线程得到执行。另一个线程必须等待当前线程执行完这个代码块以后才能执行该代码块。
	 * 二、然而，当一个线程访问object的一个synchronized(this)同步代码块时，
	 * 另一个线程仍然可以访问该object中的非synchronized(this)同步代码块。
	 * 三、尤其关键的是，当一个线程访问object的一个synchronized(this)同步代码块时，
	 * 其他线程对object中所有其它synchronized(this)同步代码块的访问将被阻塞。
	 * 四、第三个例子同样适用其它同步代码块。也就是说，当一个线程访问object的一个synchronized(this)同步代码块时，
	 * 它就获得了这个object的对象锁。结果，其它线程对该object对象所有同步代码部分的访问都被暂时阻塞。 五、以上规则对其它对象锁同样适用
	 */
	@SuppressWarnings("unchecked")
	public synchronized void createPool() throws Exception {
		// 确保连接池没有创建
		// 如果连接池己经创建了，保存连接的Vector中 connections 不会为空

		if (connections != null) {
			return; // 如果已经创建，则返回
		}

		Driver driver = (Driver) (Class.forName(this.jdbcDriver)).newInstance();
		// 实例化 JDBC Driver 中指定的驱动类实例

		DriverManager.registerDriver(driver);// 注册jdbc驱动

		connections = new Vector();// 创建保存连接的向量 , 初始时有 0 个元素
		// 根据 initialConnections 中设置的值，创建连接。
		createConnections(this.initConnections);
		System.out.println("数据库连接池创建成功!");
	}

	/**
	 * 此函数返回一个数据库连接到连接池中，并把此连接置为空闲 所有使用连接池获得的数据库连接均应在不使用此连接时返回它
	 * 
	 * @param conn
	 *            需返回到连接池中的连接对象
	 */
	@SuppressWarnings("unchecked")
	public void returnConnection(Connection conn) {
		// 确保连接池存在，如果连接没有创建（不存在），直接返回
		if (connections == null) {
			System.out.println("连接池不存在，无法返回此连接到连接池中 !");
			return;
		}
		PooledConnection pooledConn = null;
		Enumeration enmu = connections.elements();// 遍历连接池中的所有连接，找到这个要返回的连接对象
		while (enmu.hasMoreElements()) {
			pooledConn = (PooledConnection) enmu.nextElement();
			// 先找到连接池中的要返回的连接对象

			if (conn == pooledConn.getConn()) {// 找到了 , 设置此连接为空闲状态
				pooledConn.setBusy(false);
				break;
			}
		}
	}

	/**
	 * 测试一个连接是否可用，如果不可用，关掉它并返回 false 否则可用返回 true
	 * 
	 * @param conn
	 *            需要测试的数据库连接
	 * @return 返回 true 表示此连接可用， false 表示不可用
	 */
	private boolean testConnection(Connection conn) {
		try {
			// 判断测试表是否存在
			if (testTable.equals("")) {
				// 如果测试表为空，试着使用此连接的 setAutoCommit() 方法
				// 来判断连接否可用（此方法只在部分数据库可用，如果不可用 ,
				// 抛出异常）。注意：使用测试表的方法更可靠

				conn.setAutoCommit(true);
			} else {// 有测试表的时候使用测试表测试
				Statement sta = conn.createStatement();
				sta.execute("select count(*) from " + testTable);
			}
		} catch (SQLException e) {
			// 上面抛出异常，此连接己不可用，关闭它，并返回 false;
			closeConnection(conn);
			return false;
		}
		// 连接可用，返回 true
		return true;
	}

	/**
	 * 查找连接池中所有的连接，查找一个可用的数据库连接 如果没有可用的连接，返回 null
	 * 
	 * @return 返回一个可用的数据库连接
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private Connection findFreeConnection() throws SQLException {
		Connection conn = null;
		PooledConnection pooledConn = null;

		// 获得连接池向量中所有的对象
		Enumeration enu = connections.elements();
		// 遍历所有的对象，看是否有可用的连接
		while (enu.hasMoreElements()) {
			pooledConn = (PooledConnection) enu.nextElement();
			if (!pooledConn.isBusy()) {
				// 如果此对象不忙，则获得它的数据库连接并把它设为忙
				conn = pooledConn.getConn();
				pooledConn.setBusy(true);

				// 测试此连接是否可用
				if (!testConnection(conn)) {
					// 如果此连接不可再用了，则创建一个新的连接，
					// 并替换此不可用的连接对象，如果创建失败，返回 null
					try {
						conn = newConnection();
					} catch (SQLException e) {
						System.out.println(" 创建数据库连接失败！ " + e.getMessage());
						return null;
					}
					pooledConn.setConn(conn);
				}
				break;// 己经找到一个可用的连接，退出
			}
		}
		return conn;// 返回找到到的可用连接
	}

	/**
	 * 本函数从连接池向量 connections 中返回一个可用的的数据库连接，
	 * 如果当前没有可用的数据库连接，本函数则根据incrementalConnections设置的值创建几个数据库连接，
	 * 并放入连接池中。 如果创建后，所有的连接仍都在使用中，则返回 null
	 * 
	 * @return 返回一个可用的数据库连接
	 * @throws SQLException
	 */
	private Connection getFreeConnection() throws SQLException {

		// 从连接池中获得一个可用的数据库连接
		Connection conn = findFreeConnection();

		if (conn == null) {
			// 如果目前连接池中没有可用的连接
			// 创建一些连接
			createConnections(incrementalConnections);

			// 重新从池中查找是否有可用连接
			conn = findFreeConnection();
			if (conn == null) {
				// 如果创建连接后仍获得不到可用的连接，则返回 null
				return null;
			}
		}

		return conn;
	}

	/**
	 * 通过调用 getFreeConnection() 函数返回一个可用的数据库连接 如果当前没有可用的数据库连接，
	 * 并且更多的数据库连接不能创建（如连接池大小的限制），此函数等待一会再尝试获取。
	 * 
	 * @return 返回一个可用的数据库连接对象
	 * @throws SQLException
	 */
	public synchronized Connection getConnection() throws SQLException {

		// 确保连接池己被创建
		if (connections == null) {
			return null; // 连接池还没创建，则返回 null
		}

		Connection conn = getFreeConnection();// 获得一个可用的数据库连接

		while (conn == null) {// 如果目前没有可以使用的连接，即所有的连接都在使用中
			wait(250);// 等一会再试

			conn = getFreeConnection();
		}
		return conn;
	}
}
