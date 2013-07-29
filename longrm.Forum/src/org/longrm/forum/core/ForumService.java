package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;

public class ForumService {
	
	private static ArrayList<Forum> forumList;
	
	// ��̳���а���б�
	public static ArrayList<Forum> getForumList() {
		if(forumList==null)
			forumList = requestForumList(null);
		return forumList;
	}
	
	/**
	 * ��ȡ�����Ϣ
	 * @param forum_id
	 * @return
	 */
	public static Forum requestForum(String forum_id) {
		if(forum_id==null || forum_id.equalsIgnoreCase(""))
			return null;
		
		Forum forum = new Forum();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from v_forum_master where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, forum_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				BeanUtils.populateForum(forum, rs);
				forum.setMaster(rs.getString("master"));
			}
			else
				return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return forum;
	}
	
	/**
	 * ��ȡForums����б���Ϣ
	 * @param user
	 * @param forum_id
	 * @return
	 */
	public static ArrayList<Forum> requestForumList(String forum_id) {
		// ���Ӱ�������ѭ������ȡ�������Ӱ��
		String sql = "select * from v_forum_master";
		if(forum_id==null)
			sql += " start with level_index='1'";
		else
			sql += " start with id='" + forum_id + "'"; 
		sql += " connect by prior id = up_forum_id order by level_index, up_forum_id, rec_no";
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ResultSet tmprs = null;
		String tmpsql;
		ArrayList<Forum> forumList = new ArrayList<Forum>();
		try {
			conn = DAOFactory.getInstance().getConnection();
			stmt = conn.createStatement();
			
			// ȡ���Ӱ������������������Լ�������󷢱�ʱ��
			HashMap<String, Forum> forumMap = new HashMap<String, Forum>();
			tmpsql = "select * from v_forum_sum_topics";
			tmprs = stmt.executeQuery(tmpsql);
			while(tmprs.next()) {
				String key = tmprs.getString("forum_id");
				Forum tmpForum = new Forum();
				tmpForum.setTopics(tmprs.getInt("topics"));
				tmpForum.setNotes(tmprs.getInt("topics") + tmprs.getInt("re_topics"));
				tmpForum.setLast_time(tmprs.getTimestamp("last_time"));
				forumMap.put(key, tmpForum);
			}
			
			// ȡ�������Ϣ
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Forum forum = new Forum();
				BeanUtils.populateForum(forum, rs);
				forum.setMaster(rs.getString("master"));

				ArrayList<Forum> subForumList = new ArrayList<Forum>();
				forum.setSubForumList(subForumList);
				
				String up_forum_id = rs.getString("up_forum_id");
				// ������up_forum_id=null������forumList
				if(up_forum_id==null) {
					forumList.add(forum);
				}
				
				// ���������ϼ������Ӱ���б�subForumList
				else {
					Forum tmpForum = forumMap.get(forum.getId());
					if(tmpForum==null) {
						forum.setTopics(0);
						forum.setNotes(0);
						forum.setAuthority(rs.getInt("authority"));
					}
					else {
						Timestamp last_time = tmpForum.getLast_time();
						forum.setTopics(tmpForum.getTopics());
						forum.setNotes(tmpForum.getNotes());
						forum.setLast_time(last_time);
						forum.setAuthority(rs.getInt("authority"));
						
						// ȡ����󷢱���Ϣ
						tmpsql = "select * from topic where forum_id=? and last_time=?";
						pstmt = conn.prepareStatement(tmpsql);
						pstmt.setString(1, forum.getId());
						pstmt.setTimestamp(2, last_time);
						tmprs = pstmt.executeQuery();
						
						tmprs.next();
						forum.setLast_replier(tmprs.getString("last_replier"));
						forum.setLast_topic_id(tmprs.getString("id"));
						String title = tmprs.getString("title");
						if(last_time.equals(tmprs.getTimestamp("post_time")))
							forum.setLast_title(title);
						else
							forum.setLast_title("Re: " + title);	
					}					
					for(int i=0; i<forumList.size(); i++) {
						if(forumList.get(i).getId().equals(up_forum_id)) {
							ArrayList<Forum> subList = forumList.get(i).getSubForumList();
							subList.add(forum);
						}
					}
				}
			}
		}
		catch (SQLException e) {
			System.out.println("\n"+sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(null, pstmt, tmprs);
			DBTool.closeConnection(conn, stmt, rs);
		}
		
		return forumList;
	}
	
	/**
	 * ȡ���Ӱ��ӳ����Ϣ
	 * @return
	 */
	public static Map<String, Forum> requestForumMap() {
		Map<String, Forum> map = new HashMap<String, Forum>();
		
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from forum order by level_index, up_forum_id, rec_no";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Forum forum = new Forum();
				BeanUtils.populateForum(forum, rs);
				map.put(forum.getId(), forum);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(null, pstmt, rs);
		}
			
		return map;
	}
	
}
