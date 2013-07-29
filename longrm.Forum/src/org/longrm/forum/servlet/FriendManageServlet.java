package org.longrm.forum.servlet;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Friend;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.FriendService;

public class FriendManageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		boolean result = true;
		String message="";
		// δ��½�û�
		if(user==null) {
			result = false;
			message = "����û�е�½���޷�������ѣ�";
			ResultMessage rMessage = new ResultMessage("�������", message, result);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		String action = request.getParameter("action");
		String url = request.getParameter("url");
		// ��Ӻ���
		if(action.equals("add")) {
			String friendname = request.getParameter("friend");
			ArrayList<Friend> friendList = new ArrayList<Friend>();
			Friend friend = new Friend();
			friend.setMe(user.getName());
			friend.setFriend(friendname);
			friend.setTime(new Timestamp(System.currentTimeMillis()));
			friend.setDescription(request.getParameter("description"));
			friendList.add(friend);
			result = FriendService.addFriendList(friendList);
		}
		
		// ����
		if(result)
			message = "������ѳɹ���������ԭ�ȵ�ҳ�棬���Ժ�......";
		else
			message = "�������ʧ�ܣ��뷵�����²�����";
		ResultMessage rMessage = new ResultMessage("�������", message, url, result);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
