package org.longrm.forum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.Topic;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.ForumService;
import org.longrm.forum.core.TopicPost;
import org.longrm.forum.core.TopicService;
import org.longrm.forum.util.AuthorityUtils;

public class TopicPostServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String message="";
		String loc="";
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		boolean success = false;
		
		// 未登陆用户
		if(user==null) {
			message = "您还没有登陆，无法发表帖子！";
			success = false;
			ResultMessage rMessage = new ResultMessage("发帖", message, success);
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
				forum.getName() + "</a>";

		// 板块访问权限控制
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			ResultMessage rMessage = new ResultMessage(loc, "您没有足够的权限来访问这个板块！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		// 禁言用户
		if(user.getStatus()==-1) {
			loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "您已被禁言，无法发帖！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		String action = request.getParameter("action");
		// 发表新帖
		if(action.equals("null") || action.equals("new")) {
			TopicPost.newTopic(request, response, loc);
		}
		// 发表回帖
		else if(action.equals("reply")) {
			String topic_id = request.getParameter("topic_id");
			// 获取主贴信息
			Topic topic = TopicService.requestTopic(topic_id);
			
			// 非法topic_id
			if(topic==null) {
				ResultMessage rMessage = new ResultMessage("帖子", "您所访问的帖子不存在或已删除！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			// 帖子关闭或锁定
			else if(topic.getStatus()!=0) {
				loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				if(topic.getStatus()==-1)
					message = "您所要回复的帖子已锁定，不能回复！";
				else if(topic.getStatus()==-2)
					message = "你所要访问的帖子已关闭！";
				ResultMessage rMessage = new ResultMessage(loc, message, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			
			// 主贴访问权限控制
			if(!AuthorityUtils.checkAuthority(topic.getAuthority(), user)) {
				loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				ResultMessage rMessage = new ResultMessage(loc, "您没有登陆或者没有足够的权限来访问这个帖子！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			loc += "&raquo; <a href=\"viewtopic.jsp?topic_id=" + topic.getId() + "\"> " +
					topic.getTitle() + "</a>";
			TopicPost.replyTopic(request, response, loc);
		}
		// 编辑贴子
		else if(action.indexOf("edit")!=-1) {
			TopicPost.editTopic(request, response, loc);
		}
	}
}
