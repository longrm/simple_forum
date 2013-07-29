package org.longrm.forum.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.longrm.forum.bean.Role;
import org.longrm.forum.bean.User;
import org.longrm.forum.bean.UserInfo;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;
import org.longrm.forum.util.Constant;

public class UserService {
	
	// 根据积分更新用户角色
	public static String updateRole(String user_id) {
		UserInfo userinfo = requestUserInfo(user_id);
		Map<String, Role> roleMap = RoleService.getInstance().getRoleMap();
		int total = Constant.getTotalMk(userinfo.getMk1(), userinfo.getMk2(), userinfo.getMk3());
		if(total<0)
			return "-1";
		for(int i=1; i<10; i++) {
			Role r = roleMap.get(new Integer(i).toString());
			if(total>=r.getMincount() && total<r.getMaxcount())
				return r.getId();
		}
		return "-1";
	}
	
	// 是否在线
	public static boolean isOnlineById(ServletContext application, String uid) {
		Vector activeSessions = (Vector) application.getAttribute("activeSessions");
		if(activeSessions!=null && uid!=null) {
			Iterator it = activeSessions.iterator();
			while(it.hasNext()) {
				HttpSession sess = (HttpSession)it.next();
				User user = (User)sess.getAttribute("SESSION_USER");
				if(uid.equals(user.getId()))
					return true;
			}
		}
		return false;
	}

	public static boolean isOnlineByName(ServletContext application, String username) {
		Vector activeSessions = (Vector) application.getAttribute("activeSessions");
		if(activeSessions!=null && username!=null) {
			Iterator it = activeSessions.iterator();
			while(it.hasNext()) {
				HttpSession sess = (HttpSession)it.next();
				User user = (User)sess.getAttribute("SESSION_USER");
				if(username.equals(user.getName()))
					return true;
			}
		}
		return false;
	}
	
	// 通过id获取user
	public static User requestUserById(String id) {
		if(id==null)
			return null;
		User user = new User();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from fm_user where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next())
				BeanUtils.populateUserAll(user, rs);
			else
				return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return user;
	}
	
	// 通过用户名获取user
	public static User requestUserByName(String name) {
		if(name==null || name.equalsIgnoreCase(""))
			return null;
		User user = new User();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from fm_user where name=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			if(rs.next())
				BeanUtils.populateUserAll(user, rs);
			else
				return null;
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		return user;
	}
	
	/**
	 * 获取用户信息userinfo
	 * @param id
	 * @return
	 */
	public static UserInfo requestUserInfo(String id) {
		if(id==null || id.equalsIgnoreCase(""))
			return null;
		UserInfo userinfo = new UserInfo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			String sql = "select * from v_user_info_trans where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				userinfo.setId(id);
				BeanUtils.populateUserInfo(userinfo, rs);
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
		return userinfo;
	}

}
