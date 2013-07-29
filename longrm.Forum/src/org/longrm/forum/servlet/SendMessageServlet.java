package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;

public class SendMessageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// 未登陆用户
		if(user==null) {
			String message = "您还没有登陆，无法发送短信息！";
			ResultMessage rMessage = new ResultMessage("短信息", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// 发送者为当前登陆用户
		String sender = user.getName();
		// 获取发往者列表
		String receivers = request.getParameter("receivers");
		String friends = request.getParameter("friends");
		String[] receiverArray = new String[]{};
		String[] friendArray = new String[]{};
		if(!receivers.equalsIgnoreCase("")) {
			receiverArray = receivers.split(";");
		}
		if(!friends.equalsIgnoreCase("")) {
			friendArray = friends.substring(1).split(";");
		}
		// 是否保存到发件箱
		String issave = request.getParameter("issave");
		// 其他参数
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		// 写入信息表： message_sendbox, message_receivebox
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			if(receiverArray.length!=0) {
				sql = "select count(*) from fm_user where name in(";
				for(int k=0; k<receiverArray.length; k++) {
					if(k==0)
						sql += "'" + receiverArray[k] + "'";
					else
						sql += ", '" + receiverArray[k] + "'";
				}
				sql += ")";
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				rs.next();
				if(rs.getInt(1)!=receiverArray.length) {
					String message = "用户列表错误，短信息发送失败，请返回重新发送！";
					ResultMessage rMessage = new ResultMessage("短信息", message, false);
					request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
					response.sendRedirect("result.jsp");
					return;
				}
			}
			
			conn.setAutoCommit(false);
			
			//写入发件箱
			if(issave!=null && issave.equals("Y")) {
				String sendId = DBTool.getMaxId("message_sendbox", "id");
				sql = "insert into message_sendbox values(?, ?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				for(int i=0; i<receiverArray.length+friendArray.length; i++) {
					sendId = DBTool.produceNextId(sendId);
					pstmt.setString(1, sendId);
					pstmt.setString(2, title);
					pstmt.setString(3, content);
					pstmt.setTimestamp(4, time);
					pstmt.setString(5, sender);
					if(i<receiverArray.length)
						pstmt.setString(6, receiverArray[i]);
					else
						pstmt.setString(6, friendArray[i-receiverArray.length]);
					pstmt.addBatch();
				}
				pstmt.executeBatch();
			}
			
			// 写入收件箱
			String receiveId = DBTool.getMaxId("message_receivebox", "id");
			sql = "insert into message_receivebox values(?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<receiverArray.length+friendArray.length; i++) {
				receiveId = DBTool.produceNextId(receiveId);
				pstmt.setString(1, receiveId);
				pstmt.setString(2, title);
				pstmt.setString(3, content);
				pstmt.setTimestamp(4, time);
				pstmt.setString(5, sender);
				if(i<receiverArray.length)
					pstmt.setString(6, receiverArray[i]);
				else
					pstmt.setString(6, friendArray[i-receiverArray.length]);
				pstmt.setString(7, "否");
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
			// 提交事务
			conn.commit();
			conn.setAutoCommit(true);
			
			String message = "短信息发送成功，将转向原先的页面，请稍后......";
			ResultMessage rMessage = new ResultMessage("短信息", message, request.getHeader("Referer"), true);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		catch(SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			String message = "短信息发送失败，请返回重新发送！";
			ResultMessage rMessage = new ResultMessage("短信息", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
