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
	<tr><td class="h" colspan="2">������Ϣ (����ID:<%=viewuser.getId()%>)</td></tr>
	<tr class="tr3"><th>�û���</th><th><%=viewuser.getName()%> (
		<%	if(online) {	%>
			<b>����</b>
		<%	} else {	%>
			����
		<%	}	%>
			)--- <a href="search.do?poster=<%=viewuser.getName()%>">[�û�����]</a> 
		<a href="search.do?poster=<%=viewuser.getName()%>&soul=Y">[��������]</a>
		<a style="cursor:pointer;" href="search.do?">[�û��ظ�]</a>
		</th>
	</tr>

	<tr class="tr3">
		<th>ϵͳͷ��</th>
		<th><%=((SysRole)sysRoleMap.get(viewuser.getSys_role_id())).getName()%></th>
	</tr>

	<tr class="tr3">
		<th>��Աͷ��</th>
		<th><%=((Role)roleMap.get(viewuser.getRole_id())).getName()%></th>
	</tr>

	<tr class="tr3"><th>����</th><th><%=user_info.getSouls()%></th></tr>
	<tr class="tr3"><th>����</th><th><%=user_info.getTopics()%></th></tr>
	<tr class="tr3"><th>����</th><th><%=user_info.getNotes()%></th></tr>
	<tr class="tr3"><th><%=user_info.getMk1_name()%></th><th><%=user_info.getMk1()%> <%=user_info.getMk1_unit()%></th></tr>
	<tr class="tr3"><th><%=user_info.getMk2_name()%></th><th><%=user_info.getMk2()%> <%=user_info.getMk2_unit()%></th></tr>
	<tr class="tr3"><th><%=user_info.getMk3_name()%></th><th><%=user_info.getMk3()%> <%=user_info.getMk3_unit()%></th></tr>

	<tr class="tr3">
		<th>�������֪����վ�ģ�</th>
		<th><%=viewuser.getHowdoknow()%></th>
	</tr>

	<tr class="tr3">
		<th>ͷ��</th><th>
		<img class="pic" src="<%=viewuser.getHead()%>" width="110" height="110" border="0" /></th>
	</tr>

	<tr class="tr3">
		<th>����ǩ��</th>
		<th><%=viewuser.getSelf_sign()%></th>
	</tr>

	<tr class="tr3">
		<th>����ǩ��</th>
		<th><%=viewuser.getTopic_sign()%></th>
	</tr>

<%	if(viewuser.getIspublic()!=null && viewuser.getIspublic().equals("Y")) {	%>
	<tr class="tr3">
		<th>Email</th>
		<th><%=viewuser.getEmail()%></th>
	</tr>
<%	}	%>
	
	<tr class="tr3"><th>�Ա�</th><th><%=viewuser.getSex()%></th></tr>
	
	<tr class="tr3"><th>����</th><th><%=viewuser.getBirthday()==null?"":viewuser.getBirthday()%></th></tr>
	
	<tr class="tr3"><th>����:</th><th><%=ServletUtils.replaceIfMissing(viewuser.getHometown(),"")%></th></tr>

	<tr class="tr3"><th>QQ:</th><th><%=ServletUtils.replaceIfMissing(viewuser.getQq(),"")%></th></tr>

	<tr class="tr3">
		<th>blog��������ҳ</th>
		<th><a href="<%=viewuser.getBlog()%>" target="_blank"><%=ServletUtils.replaceIfMissing(viewuser.getBlog(),"")%></a></th>
	</tr>
	
	<tr class="tr3"><th>ע��ʱ��</th><th><%=viewuser.getRegister_time()%></th></tr>

	<tr class="tr3"><th>����ʱ��</th><th><%=user_info.getTime()%> Сʱ</th></tr>

	<tr class="tr3"><th>����¼</th><th><%=viewuser.getAccess_time().toString().substring(0, 16)%></th></tr>

</table>
</div>

<div style="margin-bottom:25px;text-align:center;">
<input class="btn" type="button" value="�� ��" onclick="javascript:history.go(-1)" />
</div>
