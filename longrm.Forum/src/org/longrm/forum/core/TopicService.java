package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.longrm.forum.bean.Retopic;
import org.longrm.forum.bean.Topic;
import org.longrm.forum.bean.User;
import org.longrm.forum.bean.UserInfo;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;
import org.longrm.forum.util.Constant;

public class TopicService {

	/**
	 * 获取主贴信息
	 * @param topic_id
	 * @return
	 */
	public static Topic requestTopic(String topic_id) {
		if(topic_id==null)
			return null;
		
		Topic topic = new Topic();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DAOFactory.getInstance().getConnection();
			sql = "select * from v_topic_author where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, topic_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				BeanUtils.populateTopic(topic, rs);
				topic.setAuthority(rs.getInt("authority"));
				topic.setKind(rs.getString("kind"));
				topic.setStatus(rs.getInt("topic_status"));
				topic.setSoul(rs.getInt("soul"));
				topic.setTop(rs.getInt("top"));
				topic.setRe_topics(rs.getInt("re_topics"));
				topic.setClick(rs.getInt("click"));
				
				User author = new User();
				BeanUtils.populateUser(author, rs);
				author.setId(rs.getString("user_id"));
				topic.setAuthor(author);
				
				UserInfo author_info = new UserInfo();
				BeanUtils.populateUserInfo(author_info, rs);
				topic.setAuthor_info(author_info);
			}
			else
				return null;
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return topic;
	}

	/**
	 * 获取回贴信息
	 * @param topic_id
	 * @return
	 */
	public static Retopic requestRetopic(String retopic_id) {
		if(retopic_id==null)
			return null;
		
		Retopic retopic = new Retopic();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DAOFactory.getInstance().getConnection();
			sql = "select * from v_re_topic_author where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, retopic_id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				BeanUtils.populateTopic(retopic, rs);
				
				User author = new User();
				BeanUtils.populateUser(author, rs);
				author.setId(rs.getString("user_id"));
				retopic.setAuthor(author);

				UserInfo author_info = new UserInfo();
				BeanUtils.populateUserInfo(author_info, rs);
				retopic.setAuthor_info(author_info);
			}
			else
				return null;
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return retopic;
	}

	/**
	 * 获取回帖列表
	 * @param topic_id
	 * @return
	 */
	public static ArrayList<Retopic> requestRetopicList(String topic_id, int pageIndex) {
		ArrayList<Retopic> retopicList = new ArrayList<Retopic>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DAOFactory.getInstance().getConnection();
			sql = "select * from (select rownum rn, r.* from (" +
					"select * from v_re_topic_author where topic_id=? order by post_time" +
					") r)";
			// 分页
			if(pageIndex!=-1)
				sql += " where rn between " + (pageIndex-1)*Constant.TOPIC_PAGESIZE +
						" and " + (pageIndex*Constant.TOPIC_PAGESIZE-1);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, topic_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Retopic retopic = new Retopic();
				BeanUtils.populateTopic(retopic, rs);
				retopic.setTopic_id(topic_id);
				
				User author = new User();
				BeanUtils.populateUser(author, rs);
				author.setId(rs.getString("user_id"));
				retopic.setAuthor(author);
				
				UserInfo author_info = new UserInfo();
				BeanUtils.populateUserInfo(author_info, rs);
				retopic.setAuthor_info(author_info);

				retopicList.add(retopic);
			}
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return retopicList;
	}

}
