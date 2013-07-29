package org.longrm.forum.dao;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.longrm.forum.util.BeanUtils;

public class DBTool {

	/**
	 * �ر�conn, stmt, rs
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void closeConnection(Connection conn, Statement stmt,
			ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ӱ�table���ȡ��һ��idֵ
	 * 
	 * @param table
	 * @param id
	 * @return
	 */
	public static String getNextId(String table, String id) {
		String maxId = getMaxId(table, id);
		return produceNextId(maxId);
	}

	/**
	 * �ӱ�table���ȡ��һ��ָ������Ϊlength��idֵ
	 * 
	 * @param table
	 * @param id
	 * @param length
	 * @param c
	 * @return
	 */
	public static String getNextId(String table, String id, int length, char c) {
		String maxId = getMaxId(table, id);
		return produceNextId(maxId, length, c);
	}

	/**
	 * �ӱ�table��ȡ���idֵ
	 * 
	 * @param table
	 * @param id
	 * @return
	 */
	public static String getMaxId(String table, String id) {
		String maxId = "";
		String sql = "select max(to_number(" + id + ")) from " + table;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next())
				maxId = rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection(conn, stmt, rs);
		}
		return maxId;
	}

	/**
	 * ������һ��idֵ
	 * 
	 * @param id
	 * @return
	 */
	public static String produceNextId(String id) {
		if (id == null || id.equalsIgnoreCase(""))
			return "1";
		BigInteger next = new BigInteger(id).add(new BigInteger("1"));
		return String.valueOf(next);
	}

	/**
	 * ������һ������Ϊlength��idֵ
	 * 
	 * @param id
	 * @param length
	 * @param c
	 * @return
	 */
	public static String produceNextId(String id, int length, char c) {
		BigInteger next;
		if (id == null || id.equalsIgnoreCase(""))
			next = new BigInteger("1");
		else
			next = new BigInteger(id).add(new BigInteger("1"));
		// ��length��ʽ��nextId��ǰ��ȱ�ٵ�λ����c���
		String zero = "";
		String nextId = String.valueOf(next);
		for (int i = nextId.length(); i < length; i++) {
			zero += c;
		}
		nextId = zero + nextId;
		return nextId;
	}

	/**
	 * ��ȡsql��params
	 * ������ƣ�Ч�ʹ��ͣ����ã�
	 * @param bean
	 * @param table
	 * @return
	 */
	public static Map getInsertSqlParams(String table, Object bean) {
		String sql = "insert into " + table + " (";
		String values = "";
		// params�ǲ���Ĳ���ֵ������PreparedStatement.set...
		ArrayList params = new ArrayList();
		Map map = new HashMap();

		// ȡ��bean��������ֶ�
		Class beanClass = bean.getClass();
		Field[] fields = beanClass.getDeclaredFields();

		// ��map���ֵ����bean
		try {
			for (int i = 0; i < fields.length; i++) {
				String fieldname = fields[i].getName();
				if (i == 0)
					sql += fieldname;
				else
					sql += ", " + fieldname;

				// ����get������ȡ����ֵ
				String methodname = BeanUtils.getMethodName(fieldname, "get");
				Method method = beanClass.getMethod(methodname);
				Object fieldvalue = method.invoke(bean);
				if (i == 0)
					values += "?";
				else
					values += ", ?";
				params.add(fieldvalue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sql += ") values(" + values + ")";

		map.put("sql", sql);
		map.put("params", params);
		return map;
	}

	/**
	 * ���ò��������ڱ������в���������
	 * 
	 * @param pstmt
	 * @param params
	 * @throws SQLException
	 */
	public static void setStatementParameters(PreparedStatement pstmt,
			Object[] params) throws SQLException {
		for (int i = 0; i < params.length; ++i) {
			Object obj = params[i];
			if (null == obj) {
				pstmt.setNull(i + 1, Types.CHAR);
			} else if (obj instanceof java.sql.Date) {
				pstmt.setDate(i + 1, (java.sql.Date) obj);
			} else if (obj instanceof java.sql.Timestamp) {
				pstmt.setTimestamp(i + 1, (java.sql.Timestamp) obj);
			} else {
				pstmt.setObject(i + 1, obj);
			}
		}
	}
	
	/**
	 * ���¼�¼
	 * @param sql
	 * @param params
	 * @throws SQLException
	 */
	public static void executeUpdate(String sql, Object[] params) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			DBTool.setStatementParameters(pstmt, params);
			pstmt.executeUpdate();
		}
		catch (SQLException e) {
			System.out.println(sql);
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
