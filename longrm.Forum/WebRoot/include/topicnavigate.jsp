<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>

<div class="t3">
<table width="100%" align="center" cellspacing="0" cellpadding="0">
	<tr>
	<td align="left" valign="middle">
	<div class="fl">
	<div class="pages">
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>&page_index=1" style="font-weight:bold">&laquo;</a>
<%
	for(int i=1; i<=pageCount; i++) {
		if(i==pageIndex) {
%>
	<b> <%=i%> </b>
	<%
		} else {
	%>
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>&page_index=<%=i%>"><%=i%></a>
<% 
		}
	}
%>
	<input type="text" size="3" onKeyDown="javascript: 
		if(event.keyCode==13){ 
			var value = this.value;
			if(value<=0)
				value=1;
			else if(value><%=pageCount%>)
				value = <%=pageCount%>;
			location = 'viewtopic.jsp?topic_id=<%=topic.getId()%>&page_index='+value+'';
			return false;
		}"
	>
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>&page_index=<%=pageCount%>" style="font-weight:bold">&raquo;</a> 
	Pages: ( <%=pageIndex%>/<%=pageCount%> total ) 
	</div>
	</div>
	</td>
	
	<td style="text-align:right">
	<a href="posttopic.jsp?action=reply&forum_id=<%=forum.getId()%>&topic_id=<%=topic.getId()%>"><img src="images/topic/reply.png" /></a> 
	<a href="posttopic.jsp?action=new&forum_id=<%=forum.getId()%>"><img src="images/topic/post.png" id="td_post" /></a>
	</td>
	</tr>
</table>
</div>
