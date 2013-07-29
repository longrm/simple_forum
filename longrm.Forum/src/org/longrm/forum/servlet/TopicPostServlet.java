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
		
		// δ��½�û�
		if(user==null) {
			message = "����û�е�½���޷��������ӣ�";
			success = false;
			ResultMessage rMessage = new ResultMessage("����", message, success);
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
				forum.getName() + "</a>";

		// ������Ȩ�޿���
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			ResultMessage rMessage = new ResultMessage(loc, "��û���㹻��Ȩ�������������飡", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}
		// �����û�
		if(user.getStatus()==-1) {
			loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "���ѱ����ԣ��޷�������", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return;
		}

		String action = request.getParameter("action");
		// ��������
		if(action.equals("null") || action.equals("new")) {
			TopicPost.newTopic(request, response, loc);
		}
		// �������
		else if(action.equals("reply")) {
			String topic_id = request.getParameter("topic_id");
			// ��ȡ������Ϣ
			Topic topic = TopicService.requestTopic(topic_id);
			
			// �Ƿ�topic_id
			if(topic==null) {
				ResultMessage rMessage = new ResultMessage("����", "�������ʵ����Ӳ����ڻ���ɾ����", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			// ���ӹرջ�����
			else if(topic.getStatus()!=0) {
				loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				if(topic.getStatus()==-1)
					message = "����Ҫ�ظ������������������ܻظ���";
				else if(topic.getStatus()==-2)
					message = "����Ҫ���ʵ������ѹرգ�";
				ResultMessage rMessage = new ResultMessage(loc, message, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			
			// ��������Ȩ�޿���
			if(!AuthorityUtils.checkAuthority(topic.getAuthority(), user)) {
				loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				ResultMessage rMessage = new ResultMessage(loc, "��û�е�½����û���㹻��Ȩ��������������ӣ�", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return;
			}
			loc += "&raquo; <a href=\"viewtopic.jsp?topic_id=" + topic.getId() + "\"> " +
					topic.getTitle() + "</a>";
			TopicPost.replyTopic(request, response, loc);
		}
		// �༭����
		else if(action.indexOf("edit")!=-1) {
			TopicPost.editTopic(request, response, loc);
		}
	}
}
