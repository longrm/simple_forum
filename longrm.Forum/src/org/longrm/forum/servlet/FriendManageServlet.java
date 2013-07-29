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
		// 未登陆用户
		if(user==null) {
			result = false;
			message = "您还没有登陆，无法管理好友！";
			ResultMessage rMessage = new ResultMessage("管理好友", message, result);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		String action = request.getParameter("action");
		String url = request.getParameter("url");
		// 添加好友
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
		
		// 返回
		if(result)
			message = "管理好友成功，将返回原先的页面，请稍后......";
		else
			message = "管理好友失败，请返回重新操作！";
		ResultMessage rMessage = new ResultMessage("管理好友", message, url, result);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
