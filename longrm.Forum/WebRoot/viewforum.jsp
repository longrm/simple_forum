<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*,java.util.*"%>

<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
<script src="js/forum.js" type="text/javascript"></script>

</head>
<body>

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>
 
<DIV id=main>

<%
	Forum forum = (Forum) request.getAttribute("forum");	
	String master = forum.getMaster();
	int pageCount = (Integer) request.getAttribute("page_count");
	String pageIndexStr = request.getParameter("page_index");
	int pageIndex = pageIndexStr == null ? 1 : Integer.parseInt(pageIndexStr);
	if (pageCount == 0)
		pageIndex = 0;
	String condition = (String) request.getAttribute("condition");
	String masterRight = (String) request.getAttribute("master_right");
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge����</A>&raquo; 
	<a href="viewforum.jsp?forum_id=<%=forum.getId()%>"><%=forum.getName()%></a></B>
	</TD>
  </TR>
</TABLE>
</DIV>

<div style="margin-top:15px;"></div>

<%
	if (forum.getRule() != null) {
%>
<div class="t fl">
<table cellspacing="0" cellpadding="0" align="center">
	<tr>
	<td class="h" colspan="2">
	<a style="float:right" href="#" onClick="return IndexDeploy('forum')">
	<img id="img_forum" src="images/cate_fold.gif" />
	</a>
	<b>��鹫��</b>
	</td>
	</tr>
	<tbody id="cate_forum" style="">
	<tr class="tr1">
		<th width="50%" class="t_one" onMouseOver="this.className='t_two'" onMouseOut="this.className='t_one'"> 
		<span><%=forum.getRule()%></span>
		</th>
	</tr>
	</tbody>
</table>
</div>
<%
	}
%>

<%@include file="include/forumnavigate.jsp"%>

<%@include file="include/showtopiclist.jsp"%>
	
<div class="t"><table style="padding:5px; margin-bottom:2px" class="f_two" cellspacing="0" cellpadding="0" width="100%" align="center">
	<tr>
		<td>
		<form action="viewforum.jsp?forum_id=<%=forum.getId()%>" method="post">
		<select name="date">
			<option value=""></option>
			<option value="1">1���ڵ�����</option>
			<option value="2">2���ڵ�����</option>
			<option value="7">1�����ڵ�����</option>
			<option value="30">1�����ڵ�����</option>
			<option value="60">2�����ڵ�����</option>
			<option value="90">3�����ڵ�����</option>
			<option value="180">6�����ڵ�����</option>
			<option value="365">1���ڵ�����</option>
		</select>

		<select name="order_by">
			<option value="last_time" selected>��󷢱�</option>
			<option value="post_time" >����ʱ��</option>
			<option value="click" >����</option>
			<option value="re_topics" >�ظ�</option>
		</select>
		<select name="order">
			<option value="ASC" >����</option>
			<option value="DESC" selected>����</option>
		</select>

		<input class="btn" type="button" value="�� ��" onClick="this.form.submit();">
		</form>
		</td>
		<td align="right">
		<%@include file="include/forumjump.jsp"%>
		</td>
	</tr>
</table></div>

<%@include file="include/forumnavigate.jsp"%>

<div style="margin-top:15px;"></div>

<div class="t">
<table cellspacing="0" cellpadding="0" width="100%">
<%@include file="include/showuserlist.jsp"%>
</table>
</div>

<center>
<img src="images/forum/topicnew.gif" /> �������� <img src="images/forum/topichot.gif" /> �������� 
<img src="images/forum/topiclock.gif" /> �������� <img src="images/forum/topicclose.gif" /> �ر����� 
<img src="images/forum/vote.gif" /> ͶƱ���� <img src="images/forum/votelock.gif" /> ����ͶƱ
</center><br />

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>