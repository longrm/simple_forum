package org.longrm.forum.business;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Friend;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.bean.UserInfo;
import org.longrm.forum.core.FriendService;
import org.longrm.forum.core.UserService;

public class ProfileRequest {

	/**
	 * 在profile控制面板加载前取出用户信息
	 * @param request
	 * @param response
	 * @return true
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		boolean success = true;
		String message="";
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// 未登陆用户
		if(user==null) {
			message = "您还没有登陆，无法进入控制面板！";
			success = false;
			ResultMessage rMessage = new ResultMessage("控制面板", message, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return success;
		}
		
		String action = request.getParameter("action");
		if(action==null || action.equals("edit_information")) {			
			UserInfo userinfo = UserService.requestUserInfo(user.getId());
			request.setAttribute("user_info", userinfo);
		}
		// 查看用户资料
		else if(action.equals("view_information")) {
			String user_id = request.getParameter("id");
			String user_name = request.getParameter("name");
			
			// 查看的用户资料
			User viewuser = null;
			if(user_id!=null && user_id.equals(user.getId()))
				viewuser = user;
			else if(user_name!=null && user_name.equals(user.getName()))
				viewuser = user;
			else if(user_id==null && user_name==null)
				viewuser = user;
			else {
				viewuser = UserService.requestUserById(user_id);
				if(viewuser==null)
					viewuser = UserService.requestUserByName(user_name);
				if(viewuser==null) {
					message = "您要查看的用户不存在或已删除！";
					success = false;
					ResultMessage rMessage = new ResultMessage("信息资料", message, success);
					request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
					response.sendRedirect("result.jsp");
					return success;
				}
			}
			request.setAttribute("view_user", viewuser);			
			UserInfo userinfo = UserService.requestUserInfo(viewuser.getId());
			request.setAttribute("user_info", userinfo);
		}
		else if(action.equals("friend_list")) {
			ArrayList<Friend> friendList = FriendService.requestFriendList(user.getName());
			request.setAttribute("friend_list", friendList);
		}
		
		return success;
	}
}
