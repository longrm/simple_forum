<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.Forum, java.util.ArrayList, java.sql.Timestamp"%>

<%
  ArrayList<Forum> forumList = (ArrayList<Forum>)request.getAttribute("forum_list");
  if(forumList==null)
    return;
  for(int i=0; i<forumList.size(); i++) {
    Forum forum = forumList.get(i);
    String master = forum.getMaster();
    ArrayList<Forum> subForumList = forum.getSubForumList();
%>

<div class="t">
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
	<th class="h" colspan="6">	
	  <a style="float:right" href="#" onclick="return IndexDeploy('<%=i%>')">
	    <img id="img_<%=i%>" src="images/cate_fold.gif" />
	  </a>
	  
	<% if(master!=null) {
	     String[] masterArray = master.split(","); 
	%>
	  <span class="fr a2 fn" style="margin-right:20px">分类版主：
	  <% for(int j=0; j<masterArray.length; j++) { %>
	    &nbsp;<a href="profile.jsp?action=view_information&name=<%=masterArray[j]%>" class=cfont>
	    <%=masterArray[j]%></a>
	  <% } %> 
	  </span>
	<% } %>

		&raquo; <h2><a href="index.jsp?forum_id=<%=forum.getId()%>" class="cfont"><%=forum.getName()%></a></h2>		
	</th>
	</tr>
	
	<tr></tr>
	<tr class="tr2">
	<td width="*" colspan="2" class="tac">论坛</td>
	<td style="width:6%" class="tal y-style">主题</td>
	<td style="width:6%" class="tal y-style">文章</td>
	<td style="width:20%" class="tal y-style">最后发表</td>
	<td style="width:12%" class="y-style">版主</td>
	</tr>
	<tbody id="cate_<%=i%>" style="">

  <%
	for(int k=0; k<subForumList.size(); k++) { 
    	Forum subForum = subForumList.get(k);
		String subMaster = subForum.getMaster();
		String forumpic = "new";
		if( subForum.getLast_time()==null || 
			subForum.getLast_time().before(new Timestamp(System.currentTimeMillis()-(long)24*60*60*1000)) )
       		forumpic = "old";
  %>
	<tr class="tr3 f_one" onMouseOver="this.className='tr3 f_two'" onMouseOut="this.className='tr3 f_one'">
		<td class="icon tac" width="4%">
		  <a href="viewforum.jsp?forum_id=<%=subForum.getId()%>" target="_blank">
		  <img src="images/index/<%=forumpic%>.gif" /></a>
		</td>
		
		<th>
		<h2><a href="viewforum.jsp?forum_id=<%=subForum.getId()%>"><%=subForum.getName()%></a></h2>
	<%	if(subForum.getAuthority()>0) {	%>
	&nbsp;<span class="smalltxt gray"> [权限: <%=subForum.getAuthority()%>]</span>
	<%	}	%>
		
		<br /><span class="smalltxt gray"><%=subForum.getDescpt()%></span>
		</th>
		
		<td class="tal y-style"><span class="f10"><%=subForum.getTopics()%></span><br /></td>
		<td class="tal y-style"><span class="f10"><%=subForum.getNotes()%></span><br /></td>
		
		<th>
	<%	if(subForum.getLast_replier()!=null) {	%>
		<a href="viewtopic.jsp?topic_id=<%=subForum.getLast_topic_id()%>" class="a2">
		  <%=subForum.getLast_title()%>
		</a><br />
		<span class="f12"><%=subForum.getLast_replier()%></span>
		<span class="f9 gray">[ <%=subForum.getLast_time().toString().substring(0, 16)%> ]</span>
	<%	}	%>
		&nbsp;
		</th>
		
		<td class="y-style" style="word-break: keep-all;word-wrap:no-wrap">
	<% if(subMaster==null) { %>
		无
	<% } else { 
	     String[] subMasterArray = subMaster.split(",");
	     for(int j=0; j<subMasterArray.length; j++) {
	%>
        <a href="profile.jsp?action=view_information&name=<%=subMasterArray[j]%>"><%=subMasterArray[j]%></a>
    <%   }
       }
    %>
        </td>
        
	</tr>
  <% } %>
  
	<tr><td class="f_one" colspan="6" style="height:8px"></td></tr>
	</tbody>
</table>
</div>

<% 
  }
%>
