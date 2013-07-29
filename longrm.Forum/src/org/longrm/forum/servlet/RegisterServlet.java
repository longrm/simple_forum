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
		String message = "注册失败，请重新返回注册！";
		boolean success = false;

		// 认证码验证
		String rand = (String)request.getSession().getAttribute("rand");
		String gdcode = request.getParameter("gdcode");
		if(!gdcode.equals(rand)) {
			message = "认证码输入错误，请重新输入！";
			ResultMessage rMessage = new ResultMessage("注册", message, "index.jsp", success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// 用户名验证
		String name = request.getParameter("name");
		String checkname = ValidateUtils.checkName(name);
		if(checkname!="success") {
			if(checkname.equals("isNull"))
				message = "用户名为空！";
			else if(checkname.equals("existIllegal"))
				message = "用户名含非法字符！";
			else if(checkname.equals("existUpper"))
				message = "用户名含大写字母！";
			message += "注册失败，请重新返回注册！";
			
			ResultMessage rMessage = new ResultMessage("注册", message, "index.jsp", success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		// 将页面提交的form映射到bean里
		User user = new User();
		BeanUtils.populate(user, request);
		
		// 安全问题检查
		if(user.getAnswer()==null || user.getAnswer().equalsIgnoreCase("")) {
			user.setAnswer(null);
			user.setQuestion(null);
		}
		else {
			String customQues = request.getParameter("customquestion");
			if(customQues!=null && !customQues.equals(""))
				user.setQuestion(customQues);			
		}
		
		// 设置初始值
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

			// 获取插入sql和params
			Map map = DBTool.getInsertSqlParams("fm_user", user);
			sql = (String)map.get("sql");
			ArrayList params = (ArrayList)map.get("params");
			
			// 插入fm_user表
			pstmt = conn.prepareStatement(sql);
			DBTool.setStatementParameters(pstmt, params.toArray());
			pstmt.executeUpdate();
			// 建立user_info信息
			sql = "insert into user_info(id) values(?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getId());
			pstmt.executeUpdate();
			
			conn.commit();
			conn.setAutoCommit(true);
			
			// 将SessionBinding写入session，自动处理在线用户
			HttpSession session = request.getSession();
			session.setAttribute("SESSION_USER", user);
			session.setAttribute("BindingNotify",new SessionBinding(session.getServletContext()));
			
			// 注册成功，返回index.jsp
			message = "注册成功，将转向论坛首页，请稍后......";
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
		
		ResultMessage rMessage = new ResultMessage("注册", message, "index.jsp", success);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
