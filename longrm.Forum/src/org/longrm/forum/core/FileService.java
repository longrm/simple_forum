package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.longrm.forum.bean.RetopicFile;
import org.longrm.forum.bean.TopicFile;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;

public class FileService {

	/**
	 * 取出主贴附件
	 * @param topic_id
	 * @return
	 */
	public static ArrayList<TopicFile> requestTopicFile(String topic_id) {
		if(topic_id==null)
			return null;
		
		ArrayList<TopicFile> topicFileList = new ArrayList<TopicFile>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DAOFactory.getInstance().getConnection();
			sql = "select * from topic_file where topic_id=? order by to_number(id)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, topic_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				TopicFile tf = new TopicFile();
				BeanUtils.populateFile(tf, rs);
				tf.setTopic_id(topic_id);
				topicFileList.add(tf);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return topicFileList;
	}
	
	/**
	 * 取出回贴附件
	 * @param retopic_id
	 * @return
	 */
	public static ArrayList<RetopicFile> requestRetopicFile(String retopic_id) {
		if(retopic_id==null)
			return null;
		
		ArrayList<RetopicFile> retopicFileList = new ArrayList<RetopicFile>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			conn = DAOFactory.getInstance().getConnection();
			sql = "select * from re_topic_file where re_topic_id=? order by to_number(id)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, retopic_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				RetopicFile rtf = new RetopicFile();
				BeanUtils.populateFile(rtf, rs);
				rtf.setRetopic_id(retopic_id);
				retopicFileList.add(rtf);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return retopicFileList;
	}

}
