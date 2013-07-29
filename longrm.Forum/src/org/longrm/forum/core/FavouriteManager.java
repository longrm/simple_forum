package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;

public class FavouriteManager {

	/**
	 * 添加收藏
	 * @param me
	 * @param topic_id
	 * @return
	 */
	public static boolean addFavourite(String me, String topic_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 提交回帖
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "insert into favourite values(?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, me);
			pstmt.setString(2, topic_id);
			pstmt.execute();
		}
		catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}
		return true;
	}
	
	/**
	 * 删除收藏
	 * @param me
	 * @param topic_ids
	 * @return
	 */
	public static boolean deleteFavourite(String me, String[] topic_ids) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		// 提交回帖
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "delete favourite where me=? and topic_id=?";
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				pstmt.setString(1, me);
				pstmt.setString(2, topic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch(SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}
		return true;
	}
}
