<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.core.*, java.util.*"%>

<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
</head>
<body>

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>

<%
	String action = request.getParameter("action");
	User viewuser = (User)request.getAttribute("view_user");
	UserInfo user_info = (UserInfo)request.getAttribute("user_info");
	RoleService rs = RoleService.getInstance();
	Map<String, Role> roleMap = rs.getRoleMap();
	Map<String, SysRole> sysRoleMap = rs.getSysRoleMap();
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge����</A>&raquo; ��Ϣ����</B></TD>
  </TR>
</TABLE>
</DIV>

<%	if(action!=null && action.equals("view_information") && !viewuser.getName().equals(user.getName())) {	%>
<div style="margin-top:15px"></div>
<%	} else {	%>
<div class="t" style="margin-top:15px">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr class="tr4 tac">
		<td><a href="profile.jsp">���������ҳ</a></td>
		<td><a href="profile.jsp?action=edit_information">�༭����</a></td>
		<td><a href="profile.jsp?action=view_information">�鿴����</a></td>
		<td><a href="profile.jsp?action=friend_list">�����б�</a></td>
		<td><a href="profile.jsp?action=view_authorization">�û�Ȩ��</a></td>
		<td><a href="profile.jsp?action=favorite">�ղؼ�</a></td>
		<td><a href="search.do?poster=<%=user.getName()%>" target="_blank">�ҵ�����</a></td>
	</tr>
</table>
</div>
<%	}	%>

<%	if(action==null) {	%>
	<%@include file="include/profile_index.jsp"%>
<%	} else if(action.equals("edit_information")) {	%>
	<%@include file="include/profile_edit.jsp"%>
<%	} else if(action.equals("view_information")) {	%>
	<%@include file="include/profile_view.jsp"%>
<%	} else if(action.equals("friend_list")) {	%>
	<%@include file="include/profile_friends.jsp"%>
<%	} else if(action.equals("view_authorization")) {	%>
	<%@include file="include/profile_authority.jsp"%>
<%	} else if(action.equals("favorite")) {	%>
	<%@include file="include/profile_favorite.jsp"%>
<%	}	%>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>