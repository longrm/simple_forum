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
		// δ��½�û�
		if(user==null) {
			message = "����û�е�½���޷�ɾ��������";
			ResultMessage rMessage = new ResultMessage("ɾ������", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		// ��ȡforum��Ϣ
		String forum_id = request.getParameter("forum_id");
		Forum forum = ForumService.requestForum(forum_id);		
		// forum_id�Ƿ�
		if(forum==null) {
			message = "�������ʵİ�鲻���ڻ���ɾ����";
			ResultMessage rMessage = new ResultMessage("���", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
				forum.getName() + "</a>&raquo; ɾ������";
		
		// ������Ȩ�޿���
		if(!AuthorityUtils.isMaster(forum.getMaster(), user)) {
			message = "��û���㹻��Ȩ����ɾ��������";
			ResultMessage rMessage = new ResultMessage(loc, message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// ����id�б�
		String[] retopic_ids = request.getParameterValues("retopic_id[]");
		String topic_id = request.getParameter("topic_id");
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete re_topic where id=?";
		try {
			// �����ύΪһ������
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);
			// ɾ������
			pstmt = conn.prepareStatement(sql);
			for(int i=0; i<retopic_ids.length; i++) {
				pstmt.setString(1, retopic_ids[i]);
				pstmt.addBatch();
			}
			pstmt.executeBatch();
			// ����������Ϣ
			sql = "update topic set re_topics=re_topics-? where id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, retopic_ids.length);
			pstmt.setString(2, topic_id);
			pstmt.execute();
			
			conn.commit();
			conn.setAutoCommit(true);
			// �����ɹ�
			message = "ɾ�������ɹ�����ת��ԭ�ȵ�ҳ�棬���Ժ�......";
			ResultMessage rMessage = new ResultMessage(loc, message, request.getHeader("Referer"), true);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		catch(SQLException e) {
			e.printStackTrace();
			// ����ʧ�ܣ�����ع�
			try {
				conn.rollback();
			}
			catch(SQLException re) {
				re.printStackTrace();
			}
			message = "ɾ������ʧ�ܣ��뷵�����²�����";
			ResultMessage rMessage = new ResultMessage(loc, message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
		}
		finally {
			DBTool.closeConnection(conn, pstmt, null);
		}
	}

}
