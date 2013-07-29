<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
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

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge社区</A></B></TD>
  </TR>
</TABLE>
</DIV>

<div style="margin-top:15px;"></div>

<%@include file="include/showforumlist.jsp"%>

<div class="t">
<table cellspacing="0" cellpadding="0" width="100%">

<tr>
	<th colspan="2" class="h"><a style="float:right" href="#" onClick="return IndexDeploy('info')"><img id="img_info" src="images/cate_fold.gif" /></a>论坛相关</th>
</tr>
<tr></tr>

<tbody id="cate_info" style="">

	<tr class="tr2"><td colspan="2">

	 &raquo; 友情链接</td></tr>

	<tr class="tr3"><td class="f_two tac" width="4%"><img src="images/index/share.gif" /></td><th class="f_one" width="96%" style="word-break: keep-all"><a href="http://localhost:8080/forum" target="_blank"><img src="images/links/small_logo.gif" alt="javaedge社区" width="88" height="31"></a> <br /></th>
	</tr>

</tbody>

<%@include file="include/showuserlist.jsp"%>

</table>
</div>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>