<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*"%>
<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
</head>
<body onload="javascript:document.registerForm.name.focus();">

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>
<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge社区</A>&raquo; 注册</B></TD><br>
  </TR>
  <tr height="20"></tr>
</TABLE>
</DIV>

  <%
    if (logined) {
		ResultMessage rMessage = new ResultMessage("注册", "您已经注册，请不要重复注册！", false);
		session.setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
    } else {
  %>

<form action="register.do" method="post" name="registerForm" onSubmit="return regcheck(this);">
<div class="t">

<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><th colspan="2" class="h">必填信息</th></tr>

<tr class="tr3 f_one">
<th width="35%"> 用户名<font color="red">*</font><br />不能有空格，可以是中文，长度控制在 3 - 12 字节以内</th>
<th valign="top">
<input class="input" type="text" size="20" maxlength="14" name="name" id="name" onChange="checkname()" /> 
&nbsp;<span id="name_info"></span>
</th>
</tr>

<tr class="tr3 f_two">
<th> 密码<font color="red">*</font><br />英文字母或数字等不少于6位</th>
<th style="vertical-align:middle">
<input class="input" type="password" size="20" maxlength="50" name="password" id="password" onChange="checkpwd();" />
&nbsp;<span id="pwd_info"></span>
</th>
</tr>

<tr class="tr3 f_one">
<th> 确认密码<font color="red">*</font></th>
<th><input class="input" type="password" size="20" maxlength="50" name="regpwdrepeat" id="regpwdrepeat" onChange="checkpwdrepeat();" />&nbsp;&nbsp;<span id="pwdrepeat_info"></span></th>
</tr>

<tr class="tr3 f_one">
<th>认证码<font color="red">*</font></th>
<th><input class="input" type="text" maxLength="4" name="gdcode" size="10" />
<img src="include/imagecode.jsp?" align="absmiddle" onClick="ck(this);" style="cursor:pointer;" /> 请将图片中的数字或英文字母填入到文本框中</th>
</tr>

<tr class="tr3 f_two">
<th> Email<font color="red">*</font></th>
<th>
<input class="input" type="text" size="20" maxlength="75" name="email" id="email" onChange="checkemail();" />
&nbsp;<input type="checkbox" name="ispublic" value="Y" checked />是否公开
&nbsp;<span id="email_info"></span>
</th>
</tr>

<tr class="tr3 f_one">
<th> 论坛防恶意注册<font color="red">*</font><br />请输入答案： 3+5=?(请输入中文答案:八)</th>
<th><input class="input" type="text" name="qanswer" /></th>
</tr>

<tr><td class="h" colspan="2">附加信息</td></tr>

<tr class="tr3 f_one">
	<th>你是如何知道本站的？ <br />为了方便网站的发展，请填写此项，谢谢配合！</th>
	<th><select name="howdoknow"><option value="搜索引擎">搜索引擎</option><option value="朋友介绍">朋友介绍</option><option value="友情链接">友情链接</option><option value="其他方式">其他方式</option></select> <b>请认真填写该栏信息，填写后将不允许修改</b></th>
</tr>

<tr><th colspan="2" class="h">选填信息</th></tr>

<tr class="tr3 f_one">
	<th>安全问题<br />如果启用了安全问题，需要填写正确的答案才能登录论坛</th>
	<th>
		<select name="question" onChange="showcustomquest(this.value)" style="width:130px">
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
		<input id="customquestion" style="display:none" type="text" maxlength="15" name="customquestion" class="input">
	</th>
</tr>

<tr class="tr3 f_two">
	<th>您的答案</th>
	<th><input type="text" name="answer" class="input"></th>
</tr>

<tr class="tr3 f_one">
	<th>性别</th>
	<th>
	<select name="sex">
	<option value="保密">保密</option><option value="男">男</option><option value="女">女</option>
	</select>
	</th>
</tr>

<tr class="tr3 f_one"><th>生日</th>
<th><input class="input" type="text" size="20" maxlength="10" name="birthday" onFocus="setday(this)"/></th>
</tr>

<tr class="tr3 f_one"><th>来自</th>
<th><input class="input" type="text" size="20" maxlength="15" name="hometown" /></th>
</tr>

<tr class="tr3 f_one"><th>QQ</th>
<th><input class="input" type="text" size="20" maxlength="14" name="qq" /></th>
</tr>

<tr class="tr3 f_one"><th>blog、个人网站</th>
<th><input class="input" type="text" size="20" name="blog" /></th>
</tr>

</table></div>
<div style="text-align:center;margin-bottom:25px;">
<input class="btn" type="submit" name="regsubmit" value="提 交" /></div>

</form>
<script src="js/common.js" type="text/javascript"></script>
<script src="js/dateselect.js" type="text/javascript"></script>
<script src="js/register.js" type="text/javascript"></script>
  <%
    }
  %>
</DIV>

<%@include file="include/bottom.jsp"%>

</body>
</html>