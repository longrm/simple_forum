package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.UserService;
import org.longrm.forum.dao.DAOFactory;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.Constant;
import org.longrm.forum.util.ServletUtils;

public class EditSelfInfoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String oldpwd = request.getParameter("oldpwd");
		String password = null;
		String email = null;

		User user = (User) request.getSession().getAttribute("SESSION_USER");
		String message = "";
		String loc = "编辑资料";
		boolean success;		
		
		// 密码验证
		if(!oldpwd.equals("")) {
			if(!oldpwd.equals(user.getPassword())) {
				message = "原密码输入错误，请返回重新输入！";
				success = false;
				ResultMessage rMessage = new ResultMessage(loc, message, success);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			password = request.getParameter("password");
			email = request.getParameter("email");
		}
		else {
			password = user.getPassword();
			email = user.getEmail();
		}
		
		// 获取生日
		Date birthday;
		if(request.getParameter("birthday").equalsIgnoreCase(""))
			birthday = null;
		else {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			long milliseconds=0;
			try {
				milliseconds = df.parse(request.getParameter("birthday")).getTime();
			}
			catch (ParseException pe) {
				pe.printStackTrace();
			}
			birthday = new Date(milliseconds);			
		}
		
		// 安全问题检验
		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		if(question.equals("nochange")) {
			question = user.getQuestion();
			answer = user.getAnswer();
		}
		else if(answer.equalsIgnoreCase("")) {
			question = null;
			answer = null;
		}
		else {
			String customQues = request.getParameter("customquestion");
			if(customQues!=null && !customQues.equals(""))
				question = customQues;		
		}
		
		// 其他信息
		String ispublic = request.getParameter("ispublic");
		String sex = request.getParameter("sex");
		String hometown = ServletUtils.replaceIfMissing(request.getParameter("hometown"), null);
		String qq = ServletUtils.replaceIfMissing(request.getParameter("qq"), null);
		String blog = ServletUtils.replaceIfMissing(request.getParameter("blog"), null);
		String self_sign = ServletUtils.replaceIfMissing(request.getParameter("self_sign"), null);
		String topic_sign = ServletUtils.replaceIfMissing(request.getParameter("topic_sign"), null);
		String head = ServletUtils.replaceIfMissing(request.getParameter("head"), Constant.DEFAUTL_HEAD);
		
		String sql = "";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 提交回帖
		try {
			conn = DAOFactory.getInstance().getConnection();
			sql = "update fm_user set password=?, email=?, ispublic=?, sex=?, birthday=?, hometown=?, " +
					"qq=?, blog=?, self_sign=?, topic_sign=?, head=?, question=?, answer=? where id=?";
			Object[] params = {password, email, ispublic, sex, birthday, hometown, qq, blog, 
					self_sign, topic_sign, head, question, answer, user.getId()};
			pstmt = conn.prepareStatement(sql);
			DBTool.setStatementParameters(pstmt, params);
			pstmt.executeUpdate();
			
			// 更新session里的user
			user.setPassword(password);
			user.setEmail(email);
			user.setIspublic(ispublic);
			user.setSex(sex);
			user.setBirthday(birthday);
			user.setHometown(hometown);
			user.setQq(qq);
			user.setBlog(blog);
			user.setSelf_sign(self_sign);
			user.setTopic_sign(topic_sign);
			user.setHead(head);
			request.getSession().setAttribute("SESSION_USER", user);
			
			message = "编辑资料成功，将返回原来的页面，请稍后......";
			success = true;
			ResultMessage rMessage = new ResultMessage(loc, message, request.getHeader("Referer"), success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		catch(SQLException e) {
			System.out.println("\n"+sql+"\n");
			e.printStackTrace();
			message = "编辑资料失败，请返回重新输入！";
			success = false;
			ResultMessage rMessage = new ResultMessage(loc, message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
	}

}
