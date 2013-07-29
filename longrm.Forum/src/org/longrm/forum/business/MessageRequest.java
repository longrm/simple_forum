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
	 * ��message����Ϣ����ǰȡ���û�������Ϣ
	 * @param request
	 * @param response
	 * @return true
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// δ��½�û�
		if(user==null) {
			String message = "����û�е�½���޷��������Ϣҳ�棡";
			boolean success = false;
			ResultMessage rMessage = new ResultMessage("����Ϣ", message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return success;
		}
		
		String action = request.getParameter("action");
		// �ռ���
		if(action==null || action.equals("receive")) {
			ArrayList<Message> messageList = MessageService.requestReceiveMessageList("receiver", user.getName());
			request.setAttribute("message_list", messageList);
		}
		// ������
		else if(action.equals("send")) {
			ArrayList<Message> messageList = MessageService.requestSendMessageList("sender", user.getName());
			request.setAttribute("message_list", messageList);
		}
		// ����Ϣ
		else if(action.equals("read")) {
			String mid = request.getParameter("message_id");
			String box = request.getParameter("box");
			String table = null;
			if(box.equals("send"))
				table = "message_sendbox";
			else if(box.equals("receive"))
				table = "message_receivebox";
			Message message = MessageService.requestMessage(mid, table);
			
			// ����Ϣid�Ƿ�
			if(message==null) {
				String mes = "��Ҫ�鿴�Ķ���Ϣ�����ڻ���ɾ�����뷵�����²�����";
				ResultMessage rMessage = new ResultMessage("����Ϣ", mes, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			else if(box.equals("send") && !message.getSender().equals(user.getName())) {
				String mes = "��û��Ȩ���鿴���˵Ķ���Ϣ���뷵�����²�����";
				ResultMessage rMessage = new ResultMessage("����Ϣ", mes, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			else if(box.equals("receive") && !message.getReceiver().equals(user.getName()) 
					&& !message.getSender().equals(user.getName())) {
				String mes = "��û��Ȩ���鿴���˵Ķ���Ϣ���뷵�����²�����";
				ResultMessage rMessage = new ResultMessage("����Ϣ", mes, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			request.setAttribute("message", message);
			
			// ��־��Ϣ�Ѷ�
			if(box.equals("receive") && user.getName().equals(message.getReceiver()) && message.getIsread().equals("��")) {
				Connection conn = null;
				PreparedStatement pstmt = null;
				try {
					conn = DAOFactory.getInstance().getConnection();
					String sql = "update message_receivebox set isread=? where id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "��");
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
		// ��Ϣ����
		else if(action.equals("scout")) {
			ArrayList<Message> messageList = MessageService.requestReceiveMessageList("sender", user.getName());
			request.setAttribute("message_list", messageList);
		}
		// д����Ϣ�����غ����б�
		else if(action.equals("write")) {
			ArrayList<Friend> friendList = FriendService.requestFriendList(user.getName());
			request.setAttribute("friend_list", friendList);
		}
		return true;
	}
}
