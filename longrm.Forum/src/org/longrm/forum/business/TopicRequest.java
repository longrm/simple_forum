package org.longrm.forum.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.RetopicFile;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.Retopic;
import org.longrm.forum.bean.Topic;
import org.longrm.forum.bean.TopicFile;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.FileService;
import org.longrm.forum.core.ForumService;
import org.longrm.forum.core.TopicService;
import org.longrm.forum.dao.DBTool;
import org.longrm.forum.util.AuthorityUtils;

public class TopicRequest {

	/**
	 * 在view topic加载前控制跳转和取出主贴回帖列表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String topic_id = request.getParameter("topic_id");
		String pageIndexStr = request.getParameter("page_index");
		int pageIndex = pageIndexStr==null?1:Integer.parseInt(pageIndexStr);

		Topic topic = TopicService.requestTopic(topic_id);
		Forum forum = null;
		if(topic!=null)
			forum = ForumService.requestForum(topic.getForum_id());
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		
		// 非法id
		if(topic==null) {
			ResultMessage rMessage = new ResultMessage("帖子", "您所访问的帖子不存在或已删除！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		// 管理权限
		String masterRight = AuthorityUtils.isMaster(forum.getMaster(), user)?"Y":"N";
		request.setAttribute("master_right", masterRight);
		
		if(topic.getStatus()==-2 && masterRight.equals("N")) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
				forum.getName() + "</a>&raquo; " + topic.getTitle();
			ResultMessage rMessage = new ResultMessage(loc, "您所要访问的帖子已关闭！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		// 板块访问权限控制
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
			forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "您没有登陆或者没有足够的权限来访问这个板块！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// 主贴访问权限控制
		if(!AuthorityUtils.checkAuthority(topic.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>&raquo; " + topic.getTitle();
			ResultMessage rMessage = new ResultMessage(loc, "您没有登陆或者没有足够的权限来访问这个帖子！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}

//		if(pageIndex<=0)
//			pageIndex=1;
//		else if(pageIndex>topic.getPageCount())
//			pageIndex=topic.getPageCount();
		ArrayList<Retopic> retopicList = new ArrayList<Retopic>();
		if(pageIndex>0 && pageIndex<=topic.getPageCount())
			retopicList = TopicService.requestRetopicList(topic_id, pageIndex);
		request.setAttribute("retopic_list", retopicList);
		
		// 写入request，传给viewtopic.jsp
		request.setAttribute("topic", topic);
		request.setAttribute("forum", forum);
		
		// 主贴附件
		ArrayList<TopicFile> topicFileList = FileService.requestTopicFile(topic.getId());
		request.setAttribute("topicfile_list", topicFileList);
		
		// 回贴附件
		HashMap<String, ArrayList<RetopicFile>> retopicFileMap = new HashMap<String, ArrayList<RetopicFile>>();
		for(int i=0; i<retopicList.size(); i++) {
			String id = retopicList.get(i).getId();
			ArrayList<RetopicFile> retopicFileList = FileService.requestRetopicFile(id);
			retopicFileMap.put(id, retopicFileList);
		}
		request.setAttribute("retopicfile_map", retopicFileMap);
		
		// 更新人气click++
		String sql = "update topic set click=click+1 where id=?";
		DBTool.executeUpdate(sql, new Object[]{topic.getId()});

		return true;
	}
	
	/**
	 * 在post topic加载前控制跳转以及请求数据
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public static boolean postRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		String message="";
		String url="";
		boolean success = false;
		
		// 未登陆用户
		if(user==null) {
			message = "您还没有登陆，无法发表帖子！";
			url = "login.jsp";
			success = false;
			ResultMessage rMessage = new ResultMessage("发帖", message, url, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		String forum_id = request.getParameter("forum_id");
		// 获取forum信息
		Forum forum = ForumService.requestForum(forum_id);
		request.setAttribute("forum", forum);
		
		// forum_id非法
		if(forum==null) {
			ResultMessage rMessage = new ResultMessage("板块", "您所访问的板块不存在或已删除！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// 板块访问权限控制
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "您没有登陆或者没有足够的权限来访问这个板块！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// 禁言用户
		if(user.getStatus()==-1) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "您已被禁言，无法发帖！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		String action = request.getParameter("action");
		String masterRight = AuthorityUtils.isMaster(forum.getMaster(), user)?"Y":"N";
		request.setAttribute("master_right", masterRight);
		
		String title="";
		String content="";
		// 发表新帖
		if(action==null || action.equals("new")) {
		}
		// 发表回帖
		else if(action.indexOf("reply")!=-1) {
			String topic_id = request.getParameter("topic_id");
			// 获取主贴信息
			Topic topic = TopicService.requestTopic(topic_id);
			request.setAttribute("topic", topic);
			
			// 非法topic_id
			if(topic==null) {
				ResultMessage rMessage = new ResultMessage("帖子", "您所访问的帖子不存在或已删除！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			// 帖子关闭或锁定
			else if(topic.getStatus()!=0) {
				String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				if(topic.getStatus()==-1)
					message = "您所要回复的帖子已锁定，不能回复！";
				else if(topic.getStatus()==-2)
					message = "你所要访问的帖子已关闭！";
				ResultMessage rMessage = new ResultMessage(loc, message, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			// 主贴访问权限控制
			if(!AuthorityUtils.checkAuthority(topic.getAuthority(), user)) {
				String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				ResultMessage rMessage = new ResultMessage(loc, "您没有登陆或者没有足够的权限来访问这个帖子！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			if(action.equals("reply")) {
				title = "Re:" + topic.getTitle();
				content = "";
			}
			// 引用主贴
			// Quote:<blockquote>引用第570楼fifi2001于2008-06-17 23:40发表的&nbsp; :<br />这个....= =回复就有钱么??哇~下钱了~~~~~~</blockquote><br />
			else if(action.equals("reply_quote_topic")) {
				title = "Re:" + topic.getTitle();
				content = "<h6 class=\"quote\">Quote:</h6><blockquote><b>引用楼主<i>" + topic.getPoster() +
						"</i>于" + topic.getPost_time().toString().substring(0, 16) + 
						"发表的：</b><pre>\n\n" + topic.getContent() + "</pre></blockquote>\n\n";
			}
			// 引用回贴
			else if(action.equals("reply_quote_retopic")) {
				String floor = request.getParameter("floor");
				title = "Re:" + topic.getTitle();
				String retopic_id = request.getParameter("retopic_id");
				// 获取回贴信息
				Retopic retopic = TopicService.requestRetopic(retopic_id);
				content = "<h6 class=\"quote\">Quote:</h6><blockquote><b>引用第" + floor + "楼<i>" + retopic.getPoster() + 
						"</i>于" + retopic.getPost_time().toString().substring(0, 16) + 
						"发表的：</b><pre>\n\n" + retopic.getContent() + "</pre></blockquote>\n\n";
			}
			action = "reply";
		}
		// 编辑主贴
		else if(action.equals("edit_topic")) {
			String topic_id = request.getParameter("topic_id");
			// 获取主贴信息
			Topic topic = TopicService.requestTopic(topic_id);
			request.setAttribute("topic", topic);
			
			//非法topic_id
			if(topic==null) {
				ResultMessage rMessage = new ResultMessage("修改帖子", "您所访问的帖子不存在或已删除！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			// 权限控制
			if(!topic.getPoster().equals(user.getName()) && masterRight.equals("N")) {
				ResultMessage rMessage = new ResultMessage("修改帖子", "您没有足够的权限修改此贴！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			title = topic.getTitle();
			content = topic.getContent();
			ArrayList<TopicFile> topicFileList = FileService.requestTopicFile(topic.getId());
			request.setAttribute("file_list", topicFileList);
		}
		// 编辑回贴
		else if(action.equals("edit_retopic")) {
			String topic_id = request.getParameter("topic_id");
			String retopic_id = request.getParameter("retopic_id");
			// 获取回贴信息
			TopicService ts = new TopicService();
			Retopic retopic = ts.requestRetopic(retopic_id);
			
			//	非法retopic_id
			if(retopic==null) {
				ResultMessage rMessage = new ResultMessage("修改帖子", "您所访问的帖子不存在或已删除！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			// 权限控制
			if(!retopic.getPoster().equals(user.getName()) && masterRight.equals("N")) {
				ResultMessage rMessage = new ResultMessage("修改帖子", "您没有足够的权限修改此贴！", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			title = retopic.getTitle();
			content = retopic.getContent();
			request.setAttribute("retopic_id", retopic_id);
			request.setAttribute("topic_id", topic_id);
			ArrayList<RetopicFile> retopicFileList = FileService.requestRetopicFile(retopic_id);
			request.setAttribute("file_list", retopicFileList);
		}
		
		request.setAttribute("title", title);
		request.setAttribute("content", content);
		request.setAttribute("action", action);
		return true;
	}

}
