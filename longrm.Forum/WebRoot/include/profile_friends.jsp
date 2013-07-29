<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.core.*, org.longrm.forum.util.*, java.util.*"%>

<%
	ArrayList<Friend> friendList = (ArrayList<Friend>)request.getAttribute("friend_list");
%>

<form method="post" action="firendmanage.do" onSubmit="return submitcheck(this);">
<input type="hidden" name="action" value="manage" />
<div class="t">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><th class="h" colspan="6">好友列表</th></tr>
	<tr class="tr2 y-style">
		<td width="5%">ID</td>
		<td width="5%">状态</td>
		<td width="15%">用户名</td>
		<td>描述</td>
		<td width="15%">加入时间</td>
		<td width="5%">删除</td>
	</tr>

<%
	for(int i=0; i<friendList.size(); i++) {	
		Friend friend = friendList.get(i);
		String friendname = friend.getFriend();
		boolean online = UserService.isOnlineByName(application, friendname);
%>
	<tr class="tr3 f_one">
		<td class="y-style"><%=i+1%></td>
		<td class="y-style">
			<%=online?"<b>在线</b>":"离线"%>
		</td>
		<td class="y-style"><a href="profile.jsp?action=view_information&name=<%=friendname%>"><%=friendname%></a></td>
		<td><input class="input" type="text" size="80" name="description[]" value="<%=ServletUtils.replaceIfMissing(friend.getDescription(),"")%>" /></td>
		<td class="y-style"><%=friend.getTime().toString().substring(0,16)%></td>
		<td class="y-style"><input type="checkbox" name="delete_friend[]" value="<%=friendname%>" /></td>
	</tr>
<%	}	%>
	<tr class="tr3 f_one">
		<td><br /></td>
		<td><br /></td>
		<td><input class="input" type="text" name="add_friend[]" size="20" /></td>
		<td><input class="input" type="text" name="add_description[]" size="80" /></td>
		<td><br /></td>
		<td><br /></td>
	</tr>
</table>
</div>
<div style="margin-bottom:25px;text-align:center;">
<input class="btn" type="button" name="chkall" value="全 选" onclick="CheckAll(this.form)" />
<input class="btn" type="submit" value="提 交" />
</div>
</form>