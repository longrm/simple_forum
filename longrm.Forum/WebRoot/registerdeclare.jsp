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
	<B><A href="index.jsp">javaedge����</A>&raquo; ע��</B></TD>
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
	<tr><th align="center"><font color="red" size="4"><b>���Ѿ�ע�ᣬ�벻Ҫ�ظ�ע�ᣡ</b></font></th></tr>
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
    <TH class=h>ע��</TH></TR>
  <TR>
    <TD class=f_one>
<pre>
���������û�ʱ����ʾ���Ѿ�ͬ�����ر����¡� 

��ӭ�����뱾վ��μӽ��������ۣ���վ��Ϊ������̳��Ϊά�����Ϲ������������ȶ��������Ծ������������ 

һ���������ñ�վΣ�����Ұ�ȫ��й¶�������ܣ������ַ�������Ἧ��ĺ͹���ĺϷ�Ȩ�棬�������ñ�վ���������ƺʹ���������Ϣ��
�� ��һ��ɿ�����ܡ��ƻ��ܷ��ͷ��ɡ���������ʵʩ�ģ�
��������ɿ���߸�������Ȩ���Ʒ���������ƶȵģ�
��������ɿ�����ѹ��ҡ��ƻ�����ͳһ�ģ�
�����ģ�ɿ�������ޡ��������ӣ��ƻ������Ž�ģ�
�����壩�������������ʵ��ɢ��ҥ�ԣ������������ģ�
������������⽨���š����ࡢɫ�顢�Ĳ�����������ɱ���ֲ�����������ģ�
�����ߣ���Ȼ�������˻���������ʵ�̰����˵ģ����߽����������⹥���ģ�
�����ˣ��𺦹��һ��������ģ�
�����ţ�����Υ���ܷ��ͷ�����������ģ�
����ʮ��������ҵ�����Ϊ�ġ�

�����������أ����Լ������ۺ���Ϊ����
������ֹ�������û�ʱʹ����ر�վ�Ĵʻ㣬���Ǵ������衢�ٰ�����ҥ��Ļ������京��ĸ������Խ���ע���û����������ǻὫ��ɾ����
�ġ���ֹ���κη�ʽ�Ա�վ���и����ƻ���Ϊ��
�塢�������Υ��������ط��ɷ������Ϊ����վ�Ų��������ĵ�¼��̳��Ϣ������¼���ɣ���Ҫʱ�����ǻ�����صĹ��ҹ������ṩ������Ϣ�� 
</pre>
</TD></TR></TBODY></TABLE></DIV>

<DIV style="MARGIN-BOTTOM: 25px; TEXT-ALIGN: center">
<INPUT type=button onClick=javascript:location.href="register.jsp" value="ͬ ��"> 
<INPUT onclick=javascript:history.go(-1); type=button value="ȡ ��">
</DIV>
  <%
    }
  %>
  
</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>