<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.core.*, java.util.*"%>
<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
<script src="js/dateselect.js" type="text/javascript"></script>
</head>
<body onLoad="javascript:document.searchForm.title.focus();">

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge����</A>&raquo; ����</B></TD>
  </TR>
</TABLE>
</DIV>

<form name="searchForm" action="search.do" method="post" onSubmit="this.submit.disabled=true;">
<input type="hidden" name="step" value="2" />
<div class="t" style="margin-top:15px;">
<table width="100%" cellspacing="0" cellpadding="0">
<tr><td class="h" colspan="2">����Ŀ��</td></tr>
<tr class="tr2"><td colspan="2">&nbsp;</td></tr>
<tr class="tr3 f_one">
<th width="50%">����:
<br /><input class="input" type="text" name="title" size="30" />
<br /><input type="radio" name="method" value="OR" checked />����ƥ��
<input type="radio" name="method" value="AND" />��ȫƥ��(2���ֽ�����,��ؼ����� | �ֿ�)</th>
<th>���û�������:<br />
<input class="input" type="text" name="poster" size="15" /><br />�û����п�ʹ��ͨ��� %���� ���� admi% ƥ�� admin
</th></tr>
<tr class="tr3 s5 f_one"><td colspan="2" style="border:0">�������ӷ�Χ</td></tr>
<tr class="tr3 f_one">
<th colspan="2">
<input type="radio" name="area" value="0" checked />�������
<input type="radio" name="area" value="1"  />�����������������
<input type="radio" name="area" value="2"  />�ظ�������ظ�����
<input type="checkbox" name="soul" value="Y" />��������־
</th>
</tr>
<tr class="tr3 s5 f_one"><td colspan="2" style="border:0">�������ѡ��</td></tr>

<tr class="tr3 f_one">
<th>
���Ұ�鷶Χ��
<select name="forum_id">
<option value="">* ���з���</option>
<%
  ArrayList<Forum> forumList = ForumService.getForumList();
  if(forumList!=null) {
  	for(int i=0; i<forumList.size(); i++) {
    	Forum forum = forumList.get(i);
    	ArrayList<Forum> subForumList = forum.getSubForumList();
%>
		<option value="<%=forum.getId()%>">>> <%=forum.getName()%></option>
	<%
		for(int j=0; j<subForumList.size(); j++) {
			Forum subForum = subForumList.get(j);
	%>
			<option value="<%=subForum.getId()%>"> &nbsp;|- <%=subForum.getName()%></option>
<%
		}
	}
  }
%>
</select>
</th>

<th valign="top">����ʱ�䣺
��
<input class="input" type="text" size="10" maxlength="10" name="date_start" onFocus="setday(this)"/>
��
<input class="input" type="text" size="10" maxlength="10" name="date_end" onFocus="setday(this)"/>
</th>
</tr>

<tr class="tr3 s5 f_one"><td colspan="2" style="border:0">�������:</td></tr>
<tr class="tr3 f_one"><th colspan="2">
<select name="order_by">
<option value="last_time">���ظ�ʱ��</option>
<option value="post_time" >����ʱ��</option>
<option value="re_topics">�ظ�</option>
<option value="click">����</option>
</select>
<input type="radio" name="order" value="ASC" checked />����
<input type="radio" name="order" value="DESC" checked />����
</th>
</tr>
</table>
</div>
<div style="margin-bottom:25px;text-align:center;">
<input class="btn" type="submit" name="submit" value="�� ��" />
<input class="btn" type="reset" value="�� ��" />
</div>
</form>
  
</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>