package org.longrm.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.ForumService;
import org.longrm.forum.core.TopicManager;
import org.longrm.forum.util.AuthorityUtils;

public class TopicManageServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		String message="";
		String loc="";
		String url = request.getParameter("url");
		// 未登陆用户
		if(user==null) {
			message = "您还没有登陆，无法操作帖子！";
			ResultMessage rMessage = new ResultMessage("操作帖子", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		String forum_id = request.getParameter("forum_id");
		// 获取forum信息
		Forum forum = ForumService.requestForum(forum_id);
		
		// forum_id非法
		if(forum==null) {
			ResultMessage rMessage = new ResultMessage("板块", "您所访问的板块不存在或已删除！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
				forum.getName() + "</a>&raquo; 操作帖子";
		
		// 板块访问权限控制
		if(!AuthorityUtils.isMaster(forum.getMaster(), user)) {
			ResultMessage rMessage = new ResultMessage(loc, "您没有足够的权限来操作帖子！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		String[] topic_ids = request.getParameterValues("topic_id[]");
		String action = request.getParameter("action");
		boolean result = false;
		if(action.equals("top")) {
			int top = Integer.parseInt(request.getParameter("top")); 
			result = TopicManager.topTopic(forum, user, topic_ids, top);
		}
		else if(action.equals("soul")) {
			int soul = Integer.parseInt(request.getParameter("soul")); 
			result = TopicManager.soulTopic(forum, user, topic_ids, soul);
		}
		else if(action.equals("color")) {
			String color = request.getParameter("color");
			String bold = request.getParameter("bold");
			result = TopicManager.colorTopic(forum, user, topic_ids, color, bold);
		}
		else if(action.equals("open")) {
			result = TopicManager.openTopic(forum, user, topic_ids);			
		}
		else if(action.equals("lock")) {
			result = TopicManager.lockTopic(forum, user, topic_ids);			
		}
		else if(action.equals("close")) {
			result = TopicManager.closeTopic(forum, user, topic_ids);
		}
		else if(action.equals("push")) {
			result = TopicManager.pushTopic(forum, user, topic_ids);
		}
		else if(action.equals("down")) {
			result = TopicManager.downTopic(forum, user, topic_ids);
		}
		else if(action.equals("move")) {
			String moveForumId = request.getParameter("move_forum_id");
			result = TopicManager.moveTopic(forum, user, topic_ids, moveForumId);
		}
		else if(action.equals("delete")) {
			result = TopicManager.deleteTopic(forum, user, topic_ids);
		}
		
		if(result)
			message = "帖子操作成功，将转向原先的页面，请稍后......";
		else
			message = "帖子操作失败，请返回重新操作！";
		ResultMessage rMessage = new ResultMessage(loc, message, url, result);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
