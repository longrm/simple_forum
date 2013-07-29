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
		<a class="fr a2 fn" href="viewforum.jsp?forum_id=<%=forum.getId()%>&soul=Y">��������</a>
		<a href="viewforum.jsp?forum_id=<%=forum.getId()%>"><%=forum.getName()%></a><span>������: 
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
			��
	<%
 		}
 	%>
		<a href="forummanage.jsp"><span class="s3">[������]</span></a>
		</span>
	</th>
	</tr>
	
	<tbody style="table-layout:fixed;">
	<tr class="tr2">
	<td class="tac y-style">״̬</td>
	<td style="width:53%" class="tac">����</td>
	<td style="width:13%" class="y-style">����</td>
	<td style="width:6%" class="y-style">�ظ�</td>
	<td style="width:6%" class="y-style">����</td>
	<td style="width:17%" class="y-style">��󷢱�</td>
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
	<tr class="tr2"><td colspan="6" class="tac" style="border-top:0">��ͨ����</td></tr>
	<%	}	%>

	<tr align="center" class="tr3 t_one" onMouseOver="this.className='tr3 t_two'" onMouseOut="this.className='tr3 t_one'">
	<td>
	<a title="���´���" href="viewtopic.jsp?topic_id=<%=topic.getId()%>" target="_blank">
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
	<span class="smalltxt gray"> [Ȩ��: <%=topic.getAuthority()%>]</span>
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
	<div>����ѡ��:
		<input name="action" type="radio" value="top" />�ö�
		<input name="action" type="radio" value="soul" />����
		<input name="action" type="radio" value="color" />����
		<input name="action" type="radio" value="open" />��
		<input name="action" type="radio" value="lock" />����
		<input name="action" type="radio" value="close" />�ر�
		<input name="action" type="radio" value="push" />��ǰ
		<input name="action" type="radio" value="down" />ѹ��
		<input name="action" type="radio" value="move" />�ƶ�
		<input name="action" type="radio" value="delete" checked />ɾ�� 
	</div>
	<div style="margin-top:10px;">
		<input class="btn" type="button" name="chkall" value="ȫ ѡ" onclick="CheckAll(this.form)" />
		<input class="btn" type="submit" name="submittopic" value="�� ��" />
	</div>
</div>

</form>
