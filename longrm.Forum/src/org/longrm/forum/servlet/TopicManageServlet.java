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
		// δ��½�û�
		if(user==null) {
			message = "����û�е�½���޷��������ӣ�";
			ResultMessage rMessage = new ResultMessage("��������", message, false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		
		String forum_id = request.getParameter("forum_id");
		// ��ȡforum��Ϣ
		Forum forum = ForumService.requestForum(forum_id);
		
		// forum_id�Ƿ�
		if(forum==null) {
			ResultMessage rMessage = new ResultMessage("���", "�������ʵİ�鲻���ڻ���ɾ����", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
				forum.getName() + "</a>&raquo; ��������";
		
		// ������Ȩ�޿���
		if(!AuthorityUtils.isMaster(forum.getMaster(), user)) {
			ResultMessage rMessage = new ResultMessage(loc, "��û���㹻��Ȩ�����������ӣ�", false);
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
			message = "���Ӳ����ɹ�����ת��ԭ�ȵ�ҳ�棬���Ժ�......";
		else
			message = "���Ӳ���ʧ�ܣ��뷵�����²�����";
		ResultMessage rMessage = new ResultMessage(loc, message, url, result);
		request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
	}

}
