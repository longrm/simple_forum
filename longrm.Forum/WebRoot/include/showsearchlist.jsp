<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*,java.util.*"%>
	
<%
	ArrayList<Topic> topicList = (ArrayList<Topic>) request.getAttribute("topic_list");
	if(topicList==null)
		return;
	for(int i=0; i<topicList.size(); i++) {
		Topic topic = topicList.get(i);
		Forum tmpForum = (Forum) forumMap.get(topic.getForum_id());
		String imgKind = "topicnew";
		if(topic.getStatus()==-2)
			imgKind = "topicclose";
		else if(topic.getKind()!=null && topic.getKind().equals("vote")) {
			if(topic.getStatus()==-1)
				imgKind = "votelock";
			else
				imgKind = "vote";
		}
		else {
			if(topic.getStatus()==-1){
				imgKind = "topiclock";
			}
		}
%>

	<tr align="center" class="tr3 t_one" onMouseOver="this.className='tr3 t_two'" onMouseOut="this.className='tr3 t_one'">
	<td>
	<a title="打开新窗口" href="viewtopic.jsp?topic_id=<%=topic.getId()%>" target="_blank">
	<img src='images/forum/<%=imgKind%>.gif' border=0>
	</a>
	</td>
	
	<td style="text-align:left;padding-left:8px" id="td_<%=topic.getId()%>">
<%
	if(masterRight.equals("Y")) {
%>
	<input type="checkbox" name="tidarray[]" value="<%=topic.getId()%>" style="display:" />
<%	}	%>
	
	<h3>
	
<%	if(topic.getTop()>0) {	%>
	<img src='images/forum/top_<%=topic.getTop()%>.gif' border=0>
<%	}	%>
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>" id="a_ajax_<%=topic.getId()%>">
<%
	if(topic.getColor()!=null) {
%>
	<font color=<%=topic.getColor()%>>
<%	}
	if(topic.getBold()!=null && topic.getBold().equals("Y")) {
%>
	<b>
<%	}	%>
	<%=topic.getTitle()%>
<%
	if(topic.getBold()!=null && topic.getBold().equals("Y")) {
%>
	</b>
<%	}
	if(topic.getColor()!=null) {
%>
	</font>
<%	}	%>
	</a>
<%	if(topic.getSoul()>0) {	%>
	<img src='images/forum/soul_<%=topic.getSoul()%>.gif' border=0>
<%	}	%>
<%	if(topic.getAuthority()>0) {	%>
	<span class="smalltxt gray"> [权限: <%=topic.getAuthority()%>]</span>
<%	}	%>
	
	</h3>
	</td>
	
	<td class="y-style">
	<a href="viewforum.jsp?forum_id=<%=tmpForum.getId()%>"><%=tmpForum.getName()%></a>
	<%	if(tmpForum.getAuthority()>0) {	%>
	&nbsp;<span class="smalltxt gray"> [权限: <%=tmpForum.getAuthority()%>]</span>
	<%	}	%>
	</td>
	
	<td class="tal y-style">
	<a href="control.jsp?action=view_information&name=<%=topic.getPoster()%>" class="bl"><%=topic.getPoster()%></a>
	<div class="f10"><%=topic.getPost_time().toString().substring(0, 10)%></div>
	</td>
	<td class="tal f10 y-style"><%=topic.getRe_topics()%></td>
	<td class="tal f10 y-style"><%=topic.getClick()%></td>
	<td class="tal y-style">
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>" class="f10">
	<%=topic.getLast_time().toString().substring(0, 16)%>
	</a>
	<br />by: <%=topic.getLast_replier()%>
	</td>
	</tr>
	
<% } %>

