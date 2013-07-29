package org.longrm.forum.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.bean.Topic;
import org.longrm.forum.bean.User;
import org.longrm.forum.core.ForumService;
import org.longrm.forum.core.SearchEngine;
import org.longrm.forum.util.AuthorityUtils;
import org.longrm.forum.util.Constant;

public class SearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		if(request.getHeader("Referer")==null) {
			response.sendRedirect("index.jsp");
			return;
		}
		*/

		// 获取参数： 板块forum_id、第几页pageIndex、是否精华soul、日期、排序...
		String forum_id = request.getParameter("forum_id");
		String title = request.getParameter("title");
		String poster = request.getParameter("poster");
		String user_id = request.getParameter("user_id");
		String pageIndexStr = request.getParameter("page_index");
		int pageIndex = pageIndexStr==null?1:Integer.parseInt(pageIndexStr);
		String soulStr = request.getParameter("soul");
		boolean soul = (soulStr!=null&&soulStr.equals("Y"))?true:false;
		String dateStart = request.getParameter("date_start");
		String dateEnd = request.getParameter("date_end");
		String orderBy = request.getParameter("order_by");
		String order = request.getParameter("order");
		
		// 将请求条件写入jsp，用于在当前条件下再查找或翻页
		String condition = "";
		if(forum_id!=null && !forum_id.equalsIgnoreCase(""))
			condition += "&forum_id=" + forum_id;
		if(poster!=null && !poster.equalsIgnoreCase(""))
			condition += "&poster=" + poster;
		if(user_id!=null)
			condition += "&user_id=" + user_id;
		if(title!=null && !title.equalsIgnoreCase(""))
			condition += "&title=" + title;
		if(soulStr!=null && !soulStr.equalsIgnoreCase(""))
			condition += "&soul=" + soulStr;
		if(dateStart!=null && !dateStart.equalsIgnoreCase(""))
			condition += "&date_start=" + dateStart;
		if(dateEnd!=null && !dateEnd.equalsIgnoreCase(""))
			condition += "&date_end=" + dateEnd;
		if(orderBy!=null && !orderBy.equalsIgnoreCase(""))
			condition += "&order_by=" + orderBy;
		if(order!=null && !order.equalsIgnoreCase(""))
			condition += "&order=" + order;
		if(!condition.equals(""))
			condition = condition.substring(1);
		request.setAttribute("condition", condition);

		// 获取forum信息
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		String forum_ids = SearchEngine.getSearchForums(forum_id);
		
		// 获取页面数
		int topicCount = SearchEngine.searchTopicCount(forum_ids, poster, title, soul, dateStart, dateEnd);
		int pageCount = topicCount/Constant.FORUM_PAGESIZE;
		if(topicCount%Constant.FORUM_PAGESIZE!=0)
			pageCount++;
		request.setAttribute("page_count", pageCount);
		
		// 获取板块当前页面的主题列表
		if(pageIndex<=0)
			pageIndex=1;
		else if(pageIndex>pageCount)
			pageIndex=pageCount;
		ArrayList<Topic> topicList = SearchEngine.searchTopicList(forum_ids, poster, title, 
				soul, dateStart, dateEnd, orderBy, order, pageIndex);
		request.setAttribute("topic_list", topicList);
		
		Map<String, Forum> forumMap = ForumService.requestForumMap();
		request.setAttribute("forum_map", forumMap);
		
		String masterRight = AuthorityUtils.isMaster(null, user)?"Y":"N";
		request.setAttribute("master_right", masterRight);
		
		request.getRequestDispatcher("viewsearchresult.jsp").forward(request, response);
	}

}
