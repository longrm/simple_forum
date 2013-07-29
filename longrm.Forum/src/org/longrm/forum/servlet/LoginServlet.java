package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.SessionBinding;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.BeanUtils;
import org.longrm.forum.util.CookieUtils;

public class LoginServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		boolean success = false;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String sql = "select * from fm_user where name=? and password=?";
			
			conn = DAOFactory.getInstance().getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			// �û�����������֤�ɹ�
			if (rs.next()) {
				String db_question = rs.getString("question");
				String db_answer = rs.getString("answer");
				// �ް�ȫ����
				if(db_answer==null) {
					message = "��½�ɹ�����ת����̳��ҳ�����Ժ�......";
					success = true;
				}
				// �����˰�ȫ���⣬��������ʹ�
				else {
					String question = request.getParameter("question");
					String answer = request.getParameter("answer");
					if(question.equals("�Զ�������"))
						question = request.getParameter("customquestion");
					// ��ȫ����ʹ���ȷ����֤�ɹ�
					if(question.equals(db_question) && answer.equals(db_answer)) {
						message = "��½�ɹ�����ת����̳��ҳ�����Ժ�......";
						success = true;
					}
					else {
						message = "��ȫ������ʧ�ܣ����������룡";
						success = false;
					}
				}
			}
			else {
				message = "�û���������������������룡";
				success = false;
			}
			
			// ��½�ɹ�����userд��session������index.jsp
			if(success) {
				String ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();
				Timestamp time = new Timestamp(System.currentTimeMillis());
				User user = new User();
				BeanUtils.populateUserAll(user, rs);
				user.setAccess_ip(ip);
				user.setAccess_time(time);
				// ��SessionBindingд��session���Զ����������û�
				HttpSession session = request.getSession();
				session.setAttribute("SESSION_USER", user);
				session.setAttribute("BindingNotify",new SessionBinding(session.getServletContext()));
				
				// ���·���ʱ���ip
				sql = "update fm_user set access_ip=?, access_time=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ip);
				pstmt.setTimestamp(2, time);
				pstmt.setString(3, user.getId());
				pstmt.execute();
				
				// ���û���־��idд��ͻ�����˵�cookie���Ա��´η���
				Cookie cookie = CookieUtils.getCookie(request, "chinalong_user_id");
				if(cookie==null)
					cookie = new Cookie("chinalong_user_id", user.getId());
				else
					cookie.setValue(user.getId());
				cookie.setMaxAge(Integer.parseInt(request.getParameter("cktime")));
				response.addCookie(cookie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			message = "ϵͳ�����쳣����½ʧ�ܣ�";
			success = false;
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		
		ResultMessage rMessage = new ResultMessage("��½", message, "index.jsp", success);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
