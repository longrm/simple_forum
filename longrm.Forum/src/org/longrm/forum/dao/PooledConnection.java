package org.longrm.forum.dao;

import java.sql.Connection;

public class PooledConnection {

	private Connection conn = null;;// 数据库连接

	boolean busy = false;// 此连接是否正在使用的标志，默认没有正在使用

	// 构造函数，根据一个 Connection 构告一个 PooledConnection 对象

	public PooledConnection(Connection connection) {

		this.conn = connection;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	// 获得对象连接是否忙
	public boolean isBusy() {
		return busy;
	}

	// 设置对象的连接正在忙
	public void setBusy(boolean busy) {
		this.busy = busy;
	}

}