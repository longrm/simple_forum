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
	 * 在view forum加载前取出板块信息和帖子列表
	 * @param request
	 * @param response
	 * @return boolean
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		// 获取参数： 板块forum_id、第几页pageIndex、是否精华soul、日期、排序...
		String forum_id = request.getParameter("forum_id");
		String pageIndexStr = request.getParameter("page_index");
		int pageIndex = pageIndexStr==null?1:Integer.parseInt(pageIndexStr);
		String soulStr = request.getParameter("soul");
		boolean soul = (soulStr!=null&&soulStr.equals("Y"))?true:false;
		String date = request.getParameter("date");
		String dateStart=null, dateEnd=null;
		String orderBy = request.getParameter("order_by");
		String order = request.getParameter("order");
		
		// 将请求条件写入jsp，用于在当前条件下再查找或翻页
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

		// 获取forum信息
		Forum forum = ForumService.requestForum(forum_id);
		request.setAttribute("forum", forum);
		
		// 非法id
		if(forum==null) {
			ResultMessage rMessage = new ResultMessage("板块", "您所访问的板块不存在或已删除！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}
		// 访问权限判断
		User user = (User) request.getSession().getAttribute("SESSION_USER");
		if(!AuthorityUtils.checkAuthority(forum.getAuthority(), user)) {
			String loc = "<a href=\"viewforum.jsp?forum_id=" + forum.getId() + "\"> " +
			forum.getName() + "</a>";
			ResultMessage rMessage = new ResultMessage(loc, "您没有登陆或者没有足够的权限来访问这个板块！", false);
			request.getSession().setAttribute("SESSION_RESULT_MESSAGE", rMessage);
			response.sendRedirect("result.jsp");
			return false;
		}

		String forum_ids = SearchEngine.getSearchForums(forum_id);
		
		// 获取页面数
		int topicCount = SearchEngine.searchTopicCount(forum_ids, soul, dateStart, dateEnd);
		int pageCount = topicCount/Constant.FORUM_PAGESIZE;
		if(topicCount%Constant.FORUM_PAGESIZE!=0)
			pageCount++;
		request.setAttribute("page_count", pageCount);
		
		// 获取板块当前页面的主题列表
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
