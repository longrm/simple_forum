<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*"%>
<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>

<%
	ResultMessage rMessage = (ResultMessage)session.getAttribute("SESSION_RESULT_MESSAGE");
	String url = rMessage.getUrl();
	if(rMessage.isSuccess()) {
%>
<meta http-equiv="refresh" content="2;url=<%=url%>">
<%	}	%>
</head>
<body>

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge社区</A>&raquo; <%=rMessage.getLocation()%></B></TD>
  </TR>
</TABLE>
</DIV>

<DIV class=t style="MARGIN-TOP: 15px">
<TABLE style="TABLE-LAYOUT: fixed; WORD-WRAP: break-word" cellSpacing=0 
cellPadding=6 width="100%" align=center>
  <TBODY>
  <TR>
    <TH class=h><%=rMessage.getTitle()%></TH>
  </TR>
  <TR>
    <TD class=f_one align="center">
<br><br><br><br>
<pre>
<%	if(url!=null) {%>
<a href=<%=url%>><%=rMessage.getMessage()%></a>
<%	}else{	%>
<%=rMessage.getMessage()%>
<%	}	%>
</pre>
<br><br><br>
	</TD>
  </TR>
  </TBODY>
  </TABLE>
</DIV>

<%
	if(!rMessage.isSuccess()) {
%>
<DIV style="MARGIN-BOTTOM: 25px; TEXT-ALIGN: center">
<INPUT class="btn" onclick=javascript:history.go(-1); type=button value="返回继续操作">
</DIV>
<%	}	%>
  
</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>