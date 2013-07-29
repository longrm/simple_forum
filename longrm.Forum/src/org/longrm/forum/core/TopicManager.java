package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.User;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.Constant;

public class TopicManager {

	/**
	 * �ö�����
	 * @param forum
	 * @param master
	 * @param topic_ids
	 * @param top
	 * @return
	 */
	public static boolean topTopic(Forum forum, User master, String[] topic_ids, int top) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "";
			conn.setAutoCommit(false);
			
			// �����ö�
			sql = "update topic set top=? where id=?";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				pstmt.setInt(1, top);
				pstmt.setString(2, topic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			// ��¼������־
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
					"values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, "top");
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}
	
	/**
	 * ��������
	 * @param forum
	 * @param master
	 * @param topic_ids
	 * @param soul
	 * @return
	 */
	public static boolean soulTopic(Forum forum, User master, String[] topic_ids, int soul) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "";
			conn.setAutoCommit(false);
			
			// ���»���
			sql = "update user_info set mk1=mk1+?, mk2=mk2+?, mk3=mk3+?, " +
					"souls=souls+? where id=?";
			pstmt = conn.prepareStatement(sql);
			stmt = conn.createStatement();
			for(int i=0; i<topic_ids.length; i++) {
				String tmpsql = "Select t.soul, u.Id user_id From topic t, " +
						"fm_user u Where t.poster = u.Name and t.id=" + topic_ids[i];
				rs = stmt.executeQuery(tmpsql);
				if(rs.next()) {
					int dec = soul-rs.getInt("soul");
					if(dec==0)
						continue;
					int souls=0;
					if(rs.getInt("soul")>0 && soul==0)
						souls=-1;
					else if(rs.getInt("soul")==0 && soul>0)
						souls=1;
					pstmt.setInt(1, dec*Constant.SOUL_MK1);
					pstmt.setInt(2, dec*Constant.SOUL_MK2);
					pstmt.setInt(3, dec*Constant.SOUL_MK3);
					pstmt.setInt(4, souls);
					pstmt.setString(5, rs.getString("user_id"));
					pstmt.addBatch();
				}
			}
			pstmt.executeBatch();
			
			// ���þ���
			sql = "update topic set soul=? where id=?";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				pstmt.setInt(1, soul);
				pstmt.setString(2, topic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			// ��¼������־
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
					"values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, "soul");
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
			DBTool.closeConnection(null, stmt, null);
		}
	}

	public static boolean colorTopic(Forum forum, User master, 
			String[] topic_ids, String color, String bold) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "";
			conn.setAutoCommit(false);
			
			// �ö�����
			sql = "update topic set color=?, bold=? where id=?";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				pstmt.setString(1, color);
				pstmt.setString(2, bold);
				pstmt.setString(3, topic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			// ��¼������־
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
					"values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, "color");
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

	// ������
	public static boolean openTopic(Forum forum, User master, String[] topic_ids) {
		return changeStatus(forum, master, topic_ids, "0");
	}

	// ��������
	public static boolean lockTopic(Forum forum, User master, String[] topic_ids) {
		return changeStatus(forum, master, topic_ids, "-1");
	}

	// �ر�����
	public static boolean closeTopic(Forum forum, User master, String[] topic_ids) {
		return changeStatus(forum, master, topic_ids, "-2");
	}

	// ��ǰ
	public static boolean pushTopic(Forum forum, User master, String[] topic_ids) {
		return pushdownTopic(forum, master, topic_ids, "push");
	}

	// ѹ��
	public static boolean downTopic(Forum forum, User master, String[] topic_ids) {
		return pushdownTopic(forum, master, topic_ids, "down");
	}

	/**
	 * �ƶ����Ӳ���
	 * @param forum
	 * @param master
	 * @param topic_ids
	 * @param moveForumId
	 * @return
	 */
	public static boolean moveTopic(Forum forum, User master, String[] topic_ids, String moveForumId) {
		String sql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			// ��ȡ����id
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			for(int i=0; i<topic_ids.length; i++) {
				// �ƶ�����
				sql = "update topic set forum_id=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, moveForumId);
				pstmt.setString(2, topic_ids[i]);
				pstmt.execute();
				// �ƶ�����
				sql = "update re_topic set forum_id=? where topic_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, moveForumId);
				pstmt.setString(2, topic_ids[i]);
				pstmt.execute();
				// ��������¼д���
				sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
						"values(?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				// ������һ��id
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, "move");
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.execute();
			}
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}
	}

	/**
	 * ɾ�����Ӳ���
	 * @param forum
	 * @param master
	 * @param topic_ids
	 * @return
	 */
	public static boolean deleteTopic(Forum forum, User master, String[] topic_ids) {
		String sql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			conn.setAutoCommit(false);
			// ��ȡ����id
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			for(int i=0; i<topic_ids.length; i++) {
				// ɾ����������
				sql = "delete topic_file where topic_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, topic_ids[i]);
				pstmt.execute();
				// ɾ����������
				sql = "select * from re_topic where topic_id='" + topic_ids[i] + "'";
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					sql = "delete re_topic_file where re_topic_id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, rs.getString("id"));
					pstmt.execute();
				}
				// ɾ������
				sql = "delete topic where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, topic_ids[i]);
				pstmt.execute();
				// ɾ������
				sql = "delete re_topic where topic_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, topic_ids[i]);
				pstmt.execute();
				// ��������¼д���
				sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
						"values(?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				// ������һ��id
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, "delete");
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.execute();
			}
			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
			DBTool.closeConnection(null, stmt, null);
		}
	}
	
	/**
	 * �ı�����״̬: 0������-1������-2�ر�
	 * @param forum
	 * @param master
	 * @param topic_ids
	 * @return
	 */
	private static boolean changeStatus(Forum forum, User master, String[] topic_ids, String status) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "";
			conn.setAutoCommit(false);
			
			String oper = "open";
			if(status.equals("0"))
				oper = "open";
			else if(status.equals("-1"))
				oper = "lock";
			else if(status.equals("-2"))
				oper = "close";
			// �ı�״̬
			sql = "update topic set status=? where id=?";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				pstmt.setString(1, status);
				pstmt.setString(2, topic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			// ��¼������־
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
					"values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, oper);
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}
	
	/**
	 * �ı����ʱ��
	 * @param forum
	 * @param master
	 * @param topic_ids
	 * @param oper
	 * @return
	 */
	private static boolean pushdownTopic(Forum forum, User master, String[] topic_ids, String oper) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "";
			conn.setAutoCommit(false);
			
			// �޸����ʱ��
			Timestamp time = null;
			if(oper.equals("push"))
				time = new Timestamp(System.currentTimeMillis());
			else if(oper.equals("down"))
				time = new Timestamp(0);
			sql = "update topic set last_time=? where id=?";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				pstmt.setTimestamp(1, time);
				pstmt.setString(2, topic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			// ��¼������־
			String manage_id = DBTool.getMaxId("manage_topic_log", "id");
			sql = "insert into manage_topic_log(id, manager, topic_id, forum_id, type, time) " +
					"values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<topic_ids.length; i++) {
				manage_id = DBTool.produceNextId(manage_id);
				pstmt.setString(1, manage_id);
				pstmt.setString(2, master.getName());
				pstmt.setString(3, topic_ids[i]);
				pstmt.setString(4, forum.getId());
				pstmt.setString(5, oper);
				pstmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				pstmt.addBatch();
			}
			pstmt.executeBatch();

			conn.commit();
			conn.setAutoCommit(true);
			return true;
		}
		catch(SQLException e) {
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
