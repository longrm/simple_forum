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
			
			// 用户名和密码验证成功
			if (rs.next()) {
				String db_question = rs.getString("question");
				String db_answer = rs.getString("answer");
				// 无安全问题
				if(db_answer==null) {
					message = "登陆成功，将转到论坛首页，请稍后......";
					success = true;
				}
				// 设置了安全问题，检验问题和答案
				else {
					String question = request.getParameter("question");
					String answer = request.getParameter("answer");
					if(question.equals("自定义问题"))
						question = request.getParameter("customquestion");
					// 安全问题和答案正确，验证成功
					if(question.equals(db_question) && answer.equals(db_answer)) {
						message = "登陆成功，将转到论坛首页，请稍后......";
						success = true;
					}
					else {
						message = "安全问题或答案失败，请重新输入！";
						success = false;
					}
				}
			}
			else {
				message = "用户名或密码错误，请重新输入！";
				success = false;
			}
			
			// 登陆成功，将user写入session，返回index.jsp
			if(success) {
				String ip = request.getRemoteAddr()==null?"unknown":request.getRemoteAddr();
				Timestamp time = new Timestamp(System.currentTimeMillis());
				User user = new User();
				BeanUtils.populateUserAll(user, rs);
				user.setAccess_ip(ip);
				user.setAccess_time(time);
				// 将SessionBinding写入session，自动处理在线用户
				HttpSession session = request.getSession();
				session.setAttribute("SESSION_USER", user);
				session.setAttribute("BindingNotify",new SessionBinding(session.getServletContext()));
				
				// 更新访问时间和ip
				sql = "update fm_user set access_ip=?, access_time=? where id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, ip);
				pstmt.setTimestamp(2, time);
				pstmt.setString(3, user.getId());
				pstmt.execute();
				
				// 将用户标志符id写入客户浏览端的cookie，以便下次访问
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
			message = "系统出现异常，登陆失败！";
			success = false;
		} finally {
			DBTool.closeConnection(conn, pstmt, rs);
		}
		
		ResultMessage rMessage = new ResultMessage("登陆", message, "index.jsp", success);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
