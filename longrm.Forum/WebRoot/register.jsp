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
	<B><A href="index.jsp">javaedge����</A>&raquo; ע��</B></TD><br>
  </TR>
  <tr height="20"></tr>
</TABLE>
</DIV>

  <%
    if (logined) {
		ResultMessage rMessage = new ResultMessage("ע��", "���Ѿ�ע�ᣬ�벻Ҫ�ظ�ע�ᣡ", false);
		session.setAttribute("SESSION_RESULT_MESSAGE", rMessage);
		response.sendRedirect("result.jsp");
    } else {
  %>

<form action="register.do" method="post" name="registerForm" onSubmit="return regcheck(this);">
<div class="t">

<table width="100%" cellspacing="0" cellpadding="0" align="center">
<tr><th colspan="2" class="h">������Ϣ</th></tr>

<tr class="tr3 f_one">
<th width="35%"> �û���<font color="red">*</font><br />�����пո񣬿��������ģ����ȿ����� 3 - 12 �ֽ�����</th>
<th valign="top">
<input class="input" type="text" size="20" maxlength="14" name="name" id="name" onChange="checkname()" /> 
&nbsp;<span id="name_info"></span>
</th>
</tr>

<tr class="tr3 f_two">
<th> ����<font color="red">*</font><br />Ӣ����ĸ�����ֵȲ�����6λ</th>
<th style="vertical-align:middle">
<input class="input" type="password" size="20" maxlength="50" name="password" id="password" onChange="checkpwd();" />
&nbsp;<span id="pwd_info"></span>
</th>
</tr>

<tr class="tr3 f_one">
<th> ȷ������<font color="red">*</font></th>
<th><input class="input" type="password" size="20" maxlength="50" name="regpwdrepeat" id="regpwdrepeat" onChange="checkpwdrepeat();" />&nbsp;&nbsp;<span id="pwdrepeat_info"></span></th>
</tr>

<tr class="tr3 f_one">
<th>��֤��<font color="red">*</font></th>
<th><input class="input" type="text" maxLength="4" name="gdcode" size="10" />
<img src="include/imagecode.jsp?" align="absmiddle" onClick="ck(this);" style="cursor:pointer;" /> �뽫ͼƬ�е����ֻ�Ӣ����ĸ���뵽�ı�����</th>
</tr>

<tr class="tr3 f_two">
<th> Email<font color="red">*</font></th>
<th>
<input class="input" type="text" size="20" maxlength="75" name="email" id="email" onChange="checkemail();" />
&nbsp;<input type="checkbox" name="ispublic" value="Y" checked />�Ƿ񹫿�
&nbsp;<span id="email_info"></span>
</th>
</tr>

<tr class="tr3 f_one">
<th> ��̳������ע��<font color="red">*</font><br />������𰸣� 3+5=?(���������Ĵ�:��)</th>
<th><input class="input" type="text" name="qanswer" /></th>
</tr>

<tr><td class="h" colspan="2">������Ϣ</td></tr>

<tr class="tr3 f_one">
	<th>�������֪����վ�ģ� <br />Ϊ�˷�����վ�ķ�չ������д���лл��ϣ�</th>
	<th><select name="howdoknow"><option value="��������">��������</option><option value="���ѽ���">���ѽ���</option><option value="��������">��������</option><option value="������ʽ">������ʽ</option></select> <b>��������д������Ϣ����д�󽫲������޸�</b></th>
</tr>

<tr><th colspan="2" class="h">ѡ����Ϣ</th></tr>

<tr class="tr3 f_one">
	<th>��ȫ����<br />��������˰�ȫ���⣬��Ҫ��д��ȷ�Ĵ𰸲��ܵ�¼��̳</th>
	<th>
		<select name="question" onChange="showcustomquest(this.value)" style="width:130px">
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
		<input id="customquestion" style="display:none" type="text" maxlength="15" name="customquestion" class="input">
	</th>
</tr>

<tr class="tr3 f_two">
	<th>���Ĵ�</th>
	<th><input type="text" name="answer" class="input"></th>
</tr>

<tr class="tr3 f_one">
	<th>�Ա�</th>
	<th>
	<select name="sex">
	<option value="����">����</option><option value="��">��</option><option value="Ů">Ů</option>
	</select>
	</th>
</tr>

<tr class="tr3 f_one"><th>����</th>
<th><input class="input" type="text" size="20" maxlength="10" name="birthday" onFocus="setday(this)"/></th>
</tr>

<tr class="tr3 f_one"><th>����</th>
<th><input class="input" type="text" size="20" maxlength="15" name="hometown" /></th>
</tr>

<tr class="tr3 f_one"><th>QQ</th>
<th><input class="input" type="text" size="20" maxlength="14" name="qq" /></th>
</tr>

<tr class="tr3 f_one"><th>blog��������վ</th>
<th><input class="input" type="text" size="20" name="blog" /></th>
</tr>

</table></div>
<div style="text-align:center;margin-bottom:25px;">
<input class="btn" type="submit" name="regsubmit" value="�� ��" /></div>

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