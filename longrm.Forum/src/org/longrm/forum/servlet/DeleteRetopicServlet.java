package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.ForumService;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.AuthorityUtils;

public class DeleteRetopicServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		String message="";
		String loc="";
		// 未登陆用户
		if(user==null) {
			message = "您还没有登陆，无法删除回贴！";
			ResultMessage rMessage = new ResultMessage("删除回贴", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		// 获取forum信息
		String forum_id = request.getParameter("forum_id");
		Forum forum = ForumService.requestForum(forum_id);		
		// forum_id非法
		if(forum==null) {
			message = "您所访问的板块不存在或已删除！";
			ResultMessage rMessage = new ResultMessage("板块", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
				forum.getName() + "</a>&raquo; 删除回贴";
		
		// 板块访问权限控制
		if(!AuthorityUtils.isMaster(forum.getMaster(), user)) {
			message = "您没有足够的权限来删除回贴！";
			ResultMessage rMessage = new ResultMessage(loc, message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// 回贴id列表
		String[] retopic_ids = request.getParameterValues("retopic_id[]");
		String topic_id = request.getParameter("topic_id");
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete re_topic where id=?";
		try {
			// 整个提交为一个事务
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			// 删除回贴
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<retopic_ids.length; i++) {
				pstmt.setString(1, retopic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			// 更新主贴信息
			sql = "update topic set re_topics=re_topics-? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, retopic_ids.length);
			pstmt.setString(2, topic_id);
			pstmt.execute();
			
			conn.commit();
			conn.setAutoCommit(true);
			// 操作成功
			message = "删除回贴成功，将转向原先的页面，请稍后......";
			ResultMessage rMessage = new ResultMessage(loc, message, request.getHeader("Referer"), true);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		catch(SQLException e) {
			e.printStackTrace();
			// 操作失败，事务回滚
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			message = "删除回贴失败，请返回重新操作！";
			ResultMessage rMessage = new ResultMessage(loc, message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}
	}

}
