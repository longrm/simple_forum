<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*"%>
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
	int pageCount = (Integer) request.getAttribute("page_count");
	String pageIndexStr = request.getParameter("page_index");
	int pageIndex = pageIndexStr == null ? 1 : Integer.parseInt(pageIndexStr);
	if (pageCount == 0)
		pageIndex = 0;
	String condition = (String) request.getAttribute("condition");
	Map<String, Forum> forumMap = (Map<String, Forum>) request.getAttribute("forum_map");
	String masterRight = (String) request.getAttribute("master_right");
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge����</A>&raquo; ����</B></TD>
  </TR>
</TABLE>
</DIV>

<div style="margin-top:15px;"></div>

<%@include file="include/searchnavigate.jsp"%>

<div class="t" style="margin:3px auto">
<table cellspacing="0" cellpadding="0" width="100%" id="ajaxtable">
	<tr class="h"><th class="h" colspan="7" style="width:100%">�����б�</th></tr>
	<tbody style="table-layout:fixed;">
	<tr class="tr2">
		<td width="5%" class="y-style">״̬</td> 
		<td width="40%" class="tac">����</td>
		<td width="15%" class="y-style">��̳</td>
		<td width="13%" class="y-style">����</td>
		<td width="6%" class="y-style">�ظ�</td>
		<td width="6%" class="y-style">����</td>
		<td width="15%" class="y-style">��󷢱�</td>
	</tr>
	<%@include file="include/showsearchlist.jsp"%>
	</tbody>
</table>
</div>

<%@include file="include/searchnavigate.jsp"%>
  
</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>