<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.core.*, java.util.*"%>

		<form name="jump" method="post">
		<select onchange="forumjump(this.options[this.selectedIndex].value)">
		<option value="">¿ìËÙÌøÖÁ</option>
	<%
  		ArrayList<Forum> forumList = ForumService.getForumList();
  		if(forumList!=null) {
  		for(int i=0; i<forumList.size(); i++) {
    		Forum pareForum = forumList.get(i);
    		ArrayList<Forum> subForumList = pareForum.getSubForumList();
	%>
		<option value="<%=pareForum.getId()%>">>> <%=pareForum.getName()%></option>
	<%
			for(int j=0; j<subForumList.size(); j++) {
				Forum subForum = subForumList.get(j);
	%>
			<option value="<%=subForum.getId()%>"> &nbsp;|- <%=subForum.getName()%></option>
	<%
			}
		}
  	}
	%>
		</select>
		</form>
