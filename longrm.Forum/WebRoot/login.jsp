<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*"%>
<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/login.js" type="text/javascript"></script>
</head>
<body onload="javascript:document.loginForm.name.focus();">

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>
<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge社区</A>&raquo; 登陆</B></TD>
  </TR>
</TABLE>
</DIV>

  <%
    if (logined) {
		ResultMessage rMessage = new ResultMessage("登陆", "您已经登录，请不要重复登陆！", false);
		session.setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
    } else {
  %>

<form action="login.do" method="post" name="loginForm" onSubmit="return logcheck(this);">
<div class="t" style="margin-top:15px;">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><th class="h" colspan="2"><b>登录</b></th></tr>
	<tr class="tr3">
		<td width="20%">用户名</td>
		<td><input class="input" type="text" maxLength="20" name="name" size="40" tabindex="1" /> <a href="register_declare.jsp">马上注册</a></td>
	</tr>
	<tr class="tr3 f_two">
		<td>密　码</td>
		<td><input class="input" type="password" maxLength="20" name="password" size="40" tabindex="2" /> <a href="sendpwd.jsp" target="_blank">找回密码</a></td>
	</tr>

	<tr class="tr3">
		<td>安全问题</td>
		<td>
			<select name="question" onChange="showcustomquest(this.value)" tabindex="3">
			<option value="">无安全问题</option>
			<option value="我爸爸的出生地">我爸爸的出生地</option>
			<option value="我妈妈的出生地">我妈妈的出生地</option>
			<option value="我的小学校名">我的小学校名</option>
			<option value="我的中学校名">我的中学校名</option>
			<option value="我最喜欢的运动">我最喜欢的运动</option>
			<option value="我最喜欢的歌曲">我最喜欢的歌曲</option>
			<option value="我最喜欢的电影">我最喜欢的电影</option>
			<option value="我最喜欢的电影">我最喜欢的颜色</option>
			<option value="自定义问题">自定义问题</option>
			</select>
			<input id="customquestion" style="display:none" type="text" name="customquestion" class="input" size="17" tabindex="4" />
		</td>
	<tr class="tr3 f_two">
		<td>您的答案</td>
		<td><input type="text" name="answer" class="input" size="40" tabindex="5" /> 如果您设置了安全问题，需要填写正确的答案才能登录论坛</td>
	</tr>

	<tr class="tr3 f_two">
		<td>Cookie 有效期:</td>
		<td>
			<input type="radio" name="cktime" value="31536000" checked tabindex="9" />        一年 &nbsp;
			<input type="radio" name="cktime" value="2592000" /> 一个月 &nbsp;
			<input type="radio" name="cktime" value="86400" />			一天 &nbsp;
			<input type="radio" name="cktime" value="3600" />			一小时 &nbsp;
			<input type="radio" name="cktime" value="0" />				即时 &nbsp; &nbsp;
		</td>
	</tr>
</table></div>
<center><input class="btn" type="submit" name="logsubmit" value="登 录" style="width:80px" tabindex="10" /></center>
</form>

  <%
    }
  %>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>