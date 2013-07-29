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
		
		// δ��½�û�
		if(user==null) {
			String message = "����û�е�½���޷����Ͷ���Ϣ��";
			ResultMessage rMessage = new ResultMessage("����Ϣ", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// ������Ϊ��ǰ��½�û�
		String sender = user.getName();
		// ��ȡ�������б�
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
		// �Ƿ񱣴浽������
		String issave = request.getParameter("issave");
		// ��������
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		
		// д����Ϣ�� message_sendbox, message_receivebox
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
					String message = "�û��б���󣬶���Ϣ����ʧ�ܣ��뷵�����·��ͣ�";
					ResultMessage rMessage = new ResultMessage("����Ϣ", message, false);
					request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
					response.sendRedirect("result.jsp");
					return;
				}
			}
			
			conn.setAutoCommit(false);
			
			//д�뷢����
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
			
			// д���ռ���
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
				pstmt.setString(7, "��");
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
			// �ύ����
			conn.commit();
			conn.setAutoCommit(true);
			
			String message = "����Ϣ���ͳɹ�����ת��ԭ�ȵ�ҳ�棬���Ժ�......";
			ResultMessage rMessage = new ResultMessage("����Ϣ", message, request.getHeader("Referer"), true);
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
			String message = "����Ϣ����ʧ�ܣ��뷵�����·��ͣ�";
			ResultMessage rMessage = new ResultMessage("����Ϣ", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
