package org.longrm.forum.business;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.ResultMessage;
import org.longrm.forum.bean.Topic;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.ForumService;
import org.longrm.forum.core.SearchEngine;
import org.longrm.forum.util.AuthorityUtils;
import org.longrm.forum.util.Constant;

public class ForumRequest {
	
	/**
	 * ��view forum����ǰȡ�������Ϣ�������б�
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		// ��ȡ������ ���forum_id���ڼ�ҳpageIndex���Ƿ񾫻�soul�����ڡ�����...
		String forum_id = request.getParameter("forum_id");
		String pageIndexStr = request.getParameter("page_index");
		int pageIndex = pageIndexStr==null?1:Integer.parseInt(pageIndexStr);
		String soulStr = request.getParameter("soul");
		boolean soul = (soulStr!=null&&soulStr.equals("Y"))?true:false;
		String date = request.getParameter("date");
		String dateStart=null, dateEnd=null;
		String orderBy = request.getParameter("order_by");
		String order = request.getParameter("order");
		
		// ����������д��jsp�������ڵ�ǰ�������ٲ��һ�ҳ
		String condition = "";
		if(forum_id!=null)
			condition += "&forum_id=" + forum_id;
		if(soulStr!=null)
			condition += "&soul=" + soulStr;
		if(date!=null && !date.equals("")) {
			condition += "&date=" + date;
			int day = Integer.parseInt(date);
			dateStart = new Date(System.currentTimeMillis()-(long)day*24*60*60*1000).toString();
			dateEnd = new Date(System.currentTimeMillis()).toString();
		}
		if(orderBy!=null)
			condition += "&order_by=" + orderBy;
		if(order!=null)
			condition += "&order=" + order;
		condition = condition.substring(1);
		request.setAttribute("condition", condition);

		// ��ȡforum��Ϣ
		Forum forum = ForumService.requestForum(forum_id);
		request.setAttribute("forum", forum);
		
		// �Ƿ�id
		if(forum==null) {
			ResultMessage rMessage = new ResultMessage("���", "�������ʵİ�鲻���ڻ���ɾ����", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// ����Ȩ���ж�
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
			forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "��û�е�½����û���㹻��Ȩ�������������飡", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}

		String forum_ids = SearchEngine.getSearchForums(forum_id);
		
		// ��ȡҳ����
		int topicCount = SearchEngine.searchTopicCount(forum_ids, soul, dateStart, dateEnd);
		int pageCount = topicCount/Constant.FORUM_PAGESIZE;
		if(topicCount%Constant.FORUM_PAGESIZE!=0)
			pageCount++;
		request.setAttribute("page_count", pageCount);
		
		// ��ȡ��鵱ǰҳ��������б�
		if(pageIndex<=0)
			pageIndex=1;
		else if(pageIndex>pageCount)
			pageIndex=pageCount;
		ArrayList<Topic> topicList = SearchEngine.searchTopicList(forum_ids, null, null,
				soul, dateStart, dateEnd, orderBy, order, pageIndex);
		request.setAttribute("topic_list", topicList);
		
		String masterRight = AuthorityUtils.isMaster(forum.getMaster(), user)?"Y":"N";
		request.setAttribute("master_right", masterRight);

		return true;
	}

}
