<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*"%>

<div class="t3">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr>
<td width="23%" valign="top">
<div class="t">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><th class="h"><b>�û�ͷ��</b></th></tr>
	<tr class="tr1 tac">
		<td style="padding:10px 0">
		<div><img class="pic" src="<%=user.getHead()%>" width="110" height="110" border="0" /></div><br />
		<a href="search.do?poster=<%=user.getName()%>">[�ҵ�����]</a> 
		<a href="search.do?poster=<%=user.getName()%>&soul=Y">[�ҵľ�����]</a>
		</td>
	</tr>
</table>
</div>

<div class="t">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><td class="h"><b>������Ϣ</b></td></tr>
	<tr class="tr1">
		<th>
		�û���: <%=user.getName()%> (<%=user.getSelf_sign()%>)<br />
		ϵͳͷ��: <%=((SysRole)sysRoleMap.get(user.getSys_role_id())).getName()%><br />
		��Աͷ��: <%=((Role)roleMap.get(user.getRole_id())).getName()%><br />
		����: <%=user_info.getSouls()%><br />
		����: <%=user_info.getTopics()%><br />
		����: <%=user_info.getNotes()%><br />
		<%=user_info.getMk1_name()%>: <%=user_info.getMk1()%> <%=user_info.getMk1_unit()%><br />
		<%=user_info.getMk2_name()%>: <%=user_info.getMk2()%> <%=user_info.getMk2_unit()%><br />
		<%=user_info.getMk3_name()%>: <%=user_info.getMk3()%> <%=user_info.getMk3_unit()%><br />
		����ʱ��: <%=user_info.getTime()%> Сʱ<br />
		ע��ʱ��: <%=user.getRegister_time()%><br />
		����¼: <%=user.getAccess_time().toString().substring(0, 16)%><br />
		</th>
	</tr>
</table>
</div>
</td>

<td valign="top">
<div class="t" style="width:98%; margin-right:0">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><th class="h" colspan="6"><b>������������Ϣ</b></th></tr>
<tr class="tr2 tac">
<td width="5%">ID</td>
<td width="35%">����</td>
<td width="15%">������</td>
<td width="15%">�ռ���</td>
<td width="20%">ʱ��</td>
<td width="5%">�Ѷ�</td>
</tr>
</table>
</div>

<div class="t" style="width:98%; margin-right:0">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><th class="h" colspan="5"><b>��������ղ�����</b></th></tr>
<tr class="tr2 tac">
<td width="5%" class="y-style">ID</td>
<td width="35%" class="y-style">����</td>
<td width="15%" class="y-style">��̳</td>
<td width="15%" class="y-style">����</td>
<td width="25%" class="y-style">����ʱ��</td>
</tr>
</table>
</div>
</td>

</tr>
</table>
</div>
