package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.longrm.forum.bean.Friend;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;

public class FriendService {

	/**
	 * 获取好友列表
	 * @param me
	 * @return
	 */
	public static ArrayList<Friend> requestFriendList(String me) {
		ArrayList<Friend> friendList = new ArrayList<Friend>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from friend where me=? order by time";
		try {
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, me);
			rs = pstmt.executeQuery();
			while(rs.next()){
				Friend friend = new Friend();
				BeanUtils.populateFriend(friend, rs);
				friendList.add(friend);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return friendList;
	}
	
	/**
	 * 在数据库里添加friend
	 * @param friendList
	 * @return boolean
	 */
	public static boolean addFriendList(ArrayList<Friend> friendList) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into friend values(?, ?, ?, ?)";
		try {
			// 整个提交为一个事务
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<friendList.size(); i++) {
				Friend friend = friendList.get(i);
				pstmt.setString(1, friend.getMe());
				pstmt.setString(2, friend.getFriend());
				pstmt.setTimestamp(3, friend.getTime());
				pstmt.setString(4, friend.getDescription());
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
		}
		catch(SQLException e) {
			e.printStackTrace();
			// 事务回滚
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}
		return true;
	}
}
