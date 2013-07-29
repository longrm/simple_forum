package org.longrm.forum.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.util.CookieUtils;

public class LogoutServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// ȥ��session
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("SESSION_USER");
		if(user!=null) {
			session.removeAttribute("BindingNotify");
			session.invalidate();
		}
		
		// ɾ��cookie
		Cookie cookie = CookieUtils.getCookie(request, "chinalong_user_id");
		if(cookie!=null) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		
		ResultMessage rMessage = new ResultMessage("�˳�", "�����˳���½����ת��֮ǰ��ҳ�棬���Ժ�......", 
				request.getHeader("Referer"), true);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
