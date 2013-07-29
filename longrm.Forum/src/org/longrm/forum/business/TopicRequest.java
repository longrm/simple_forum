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
	 * ��view topic����ǰ������ת��ȡ�����������б���Ϣ
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
		
		// �Ƿ�id
		if(topic==null) {
			ResultMessage rMessage = new ResultMessage("����", "�������ʵ����Ӳ����ڻ���ɾ����", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		// ����Ȩ��
		String masterRight = AuthorityUtils.isMaster(forum.getMaster(), user)?"Y":"N";
		request.setAttribute("master_right", masterRight);
		
		if(topic.getStatus()==-2 && masterRight.equals("N")) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
				forum.getName() + "</a>&raquo; " + topic.getTitle();
			ResultMessage rMessage = new ResultMessage(loc, "����Ҫ���ʵ������ѹرգ�", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		// ������Ȩ�޿���
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
			forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "��û�е�½����û���㹻��Ȩ�������������飡", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// ��������Ȩ�޿���
		if(!AuthorityUtils.checkAuthority(topic.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>&raquo; " + topic.getTitle();
			ResultMessage rMessage = new ResultMessage(loc, "��û�е�½����û���㹻��Ȩ��������������ӣ�", false);
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
		
		// д��request������viewtopic.jsp
		request.setAttribute("topic", topic);
		request.setAttribute("forum", forum);
		
		// ��������
		ArrayList<TopicFile> topicFileList = FileService.requestTopicFile(topic.getId());
		request.setAttribute("topicfile_list", topicFileList);
		
		// ��������
		HashMap<String, ArrayList<RetopicFile>> retopicFileMap = new HashMap<String, ArrayList<RetopicFile>>();
		for(int i=0; i<retopicList.size(); i++) {
			String id = retopicList.get(i).getId();
			ArrayList<RetopicFile> retopicFileList = FileService.requestRetopicFile(id);
			retopicFileMap.put(id, retopicFileList);
		}
		request.setAttribute("retopicfile_map", retopicFileMap);
		
		// ��������click++
		String sql = "update topic set click=click+1 where id=?";
		DBTool.executeUpdate(sql, new Object[]{topic.getId()});

		return true;
	}
	
	/**
	 * ��post topic����ǰ������ת�Լ���������
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
		
		// δ��½�û�
		if(user==null) {
			message = "����û�е�½���޷��������ӣ�";
			url = "login.jsp";
			success = false;
			ResultMessage rMessage = new ResultMessage("����", message, url, success);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		String forum_id = request.getParameter("forum_id");
		// ��ȡforum��Ϣ
		Forum forum = ForumService.requestForum(forum_id);
		request.setAttribute("forum", forum);
		
		// forum_id�Ƿ�
		if(forum==null) {
			ResultMessage rMessage = new ResultMessage("���", "�������ʵİ�鲻���ڻ���ɾ����", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// ������Ȩ�޿���
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "��û�е�½����û���㹻��Ȩ�������������飡", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// �����û�
		if(user.getStatus()==-1) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
					forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "���ѱ����ԣ��޷�������", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		
		String action = request.getParameter("action");
		String masterRight = AuthorityUtils.isMaster(forum.getMaster(), user)?"Y":"N";
		request.setAttribute("master_right", masterRight);
		
		String title="";
		String content="";
		// ��������
		if(action==null || action.equals("new")) {
		}
		// �������
		else if(action.indexOf("reply")!=-1) {
			String topic_id = request.getParameter("topic_id");
			// ��ȡ������Ϣ
			Topic topic = TopicService.requestTopic(topic_id);
			request.setAttribute("topic", topic);
			
			// �Ƿ�topic_id
			if(topic==null) {
				ResultMessage rMessage = new ResultMessage("����", "�������ʵ����Ӳ����ڻ���ɾ����", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			// ���ӹرջ�����
			else if(topic.getStatus()!=0) {
				String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				if(topic.getStatus()==-1)
					message = "����Ҫ�ظ������������������ܻظ���";
				else if(topic.getStatus()==-2)
					message = "����Ҫ���ʵ������ѹرգ�";
				ResultMessage rMessage = new ResultMessage(loc, message, false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			// ��������Ȩ�޿���
			if(!AuthorityUtils.checkAuthority(topic.getAuthority(), user)) {
				String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
						forum.getName() + "</a>&raquo; " + topic.getTitle();
				ResultMessage rMessage = new ResultMessage(loc, "��û�е�½����û���㹻��Ȩ��������������ӣ�", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			if(action.equals("reply")) {
				title = "Re:" + topic.getTitle();
				content = "";
			}
			// ��������
			// Quote:<blockquote>���õ�570¥fifi2001��2008-06-17 23:40�����&nbsp; :<br />���....= =�ظ�����Ǯô??��~��Ǯ��~~~~~~</blockquote><br />
			else if(action.equals("reply_quote_topic")) {
				title = "Re:" + topic.getTitle();
				content = "<h6 class=\"quote\">Quote:</h6><blockquote><b>����¥��<i>" + topic.getPoster() +
						"</i>��" + topic.getPost_time().toString().substring(0, 16) + 
						"����ģ�</b><pre>\n\n" + topic.getContent() + "</pre></blockquote>\n\n";
			}
			// ���û���
			else if(action.equals("reply_quote_retopic")) {
				String floor = request.getParameter("floor");
				title = "Re:" + topic.getTitle();
				String retopic_id = request.getParameter("retopic_id");
				// ��ȡ������Ϣ
				Retopic retopic = TopicService.requestRetopic(retopic_id);
				content = "<h6 class=\"quote\">Quote:</h6><blockquote><b>���õ�" + floor + "¥<i>" + retopic.getPoster() + 
						"</i>��" + retopic.getPost_time().toString().substring(0, 16) + 
						"����ģ�</b><pre>\n\n" + retopic.getContent() + "</pre></blockquote>\n\n";
			}
			action = "reply";
		}
		// �༭����
		else if(action.equals("edit_topic")) {
			String topic_id = request.getParameter("topic_id");
			// ��ȡ������Ϣ
			Topic topic = TopicService.requestTopic(topic_id);
			request.setAttribute("topic", topic);
			
			//�Ƿ�topic_id
			if(topic==null) {
				ResultMessage rMessage = new ResultMessage("�޸�����", "�������ʵ����Ӳ����ڻ���ɾ����", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			// Ȩ�޿���
			if(!topic.getPoster().equals(user.getName()) && masterRight.equals("N")) {
				ResultMessage rMessage = new ResultMessage("�޸�����", "��û���㹻��Ȩ���޸Ĵ�����", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			
			title = topic.getTitle();
			content = topic.getContent();
			ArrayList<TopicFile> topicFileList = FileService.requestTopicFile(topic.getId());
			request.setAttribute("file_list", topicFileList);
		}
		// �༭����
		else if(action.equals("edit_retopic")) {
			String topic_id = request.getParameter("topic_id");
			String retopic_id = request.getParameter("retopic_id");
			// ��ȡ������Ϣ
			TopicService ts = new TopicService();
			Retopic retopic = ts.requestRetopic(retopic_id);
			
			//	�Ƿ�retopic_id
			if(retopic==null) {
				ResultMessage rMessage = new ResultMessage("�޸�����", "�������ʵ����Ӳ����ڻ���ɾ����", false);
				request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
				response.sendRedirect("result.jsp");
				return false;
			}
			// Ȩ�޿���
			if(!retopic.getPoster().equals(user.getName()) && masterRight.equals("N")) {
				ResultMessage rMessage = new ResultMessage("�޸�����", "��û���㹻��Ȩ���޸Ĵ�����", false);
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
