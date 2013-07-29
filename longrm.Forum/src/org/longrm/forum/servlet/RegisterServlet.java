package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
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
import org.longrm.forum.util.Constant;
import org.longrm.forum.util.ValidateUtils;

public class RegisterServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String message = "ע��ʧ�ܣ������·���ע�ᣡ";
		boolean success = false;

		// ��֤����֤
		String rand = (String)request.getSession().getAttribute("rand");
		String gdcode = request.getParameter("gdcode");
		if(!gdcode.equals(rand)) {
			message = "��֤������������������룡";
			ResultMessage rMessage = new ResultMessage("ע��", message, "index.jsp", success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// �û�����֤
		String name = request.getParameter("name");
		String checkname = ValidateUtils.checkName(name);
		if(checkname!="success") {
			if(checkname.equals("isNull"))
				message = "�û���Ϊ�գ�";
			else if(checkname.equals("existIllegal"))
				message = "�û������Ƿ��ַ���";
			else if(checkname.equals("existUpper"))
				message = "�û�������д��ĸ��";
			message += "ע��ʧ�ܣ������·���ע�ᣡ";
			
			ResultMessage rMessage = new ResultMessage("ע��", message, "index.jsp", success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		// ��ҳ���ύ��formӳ�䵽bean��
		User user = new User();
		BeanUtils.populate(user, request);
		
		// ��ȫ������
		if(user.getAnswer()==null || user.getAnswer().equalsIgnoreCase("")) {
			user.setAnswer(null);
			user.setQuestion(null);
		}
		else {
			String customQues = request.getParameter("customquestion");
			if(customQues!=null && !customQues.equals(""))
				user.setQuestion(customQues);			
		}
		
		// ���ó�ʼֵ
		String nextId = DBTool.getNextId("fm_user", "id");
		long l = System.currentTimeMillis();
		String ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();
		user.setId(nextId);
		user.setHead(Constant.DEFAUTL_HEAD);
		user.setRole_id("1");
		user.setSys_role_id(Constant.GENERAL_USER);
		user.setRegister_time(new Date(l));
		user.setRegister_ip(ip);
		user.setAccess_time(new Timestamp(l));
		user.setAccess_ip(ip);
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = DAOFactory.getInstance().getConnection();
			conn.setAutoCommit(false);

			// ��ȡ����sql��params
			Map map = DBTool.getInsertSqlParams("fm_user", user);
			sql = (String)map.get("sql");
			ArrayList params = (ArrayList)map.get("params");
			
			// ����fm_user��
			pstmt = conn.prepareStatement(sql);
			DBTool.setStatementParameters(pstmt, params.toArray());
			pstmt.executeUpdate();
			// ����user_info��Ϣ
			sql = "insert into user_info(id) values(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getId());
			pstmt.executeUpdate();
			
			conn.commit();
			conn.setAutoCommit(true);
			
			// ��SessionBindingд��session���Զ����������û�
			HttpSession session = request.getSession();
			session.setAttribute("SESSION_USER", user);
			session.setAttribute("BindingNotify",new SessionBinding(session.getServletContext()));
			
			// ע��ɹ�������index.jsp
			message = "ע��ɹ�����ת����̳��ҳ�����Ժ�......";
			success = true;
			request.getSession().removeAttribute("rand");
		}
		catch (SQLException e) {
			System.out.println(sql);
			try {
				conn.rollback();
			}
			catch(SQLException se) {
				se.printStackTrace();
			}
			e.printStackTrace();
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		
		ResultMessage rMessage = new ResultMessage("ע��", message, "index.jsp", success);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
