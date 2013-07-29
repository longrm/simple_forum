<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.util.*, java.util.*"%>

<%
	String name = viewuser.getName();
	boolean viewself = true;
	boolean online = UserService.isOnlineByName(application, name);
	if(!name.equals(user.getName()))
		viewself = false;
%>

<div class="t">
<table width="100%" cellspacing="0" cellpadding="0" style="table-layout:fixed">
	<tr><td class="h" colspan="2">个人信息 (数字ID:<%=viewuser.getId()%>)</td></tr>
	<tr class="tr3"><th>用户名</th><th><%=viewuser.getName()%> (
		<%	if(online) {	%>
			<b>在线</b>
		<%	} else {	%>
			离线
		<%	}	%>
			)--- <a href="search.do?poster=<%=viewuser.getName()%>">[用户主题]</a> 
		<a href="search.do?poster=<%=viewuser.getName()%>&soul=Y">[精华主题]</a>
		<a style="cursor:pointer;" href="search.do?">[用户回复]</a>
		</th>
	</tr>

	<tr class="tr3">
		<th>系统头衔</th>
		<th><%=((SysRole)sysRoleMap.get(viewuser.getSys_role_id())).getName()%></th>
	</tr>

	<tr class="tr3">
		<th>会员头衔</th>
		<th><%=((Role)roleMap.get(viewuser.getRole_id())).getName()%></th>
	</tr>

	<tr class="tr3"><th>精华</th><th><%=user_info.getSouls()%></th></tr>
	<tr class="tr3"><th>主题</th><th><%=user_info.getTopics()%></th></tr>
	<tr class="tr3"><th>发帖</th><th><%=user_info.getNotes()%></th></tr>
	<tr class="tr3"><th><%=user_info.getMk1_name()%></th><th><%=user_info.getMk1()%> <%=user_info.getMk1_unit()%></th></tr>
	<tr class="tr3"><th><%=user_info.getMk2_name()%></th><th><%=user_info.getMk2()%> <%=user_info.getMk2_unit()%></th></tr>
	<tr class="tr3"><th><%=user_info.getMk3_name()%></th><th><%=user_info.getMk3()%> <%=user_info.getMk3_unit()%></th></tr>

	<tr class="tr3">
		<th>你是如何知道本站的？</th>
		<th><%=viewuser.getHowdoknow()%></th>
	</tr>

	<tr class="tr3">
		<th>头像</th><th>
		<img class="pic" src="<%=viewuser.getHead()%>" width="110" height="110" border="0" /></th>
	</tr>

	<tr class="tr3">
		<th>个性签名</th>
		<th><%=viewuser.getSelf_sign()%></th>
	</tr>

	<tr class="tr3">
		<th>帖子签名</th>
		<th><%=viewuser.getTopic_sign()%></th>
	</tr>

<%	if(viewuser.getIspublic()!=null && viewuser.getIspublic().equals("Y")) {	%>
	<tr class="tr3">
		<th>Email</th>
		<th><%=viewuser.getEmail()%></th>
	</tr>
<%	}	%>
	
	<tr class="tr3"><th>性别</th><th><%=viewuser.getSex()%></th></tr>
	
	<tr class="tr3"><th>生日</th><th><%=viewuser.getBirthday()==null?"":viewuser.getBirthday()%></th></tr>
	
	<tr class="tr3"><th>来自:</th><th><%=ServletUtils.replaceIfMissing(viewuser.getHometown(),"")%></th></tr>

	<tr class="tr3"><th>QQ:</th><th><%=ServletUtils.replaceIfMissing(viewuser.getQq(),"")%></th></tr>

	<tr class="tr3">
		<th>blog、个人主页</th>
		<th><a href="<%=viewuser.getBlog()%>" target="_blank"><%=ServletUtils.replaceIfMissing(viewuser.getBlog(),"")%></a></th>
	</tr>
	
	<tr class="tr3"><th>注册时间</th><th><%=viewuser.getRegister_time()%></th></tr>

	<tr class="tr3"><th>在线时间</th><th><%=user_info.getTime()%> 小时</th></tr>

	<tr class="tr3"><th>最后登录</th><th><%=viewuser.getAccess_time().toString().substring(0, 16)%></th></tr>

</table>
</div>

<div style="margin-bottom:25px;text-align:center;">
<input class="btn" type="button" value="返 回" onclick="javascript:history.go(-1)" />
</div>
