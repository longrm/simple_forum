package org.longrm.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.FavouriteManager;

public class FavouriteManageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		boolean result = true;
		String message = "";
		// δ��½�û�
		if (user == null) {
			result = false;
			message = "����û�е�½���޷������ղؼУ�";
			ResultMessage rMessage = new ResultMessage("�����ղؼ�", message, result);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		// �����ղؼ�
		String action = request.getParameter("action");
		if(action.equals("add")) {
			String topic_id = request.getParameter("topic_id");
			result = FavouriteManager.addFavourite(user.getName(), topic_id);
		}
		else if(action.equals("delete")) {
			String[] topic_ids = request.getParameterValues("topic_id");
			result = FavouriteManager.deleteFavourite(user.getName(), topic_ids);
		}
		if(result)
			message = "�����ղؼгɹ�����ת��֮ǰ��ҳ�棬���Ժ�.....";
		else
			message = "�����ղؼ�ʧ�ܣ��뷵�����²�����";
		ResultMessage rMessage = new ResultMessage("�����ղؼ�", message, request.getHeader("Referer"), result);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}
}
