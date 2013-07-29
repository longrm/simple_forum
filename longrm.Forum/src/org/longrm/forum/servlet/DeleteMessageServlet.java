package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;

public class DeleteMessageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// δ��½�û�
		if(user==null) {
			String message = "����û�е�½���޷�ɾ������Ϣ��";
			ResultMessage rMessage = new ResultMessage("����Ϣ", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// ��ȡɾ���б�
			String[] message_ids = request.getParameterValues("message_id[]");
			String box = request.getParameter("box");
			String table = null;
			if(box.equals("send"))
				table = "message_sendbox";
			else if(box.equals("receive"))
				table = "message_receivebox";
			
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			String sql = "delete " + table + " where id=?";
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<message_ids.length; i++) {
				pstmt.setString(1, message_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			
			// �ύ����
			conn.commit();
			conn.setAutoCommit(true);
			
			String message = "����Ϣɾ���ɹ�����ת��ԭ�ȵ�ҳ�棬���Ժ�......";
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
			String message = "����Ϣɾ��ʧ�ܣ��뷵�����·��ͣ�";
			ResultMessage rMessage = new ResultMessage("����Ϣ", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
