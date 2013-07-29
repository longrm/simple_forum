<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.core.*, java.util.*"%>
<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
</head>
<body>

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>

<%
	request.setCharacterEncoding("GBK");
	String action = request.getParameter("action");
	String[] topic_ids = request.getParameterValues("topic_id[]");
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge����</A>&raquo; 
	<a href="viewforum.jsp?forum_id=<%=request.getParameter("forum_id")%>"><%=request.getParameter("forum_name")%></a>
	&raquo; ����ѡ��</B></TD>
  </TR>
</TABLE>
</DIV>

<div style="margin-top:15px;"></div>

<form action="topicmanage.do" method="post">
<input type="hidden" name="forum_id" value="<%=request.getParameter("forum_id")%>">
<input type="hidden" name="url" value="<%=request.getHeader("Referer")%>">
<input type="hidden" name="action" value="<%=action%>">
<%	for(int i=0; i<topic_ids.length; i++) {	%>
<input type="hidden" name="topic_id[]" value="<%=topic_ids[i]%>">
<%	}	%>

<div class="t">
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
	<td align="center" class="f_one" style="padding:7px">	
		<DIV style="MARGIN-TOP: 5px">
		<span>
	<%	if(action.equals("soul")) {	%>
		<input name="soul" type="radio" value="0" checked />��ͨ��
		<input name="soul" type="radio" value="1" />����1
		<input name="soul" type="radio" value="2" />����2
	<%	} else if(action.equals("color")) {	%>
		��ɫ��<input name="color" type="text" value="black" size="10" />
		<input type="checkbox" name="bold" value="Y" />����
	<%	 } else if(action.equals("top")) {	%>
		<input name="top" type="radio" value="0" checked />��ͨ��
		<input name="top" type="radio" value="1" />�ö�1
		<input name="top" type="radio" value="2" />�ö�2
		<input name="top" type="radio" value="3" />�ö�3
	<%	} else if(action.equals("move")) {	%>
		<select name="move_forum_id">
		<option value="">* ���з���</option>
		<%
 		 	ArrayList<Forum> forumList = ForumService.getForumList();
  			if(forumList!=null) {
  				for(int i=0; i<forumList.size(); i++) {
    				Forum forum = forumList.get(i);
    				ArrayList<Forum> subForumList = forum.getSubForumList();
		%>
			<option value="<%=forum.getId()%>">>><%=forum.getName()%></option>
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
	<%	}	%>
		</span>
		</DIV>
		<br><br>
		<input class="btn" type="submit" name="Submit" value=" �� �� " />
	</td>
	</tr>
</table>
</div>
</form>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>