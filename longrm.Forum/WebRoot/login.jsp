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
	<B><A href="index.jsp">javaedge����</A>&raquo; ��½</B></TD>
  </TR>
</TABLE>
</DIV>

  <%
    if (logined) {
		ResultMessage rMessage = new ResultMessage("��½", "���Ѿ���¼���벻Ҫ�ظ���½��", false);
		session.setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
    } else {
  %>

<form action="login.do" method="post" name="loginForm" onSubmit="return logcheck(this);">
<div class="t" style="margin-top:15px;">
<table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><th class="h" colspan="2"><b>��¼</b></th></tr>
	<tr class="tr3">
		<td width="20%">�û���</td>
		<td><input class="input" type="text" maxLength="20" name="name" size="40" tabindex="1" /> <a href="register_declare.jsp">����ע��</a></td>
	</tr>
	<tr class="tr3 f_two">
		<td>�ܡ���</td>
		<td><input class="input" type="password" maxLength="20" name="password" size="40" tabindex="2" /> <a href="sendpwd.jsp" target="_blank">�һ�����</a></td>
	</tr>

	<tr class="tr3">
		<td>��ȫ����</td>
		<td>
			<select name="question" onChange="showcustomquest(this.value)" tabindex="3">
			<option value="">�ް�ȫ����</option>
			<option value="�Ұְֵĳ�����">�Ұְֵĳ�����</option>
			<option value="������ĳ�����">������ĳ�����</option>
			<option value="�ҵ�СѧУ��">�ҵ�СѧУ��</option>
			<option value="�ҵ���ѧУ��">�ҵ���ѧУ��</option>
			<option value="����ϲ�����˶�">����ϲ�����˶�</option>
			<option value="����ϲ���ĸ���">����ϲ���ĸ���</option>
			<option value="����ϲ���ĵ�Ӱ">����ϲ���ĵ�Ӱ</option>
			<option value="����ϲ���ĵ�Ӱ">����ϲ������ɫ</option>
			<option value="�Զ�������">�Զ�������</option>
			</select>
			<input id="customquestion" style="display:none" type="text" name="customquestion" class="input" size="17" tabindex="4" />
		</td>
	<tr class="tr3 f_two">
		<td>���Ĵ�</td>
		<td><input type="text" name="answer" class="input" size="40" tabindex="5" /> ����������˰�ȫ���⣬��Ҫ��д��ȷ�Ĵ𰸲��ܵ�¼��̳</td>
	</tr>

	<tr class="tr3 f_two">
		<td>Cookie ��Ч��:</td>
		<td>
			<input type="radio" name="cktime" value="31536000" checked tabindex="9" />        һ�� &nbsp;
			<input type="radio" name="cktime" value="2592000" /> һ���� &nbsp;
			<input type="radio" name="cktime" value="86400" />			һ�� &nbsp;
			<input type="radio" name="cktime" value="3600" />			һСʱ &nbsp;
			<input type="radio" name="cktime" value="0" />				��ʱ &nbsp; &nbsp;
		</td>
	</tr>
</table></div>
<center><input class="btn" type="submit" name="logsubmit" value="�� ¼" style="width:80px" tabindex="10" /></center>
</form>

  <%
    }
  %>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>