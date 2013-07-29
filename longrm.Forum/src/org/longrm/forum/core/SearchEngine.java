package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.longrm.forum.bean.Topic;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;
import org.longrm.forum.util.Constant;

public class SearchEngine {
	
	/**
	 * ȡ��Ҫ���ҵ��������ڵİ��: 'id1', 'id2', ...
	 * @param forum_id
	 * @return
	 */
	public static String getSearchForums(String forum_id) {
		String forum_ids = "";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			
			sql = "select * from forum";
			if(forum_id==null)
				sql += " start with level_index='1'";
			else
				sql += " start with id='" + forum_id + "'"; 
			sql += " connect by prior id = up_forum_id order by level_index, up_forum_id, rec_no";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				forum_ids += "'" + rs.getString("id") + "', ";
			}
			
			if(!forum_ids.equals(""))
				forum_ids = forum_ids.substring(0, forum_ids.length()-2);
		}
		catch(SQLException e) {
			System.out.println(sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
		}
		return forum_ids;
	}
	
	public static int searchTopicCount(String forum_ids, boolean soul, String dateStart, String dateEnd) {
		return searchTopicCount(forum_ids, null, null, soul, dateStart, dateEnd);
	}

	/**
	 * �����������������ڷ�ҳ
	 * @param forum_ids
	 * @param poster
	 * @param title
	 * @param soul
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public static int searchTopicCount(String forum_ids, String poster, String title, boolean soul,
			String dateStart, String dateEnd) {
		int count=0;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			stmt = conn.createStatement();

			// ��������ƴsql���
			sql = "select count(*) from topic where 1=1";
			
			// �������ڰ��
			if(!forum_ids.equals(""))
				sql += " and forum_id in(" + forum_ids + ")";
			// ����
			if(poster!=null && !poster.equalsIgnoreCase(""))
				sql += " and poster like '" + poster + "'";
			// ����
			if(title!=null && !title.equalsIgnoreCase(""))
				sql += " and title like '%" + title + "%'";
			// ������
			if(soul==true)
				sql += " and soul!=0";
			// ����ʱ��
			if(dateStart!=null && !dateStart.equalsIgnoreCase(""))
				sql += " and post_time>=to_date('" + dateStart + "', 'yyyy-MM-dd')";
			if(dateEnd!=null && !dateEnd.equalsIgnoreCase(""))
				sql += " and post_time<=to_date('" + dateEnd + "', 'yyyy-MM-dd')";
			
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
		}
		catch(SQLException e) {
			System.out.println(sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
		}
		return count;
	}
	
	public static ArrayList<Topic> searchTopicList(String forum_ids, boolean soul, 
			String dateStart, String dateEnd, int pageIndex) {
		return searchTopicList(forum_ids, null, null, soul, dateStart, dateEnd, null, null, pageIndex);
	}

	/**
	 * ��������
	 * @param forum_ids
	 * @param poster
	 * @param title
	 * @param soul
	 * @param dateStart
	 * @param dateEnd
	 * @param orderBy
	 * @param order
	 * @param pageIndex
	 * @return
	 */
	public static ArrayList<Topic> searchTopicList(String forum_ids, String poster, String title, boolean soul,
			String dateStart, String dateEnd, String orderBy, String order, int pageIndex) {
		// ����arraylist
		ArrayList<Topic> topicList = new ArrayList<Topic>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			stmt = conn.createStatement();

			// ��������ƴsql���
			sql = "select * from (select rownum rn, r.* from (select topic.* from topic where 1=1";
			
			// �������ڰ��
			if(!forum_ids.equals(""))
				sql += " and forum_id in(" + forum_ids + ")";
			// ����
			if(poster!=null && !poster.equalsIgnoreCase(""))
				sql += " and poster like '" + poster + "'";
			// ����
			if(title!=null && !title.equalsIgnoreCase(""))
				sql += " and title like '%" + title + "%'";
			// ������
			if(soul==true)
				sql += " and soul!=0";
			// ����ʱ��
			if(dateStart!=null && !dateStart.equalsIgnoreCase(""))
				sql += " and post_time>=to_date('" + dateStart + "', 'yyyy-MM-dd')";
			if(dateEnd!=null && !dateEnd.equalsIgnoreCase(""))
				sql += " and post_time<=to_date('" + dateEnd + "', 'yyyy-MM-dd')";
			// ����
			if(orderBy==null || orderBy.equalsIgnoreCase(""))
				orderBy = "last_time";
			if(order==null || order.equalsIgnoreCase(""))
				order = "desc";
			sql += " order by top desc, " + orderBy + " " + order + ") r)";
			
			// ��ҳ
			if(pageIndex!=-1)
				sql += " where rn between " + ((pageIndex - 1) * Constant.FORUM_PAGESIZE + 1) +
						" and " + pageIndex * Constant.FORUM_PAGESIZE;
			
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Topic topic = new Topic();
				BeanUtils.populateTopic(topic, rs);
				topic.setAuthority(rs.getInt("authority"));
				topic.setRe_topics(rs.getInt("re_topics"));
				topic.setClick(rs.getInt("click"));
				topic.setBold(rs.getString("bold"));
				topic.setColor(rs.getString("color"));
				topic.setKind(rs.getString("kind"));
				topic.setTop(rs.getInt("top"));
				topic.setSoul(rs.getInt("soul"));
				topic.setLast_replier(rs.getString("last_replier"));
				topic.setLast_time(rs.getTimestamp("last_time"));
				topic.setStatus(rs.getInt("status"));
				topicList.add(topic);
			}
		}
		catch(SQLException e) {
			System.out.println(sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
		}
		return topicList;
	}
	
}
