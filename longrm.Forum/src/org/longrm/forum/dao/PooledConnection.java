package org.longrm.forum.dao;

import java.sql.Connection;

public class PooledConnection {

	private Connection conn = null;;// ���ݿ�����

	boolean busy = false;// �������Ƿ�����ʹ�õı�־��Ĭ��û������ʹ��

	// ���캯��������һ�� Connection ����һ�� PooledConnection ����

	public PooledConnection(Connection connection) {

		this.conn = connection;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	// ��ö��������Ƿ�æ
	public boolean isBusy() {
		return busy;
	}

	// ���ö������������æ
	public void setBusy(boolean busy) {
		this.busy = busy;
	}

}