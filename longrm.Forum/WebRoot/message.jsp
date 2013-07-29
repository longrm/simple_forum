<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*"%>

<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
<script src="js/message.js" type="text/javascript"></script>
</head>
<body>

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>

<%
	String action = request.getParameter("action");
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge社区</A>&raquo; 短信息</B></TD>
  </TR>
</TABLE>
</DIV>

<div class="t" style="margin-top:15px;">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="h" colspan="7">
		&nbsp;【信箱状态：目前有短消息<b> 0 </b>条；最多可存消息 <b>30</b> 条；使用率 <b>0%</b> 】
		</td>
	</tr>
	<tr class="tr2">
		<td><a href="message.jsp?action=receive">收件箱</a></td>
		<td><a href="message.jsp?action=send">发件箱</a></td>
		<td width="20%">
			<a href="message.jsp?action=scout">消息跟踪</a>(<font color="red">可删除已发消息</font>)
		</td>
		<td><a href="message.jsp?action=write">写新消息</a> </td>
		<td><a href="message.jsp?action=banned">屏蔽列表</a> </td>
		<td><a style="cursor:pointer;" onclick="return checkset();">清空</a></td>
	</tr>
</table></div>

<%	if(action==null || action.equals("receive")) {	%>
	<%@include file="include/message_receive.jsp"%>
<%	} else if(action.equals("send")) {	%>
	<%@include file="include/message_send.jsp"%>
<%	} else if(action.equals("read")) {	%>
	<%@include file="include/message_read.jsp"%>
<%	} else if(action.equals("scout")) {	%>
	<%@include file="include/message_scout.jsp"%>
<%	} else if(action.equals("write")) {	%>
	<%@include file="include/message_write.jsp"%>
<%	}	%>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>