package org.longrm.forum.business;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Friend;
import org.longrm.forum.bean.Message;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.FriendService;
import org.longrm.forum.core.MessageService;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;

public class MessageRequest {

	/**
	 * 在message短信息加载前取出用户朋友信息
	 * @param request
	 * @param response
	 * @return true
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// 未登陆用户
		if(user==null) {
			String message = "您还没有登陆，无法进入短信息页面！";
			boolean success = false;
			ResultMessage rMessage = new ResultMessage("短信息", message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return success;
		}
		
		String action = request.getParameter("action");
		// 收件箱
		if(action==null || action.equals("receive")) {
			ArrayList<Message> messageList = MessageService.requestReceiveMessageList("receiver", user.getName());
			request.setAttribute("message_list", messageList);
		}
		// 发件箱
		else if(action.equals("send")) {
			ArrayList<Message> messageList = MessageService.requestSendMessageList("sender", user.getName());
			request.setAttribute("message_list", messageList);
		}
		// 读消息
		else if(action.equals("read")) {
			String mid = request.getParameter("message_id");
			String box = request.getParameter("box");
			String table = null;
			if(box.equals("send"))
				table = "message_sendbox";
			else if(box.equals("receive"))
				table = "message_receivebox";
			Message message = MessageService.requestMessage(mid, table);
			
			// 短信息id非法
			if(message==null) {
				String mes = "您要查看的短信息不存在或已删除，请返回重新操作！";
				ResultMessage rMessage = new ResultMessage("短信息", mes, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			else if(box.equals("send") && !message.getSender().equals(user.getName())) {
				String mes = "您没有权力查看他人的短信息，请返回重新操作！";
				ResultMessage rMessage = new ResultMessage("短信息", mes, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			else if(box.equals("receive") && !message.getReceiver().equals(user.getName()) 
					&& !message.getSender().equals(user.getName())) {
				String mes = "您没有权力查看他人的短信息，请返回重新操作！";
				ResultMessage rMessage = new ResultMessage("短信息", mes, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			request.setAttribute("message", message);
			
			// 标志信息已读
			if(box.equals("receive") && user.getName().equals(message.getReceiver()) && message.getIsread().equals("否")) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DAOFactory.getInstance().getConnection();
					String sql = "update message_receivebox set isread=? where id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "是");
					pstmt.setString(2, message.getId());
					pstmt.execute();
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
				finally {
					DBTool.closeConnection(conn, pstmt, null);
				}
			}
		}
		// 消息跟踪
		else if(action.equals("scout")) {
			ArrayList<Message> messageList = MessageService.requestReceiveMessageList("sender", user.getName());
			request.setAttribute("message_list", messageList);
		}
		// 写新信息，加载好友列表
		else if(action.equals("write")) {
			ArrayList<Friend> friendList = FriendService.requestFriendList(user.getName());
			request.setAttribute("friend_list", friendList);
		}
		return true;
	}
}
