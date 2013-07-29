package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.longrm.forum.bean.Message;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;

public class MessageService {

	/**
	 * 获取收件箱列表
	 * @param field
	 * @param username
	 * @return
	 */
	public static ArrayList<Message> requestReceiveMessageList(String field, String username) {
		ArrayList<Message> messageList = new ArrayList<Message>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from message_receivebox where " + field + "=? order by time desc";
		try {
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Message message = new Message();
				BeanUtils.populateMessage(message, rs);
				message.setIsread(rs.getString("isread"));
				messageList.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return messageList;
	}
	
	/**
	 * 获取发件箱
	 * @param field
	 * @param username
	 * @return
	 */
	public static ArrayList<Message> requestSendMessageList(String field, String username) {
		ArrayList<Message> messageList = new ArrayList<Message>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from message_sendbox where " + field + "=? order by time desc";
		try {
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Message message = new Message();
				BeanUtils.populateMessage(message, rs);
				messageList.add(message);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return messageList;
	}
	
	/**
	 * 获取指定短信息
	 * @param mid
	 * @return
	 */
	public static Message requestMessage(String mid, String table) {
		Message message = new Message();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from " + table + " where id=?";
		try {
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mid);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				BeanUtils.populateMessage(message, rs);
				if(table.equals("message_receivebox"))
					message.setIsread(rs.getString("isread"));
			}
			else
				return null;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return message;
	}
	
	/**
	 * 检查是否有新短信
	 * @param username
	 * @return
	 */
	public static boolean hasNewMessage(String username) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select count(*) from message_receivebox where receiver=? and isread=?";
		try {
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, username);
			pstmt.setString(2, "否");
			rs = pstmt.executeQuery();
			rs.next();
			if(rs.getInt(1)>0)
				result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return result;
	}

}
