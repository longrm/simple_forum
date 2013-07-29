package org.longrm.forum.core;

import java.io.IOException;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.AuthorityUtils;
import org.longrm.forum.util.Constant;

public class TopicPost {

	/**
	 * 发表新帖
	 * @param request
	 * @param response
	 * @param loc
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean newTopic(HttpServletRequest request,
			HttpServletResponse response, String loc) throws ServletException, IOException {
		String message="";
		String url="";
		boolean success = false;
		User user = (User) request.getSession().getAttribute("SESSION_USER");

		String table = "topic";
		String sql = "";
		String forum_id = request.getParameter("forum_id");
		String id = DBTool.getNextId(table, "id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String poster = user.getName();
		String post_ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();
		int top = 0;
		if(request.getParameter("top")!=null)
			top = Integer.parseInt(request.getParameter("top"));
		int soul = 0;
		if(request.getParameter("soul")!=null)
			soul = Integer.parseInt(request.getParameter("soul"));
		String authorityStr = request.getParameter("authority");
		String color = request.getParameter("color");
		String bold = request.getParameter("bold");
		int authority = 0;
		if(authorityStr!=null && !authorityStr.equalsIgnoreCase(""))
			authority = Integer.parseInt(authorityStr);

		// 权限判断
		if(!AuthorityUtils.checkAuthority(authority, user)) {
			message = "您设置的权限值超过您所拥有的权限，发表失败，请返回重新设置！";
			success = false;
			ResultMessage rMessage = new ResultMessage(loc, message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}

		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		ResultSet rs = null;
		// 提交回帖
		try {
			conn = DAOFactory.getInstance().getConnection();
			// 事务处理
			conn.setAutoCommit(false);
			
			// 将数据插入数据库，对于clob字段，使其为空clob数据:empty_clob()
			stmt = conn.createStatement();
			sql = "insert into "+ table + "(id, title, content, forum_id, " +
					"poster, post_time, post_ip, last_replier, last_time, " +
					"top, soul, authority)" +
					" values('" + id + "', '" + title + "', empty_clob(), '" + forum_id + 
					"', '" + poster + "', systimestamp, '" + post_ip + "', '" +
					poster + "', systimestamp, " + top + ", " + soul + ", " + authority + ")";
			stmt.executeUpdate(sql);
			
			// 从数据库中取出插入的clob字段，并将其赋值给oracle.sql.clob类型的变量
			sql = "select content from " + table + " where id=" + id + " for update";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				// 给clob数据重新赋值，然后更新到数据库中
				Clob clob = rs.getClob(1);
				clob.setString(1, content);
				sql = "update " + table + " set content=?, color=?, bold=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setClob(1, clob);
				pstmt.setString(2, color);
				pstmt.setString(3, bold);
				pstmt.setString(4, id);
				pstmt.executeUpdate();
			}
			
			// 积分系统里更新用户的积分信息 ...
			sql = "update user_info set mk1=mk1+?, mk2=mk2+?, mk3=mk3+?, " +
					"souls=souls+?, topics=topics+?, notes=notes+? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Constant.TOPIC_MK1+soul*Constant.SOUL_MK1);
			pstmt.setInt(2, Constant.TOPIC_MK2+soul*Constant.SOUL_MK2);
			pstmt.setInt(3, Constant.TOPIC_MK3+soul*Constant.SOUL_MK3);
			pstmt.setInt(4, soul>0?1:0);
			pstmt.setInt(5, 1);
			pstmt.setInt(6, 1);
			pstmt.setString(7, user.getId());
			pstmt.executeUpdate();
			
			// 更新角色level
			String newRole = UserService.updateRole(user.getId());
			if(!newRole.equals(user.getRole_id())) {
				sql = "update fm_user set role_id=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newRole);
				pstmt.setString(2, user.getId());
				pstmt.executeUpdate();
				// 更新session里的user
				user.setRole_id(newRole);
				request.getSession().setAttribute("SESSION_USER", user);
			}
			
			// 事务提交
			conn.commit();
			conn.setAutoCommit(true);
			
			message = "发表帖子成功，将转向该主贴，请稍后......";
			url = "viewtopic.jsp?topic_id=" + id;
			success = true;
			ResultMessage rMessage = new ResultMessage(loc, message, url, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return true;
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			// 事务提交失败，回滚
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			e.printStackTrace();
			message = "发表帖子失败，请返回重新发表！";
			success = false;
			ResultMessage rMessage = new ResultMessage(loc, message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
			DBTool.closeConnection(null, pstmt, null);
		}
	}
	
	/**
	 * 发表回帖
	 * @param request
	 * @param response
	 * @param loc
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean replyTopic(HttpServletRequest request,
			HttpServletResponse response, String loc) throws ServletException, IOException {
		String message="";
		String url="";
		boolean success = false;
		User user = (User) request.getSession().getAttribute("SESSION_USER");

		String table = "re_topic";
		String sql = "";
		String forum_id = request.getParameter("forum_id");
		String topic_id = request.getParameter("topic_id");
		String id = DBTool.getNextId(table, "id");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String poster = user.getName();
		Timestamp post_time = new Timestamp(System.currentTimeMillis());
		String post_ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();

		Connection conn = null;
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 提交回帖
		try {
			conn = DAOFactory.getInstance().getConnection();
			// 事务处理
			conn.setAutoCommit(false);
			
			// 将数据插入数据库，对于clob字段，使其为空clob数据:empty_clob()
			stmt = conn.createStatement();
			sql = "insert into "+ table + "(id, topic_id, title, content, forum_id, " +
					"poster, post_time, post_ip)" +
					" values('" + id + "', '" + topic_id + "', '" + title + 
					"', empty_clob(), '" + forum_id + 
					"', '" + poster + "', systimestamp, '" + post_ip + "')";
			stmt.executeUpdate(sql);

			// 从数据库中取出插入的clob字段，并将其赋值给oracle.sql.clob类型的变量
			sql = "select content from " + table + " where id=" + id + " for update";
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				// 给clob数据重新赋值，然后更新到数据库中
				Clob clob = rs.getClob(1);
				clob.setString(1, content);
				sql = "update " + table + " set content=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setClob(1, clob);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			}
			
			// 提交回帖后更新主贴的最后发表信息
			sql = "update topic set re_topics=re_topics+1, last_replier=?, last_time=? where id=?";
			Object[] params = {poster, post_time, topic_id};
			pstmt = conn.prepareStatement(sql);
			DBTool.setStatementParameters(pstmt, params);
			pstmt.executeUpdate();
			
			// 积分系统里更新用户的积分信息 ...
			sql = "update user_info set mk1=?, mk2=mk2+?, mk3=mk3+?, notes=notes+1 where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, Constant.RETOPIC_MK1);
			pstmt.setInt(2, Constant.RETOPIC_MK2);
			pstmt.setInt(3, Constant.RETOPIC_MK3);
			pstmt.setString(4, user.getId());
			pstmt.executeUpdate();
			
			// 更新角色level
			String newRole = UserService.updateRole(user.getId());
			if(!newRole.equals(user.getRole_id())) {
				sql = "update fm_user set role_id=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, newRole);
				pstmt.setString(2, user.getId());
				pstmt.executeUpdate();
				// 更新session里的user
				user.setRole_id(newRole);
				request.getSession().setAttribute("SESSION_USER", user);
			}
			
			// 事务提交
			conn.commit();
			conn.setAutoCommit(true);
			
			message = "发表帖子成功，将转向该主贴，请稍后......";
			url = "viewtopic.jsp?topic_id=" + topic_id;
			success = true;
			ResultMessage rMessage = new ResultMessage(loc, message, url, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return true;
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			// 事务提交失败，回滚
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			e.printStackTrace();
			message = "发表回帖失败，请返回重新发表！";
			success = false;
			ResultMessage rMessage = new ResultMessage(loc, message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		finally {
			DBTool.closeConnection(conn, stmt, rs);
			DBTool.closeConnection(null, pstmt, null);
		}
	}
	
	/**
	 * 编辑帖子
	 * @param request
	 * @param response
	 * @param loc
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean editTopic(HttpServletRequest request,
			HttpServletResponse response, String loc) throws ServletException, IOException {
		String message="";
		String url="";
		boolean success = false;
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		String action = request.getParameter("action");
		String topic_id = request.getParameter("topic_id");
		String retopic_id = request.getParameter("retopic_id");
		// 文件删除列表
		String[] fileList = request.getParameterValues("file_delete_list");
		
		String table="";
		String id="";
		if(action.equals("edit_topic")) {
			table = "topic";
			id = topic_id;
		}
		else if(action.equals("edit_retopic")) {
			table = "re_topic";
			id = retopic_id;
		}

		String sql = "";
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String modifier = user.getName();
		Timestamp modify_time = new Timestamp(System.currentTimeMillis());
		String modify_ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();
		Object[] params = {title, modifier, modify_time, modify_ip, id};

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 提交回帖
		try {
			conn = DAOFactory.getInstance().getConnection();
			// 事务处理
			conn.setAutoCommit(false);
			
			// 删除选定的文件
			if(fileList!=null && fileList.length>0) {
				String fileIds = "";
				for(int i=0; i<fileList.length; i++)
					fileIds += "'" + fileList[i] + "',";
				sql = "delete " + table + "_file where id in (" + fileIds.substring(0, fileIds.length()-1) + ")";
				pstmt = conn.prepareStatement(sql);
				pstmt.executeUpdate();
			}
			
			// 先把clob字段content置空
			sql = "update " + table + " set title=?, content=empty_clob(), modifier=?, " +
				"modify_time=?, modify_ip=? where id=?";
			pstmt = conn.prepareStatement(sql);
			DBTool.setStatementParameters(pstmt, params);
			pstmt.executeUpdate();
			
			// 从数据库中取出插入的clob字段，并将其赋值给oracle.sql.clob类型的变量
			sql = "select content from " + table + " where id=" + id + " for update";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				// 给clob数据重新赋值，然后更新到数据库中
				Clob clob = rs.getClob(1);
				clob.setString(1, content);
				sql = "update " + table + " set content=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setClob(1, clob);
				pstmt.setString(2, id);
				pstmt.executeUpdate();
			}
			
			// 事务提交
			conn.commit();
			conn.setAutoCommit(true);
			
			message = "编辑帖子成功，将转向该主贴，请稍后......";
			url = "viewtopic.jsp?topic_id=" + topic_id;
			success = true;
			ResultMessage rMessage = new ResultMessage(loc, message, url, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return true;
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			// 事务提交失败，回滚
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			e.printStackTrace();
			message = "编辑帖子失败，请返回重新发表！";
			success = false;
			ResultMessage rMessage = new ResultMessage(loc, message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
