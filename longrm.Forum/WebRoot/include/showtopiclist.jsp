<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.Topic,java.util.*"%>

<form action="managetopicoption.jsp" method="post" name="topicmanageForm" onSubmit="return submitcheck(this);">
<input type="hidden" name="forum_id" value="<%=forum.getId()%>">
<input type="hidden" name="forum_name" value="<%=forum.getName()%>">
<div class="t" style="margin:3px auto">
<table cellspacing="0" cellpadding="0" width="100%" id="ajaxtable">
	<tr>
	<th class="h" colspan="6" style="width:100%">
		<a class="fr a2 fn"></a>
		<a class="fr a2 fn" href="viewforum.jsp?forum_id=<%=forum.getId()%>&soul=Y">精华主题</a>
		<a href="viewforum.jsp?forum_id=<%=forum.getId()%>"><%=forum.getName()%></a><span>　版主: 
	<%
		if (master != null) {
			String[] masterArray = master.split(",");
			for (int i = 0; i < masterArray.length; i++) {
		%>
		<a href="profile.jsp?action=view_information&name=<%=masterArray[i]%>"><%=masterArray[i]%></a> 
	<%
 			}
 		} else {
 	%>
			无
	<%
 		}
 	%>
		<a href="forummanage.jsp"><span class="s3">[版块管理]</span></a>
		</span>
	</th>
	</tr>
	
	<tbody style="table-layout:fixed;">
	<tr class="tr2">
	<td class="tac y-style">状态</td>
	<td style="width:53%" class="tac">文章</td>
	<td style="width:13%" class="y-style">作者</td>
	<td style="width:6%" class="y-style">回复</td>
	<td style="width:6%" class="y-style">人气</td>
	<td style="width:17%" class="y-style">最后发表</td>
	</tr>
	
<%
	ArrayList<Topic> topicList = (ArrayList<Topic>) request.getAttribute("topic_list");
	if(topicList==null)
		return;
	for(int i=0; i<topicList.size(); i++) {
		Topic topic = topicList.get(i);
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
		if(i>0 && topicList.get(i-1).getTop()>0 && topic.getTop()==0) {
%>
	<tr class="tr2"><td colspan="6" class="tac" style="border-top:0">普通主题</td></tr>
	<%	}	%>

	<tr align="center" class="tr3 t_one" onMouseOver="this.className='tr3 t_two'" onMouseOut="this.className='tr3 t_one'">
	<td>
	<a title="打开新窗口" href="viewtopic.jsp?topic_id=<%=topic.getId()%>" target="_blank">
	<img src='images/forum/<%=imgKind%>.gif' border=0>
	</a>
	</td>
	
	<td style="text-align:left;padding-left:8px">
<%
	if(masterRight.equals("Y")) {
%>
	<input type="checkbox" name="topic_id[]" value="<%=topic.getId()%>" />
<%	}	%>
	
	<h3>
	
<%	if(topic.getTop()>0) {	%>
	<img src='images/forum/top_<%=topic.getTop()%>.gif' border=0>
<%	}	%>
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>">
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
<%	if(topic.getPageCount()>1) {	%>
	[ <img src='images/forum/multipage.gif' border=0><span style='font-size:7pt;font-family:verdana;'>
	<%	for(int j=1; j<=topic.getPageCount(); j++) {	%>
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>&page_index=<%=j%>"><%=j%></a>
	<%	}	%>
	</span> ]
<%	}	%>

	</h3>
	</td>
	
	<td class="tal y-style">
	<a href="profile.jsp?action=view_information&name=<%=topic.getPoster()%>" class="bl"><%=topic.getPoster()%></a>
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
	</tbody>

</table>
</div>

<div class="t tac f_one" style="padding:15px 0 15px; margin-bottom:2px">
	<div>管理选项:
		<input name="action" type="radio" value="top" />置顶
		<input name="action" type="radio" value="soul" />精华
		<input name="action" type="radio" value="color" />加亮
		<input name="action" type="radio" value="open" />打开
		<input name="action" type="radio" value="lock" />锁定
		<input name="action" type="radio" value="close" />关闭
		<input name="action" type="radio" value="push" />提前
		<input name="action" type="radio" value="down" />压帖
		<input name="action" type="radio" value="move" />移动
		<input name="action" type="radio" value="delete" checked />删除 
	</div>
	<div style="margin-top:10px;">
		<input class="btn" type="button" name="chkall" value="全 选" onclick="CheckAll(this.form)" />
		<input class="btn" type="submit" name="submittopic" value="提 交" />
	</div>
</div>

</form>
