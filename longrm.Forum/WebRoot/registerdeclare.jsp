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
	<B><A href="index.jsp">javaedge社区</A>&raquo; 注册</B></TD>
  </TR>
</TABLE>
</DIV>

  <%
    if (logined) {
  %>
<div class="t" style="margin-top:35px;">
<table width="100%">
<tr><td>
<table height="300" align="center">
    <tr class="tr3 f_two" height="10"></tr>
	<tr><th align="center"><font color="red" size="4"><b>您已经注册，请不要重复注册！</b></font></th></tr>
	<tr class="tr3 f_two" height="10"></tr>
</table>
</td></tr>
</table>
</div>
  <%
    } else {
  %>

<DIV class=t style="MARGIN-TOP: 15px">
<TABLE style="TABLE-LAYOUT: fixed; WORD-WRAP: break-word" cellSpacing=0 
cellPadding=6 width="100%" align=center>
  <TBODY>
  <TR>
    <TH class=h>注册</TH></TR>
  <TR>
    <TD class=f_one>
<pre>
当您申请用户时，表示您已经同意遵守本规章。 

欢迎您加入本站点参加交流和讨论，本站点为公共论坛，为维护网上公共秩序和社会稳定，请您自觉遵守以下条款： 

一、不得利用本站危害国家安全、泄露国家秘密，不得侵犯国家社会集体的和公民的合法权益，不得利用本站制作、复制和传播下列信息：
　 （一）煽动抗拒、破坏宪法和法律、行政法规实施的；
　（二）煽动颠覆国家政权，推翻社会主义制度的；
　（三）煽动分裂国家、破坏国家统一的；
　（四）煽动民族仇恨、民族歧视，破坏民族团结的；
　（五）捏造或者歪曲事实，散布谣言，扰乱社会秩序的；
　（六）宣扬封建迷信、淫秽、色情、赌博、暴力、凶杀、恐怖、教唆犯罪的；
　（七）公然侮辱他人或者捏造事实诽谤他人的，或者进行其他恶意攻击的；
　（八）损害国家机关信誉的；
　（九）其他违反宪法和法律行政法规的；
　（十）进行商业广告行为的。

二、互相尊重，对自己的言论和行为负责。
三、禁止在申请用户时使用相关本站的词汇，或是带有侮辱、毁谤、造谣类的或是有其含义的各种语言进行注册用户，否则我们会将其删除。
四、禁止以任何方式对本站进行各种破坏行为。
五、如果您有违反国家相关法律法规的行为，本站概不负责，您的登录论坛信息均被记录无疑，必要时，我们会向相关的国家管理部门提供此类信息。 
</pre>
</TD></TR></TBODY></TABLE></DIV>

<DIV style="MARGIN-BOTTOM: 25px; TEXT-ALIGN: center">
<INPUT type=button onClick=javascript:location.href="register.jsp" value="同 意"> 
<INPUT onclick=javascript:history.go(-1); type=button value="取 消">
</DIV>
  <%
    }
  %>
  
</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>