package org.longrm.forum.business;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.longrm.forum.bean.Forum;
import org.longrm.forum.core.ForumService;

public class IndexRequest {

	/**
	 * 在index加载前取出板块列表信息
	 * @param request
	 * @param response
	 * @return true
	 */
	public static boolean viewRequest(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String forum_id = request.getParameter("forum_id");
		ArrayList<Forum> forumList = ForumService.requestForumList(forum_id);
//		ArrayList<Forum> forumList = ForumService.getForumList();
//		if(forum_id!=null) {
//			for(int i=0; i<forumList.size(); i++) {
//				Forum forum = forumList.get(i);
//				if(forum.getId().equals(forum_id)) {
//					forumList = new ArrayList<Forum>();
//					forumList.add(forum);
//					break;
//				}
//			}
//		}
		request.setAttribute("forum_list", forumList);
		return true;
	}
}
