<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*"%>

<div class="t3">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr>
<td width="23%" valign="top">
<div class="t">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><th class="h"><b>用户头像</b></th></tr>
	<tr class="tr1 tac">
		<td style="padding:10px 0">
		<div><img class="pic" src="<%=user.getHead()%>" width="110" height="110" border="0" /></div><br />
		<a href="search.do?poster=<%=user.getName()%>">[我的主题]</a> 
		<a href="search.do?poster=<%=user.getName()%>&soul=Y">[我的精华帖]</a>
		</td>
	</tr>
</table>
</div>

<div class="t">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><td class="h"><b>基本信息</b></td></tr>
	<tr class="tr1">
		<th>
		用户名: <%=user.getName()%> (<%=user.getSelf_sign()%>)<br />
		系统头衔: <%=((SysRole)sysRoleMap.get(user.getSys_role_id())).getName()%><br />
		会员头衔: <%=((Role)roleMap.get(user.getRole_id())).getName()%><br />
		精华: <%=user_info.getSouls()%><br />
		主题: <%=user_info.getTopics()%><br />
		发帖: <%=user_info.getNotes()%><br />
		<%=user_info.getMk1_name()%>: <%=user_info.getMk1()%> <%=user_info.getMk1_unit()%><br />
		<%=user_info.getMk2_name()%>: <%=user_info.getMk2()%> <%=user_info.getMk2_unit()%><br />
		<%=user_info.getMk3_name()%>: <%=user_info.getMk3()%> <%=user_info.getMk3_unit()%><br />
		在线时间: <%=user_info.getTime()%> 小时<br />
		注册时间: <%=user.getRegister_time()%><br />
		最后登录: <%=user.getAccess_time().toString().substring(0, 16)%><br />
		</th>
	</tr>
</table>
</div>
</td>

<td valign="top">
<div class="t" style="width:98%; margin-right:0">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><th class="h" colspan="6"><b>最新五条短消息</b></th></tr>
<tr class="tr2 tac">
<td width="5%">ID</td>
<td width="35%">标题</td>
<td width="15%">发件人</td>
<td width="15%">收件人</td>
<td width="20%">时间</td>
<td width="5%">已读</td>
</tr>
</table>
</div>

<div class="t" style="width:98%; margin-right:0">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><th class="h" colspan="5"><b>最新五个收藏主题</b></th></tr>
<tr class="tr2 tac">
<td width="5%" class="y-style">ID</td>
<td width="35%" class="y-style">标题</td>
<td width="15%" class="y-style">论坛</td>
<td width="15%" class="y-style">作者</td>
<td width="25%" class="y-style">发表时间</td>
</tr>
</table>
</div>
</td>

</tr>
</table>
</div>
