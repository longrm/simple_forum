package org.longrm.forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Test {

	public static void main(String[] args) throws Exception {
		String sql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		conn = DAOFactory.getInstance().getConnection();
		conn.setAutoCommit(false);
		// ��ȡ����id
		String manage_id = DBTool.getMaxId("manage_topic_log", "id");
		for (int i = 0; i < 1; i++) {
			// ɾ������
			sql = "delete topic where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "10");
			pstmt.execute();
			// ɾ����������
			sql = "delete topic_file where topic_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "10");
			pstmt.execute();
			// ɾ������
			sql = "delete re_topic where topic_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "10");
			pstmt.execute();
		}
		pstmt.execute();
		conn.commit();
		DBTool.closeConnection(conn, pstmt, rs);
	}
}
