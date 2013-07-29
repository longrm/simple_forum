<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.User, java.sql.Date"%>
<%@page import="org.longrm.forum.util.Constant"%>
<%
  User user = (User) session.getAttribute("SESSION_USER");
  boolean logined = (user!=null);
%>
<!-- �˵� -->
<div id="header">

<div id="guide" class="guide">
<span class="s3">&raquo;</span>
  <%
    if (!logined) {
  %>
  ����δ��<a href="login.jsp">��¼</a> &nbsp;<a href="registerdeclare.jsp"><b>ע��</b></a>
  <%
    } else {
  %>
  <span style="font-weight:bold" class="s3"><%=user.getName()%></span>
  &nbsp;<a href="logout.do">�˳�</a> 
	| <a id="profile" href="profile.jsp" onmouseover="showMenu(this.id)" onmouseout="hideMenu(this.id)">�������<img src="images/menu-down.gif"/></a>
	| <a id="message" href="message.jsp" onmouseover="showMenu(this.id)" onmouseout="hideMenu(this.id)">����Ϣ<img src="images/menu-down.gif" /></a>
	| <a href="member.jsp">��Ա</a>
	<%
	  if(!user.getSys_role_id().equals(Constant.GENERAL_USER)) {
	%>
	| <a href="admin/index.jsp" target="_blank">ϵͳ����</a>
	<%
	  }
	%>
	| <a id="sort" href="sort.jsp" onmouseover="showMenu(this.id)" onmouseout="hideMenu(this.id)">ͳ������<img src="images/menu-down.gif" /></a>
  <%
    }
  %>
    | <a href="tag.jsp">��ǩ</a>
	| <a href="show.jsp">չ��</a>
	| <a href="search.jsp">����</a>
	| <a href="blog.jsp">����</a>
	| <a href="help.jsp">����</a>
	| <a href="search.do?date_start=<%=new Date(System.currentTimeMillis())%>" target="_blank">���ı�վ����</a>
</div>

<div id="menu_message" class="menu" onmouseover="showMenu('message')" onmouseout="hideMenu('message')" style="display:none;">
<table width="120" cellspacing="1" cellpadding="5">
	<tr><th class="h">����Ϣ</th></tr>
	<tr><td class="f_one"><a href="message.jsp?action=receive">�ռ���</a></td></tr>
	<tr><td class="f_one"><a href="message.jsp?action=send">������</a></td></tr>
	<tr><td class="f_one"><a href="message.jsp?action=write">д����Ϣ</a></td></tr>
</table>
</div>
<div id="menu_profile" class="menu" onmouseover="showMenu('profile')" onmouseout="hideMenu('profile')" style="display:none;">
<table width="120" cellspacing="1" cellpadding="5">
	<tr><th class="h">�������</th></tr>
	<tr><td><a href="profile.jsp">���������ҳ</a></td></tr>
	<tr><td><a href="profile.jsp?action=edit_information">�༭��������</a></td></tr>
	<tr><td><a href="profile.jsp?action=view_information">�鿴��������</a></td></tr>
	<tr><td><a href="profile.jsp?action=friend_list">�����б�</a></td></tr>
	<tr><td><a href="profile.jsp?action=view_authorization">�û�Ȩ�޲鿴</a></td></tr>
	<tr><td><a href="profile.jsp?action=favorite">�ղؼ�</a></td></tr>
	<tr><td><a href="search.do?poster=<%=logined?user.getName():""%>">�ҵ�����</a></td></tr>
</table>
</div>
<div id="menu_sort" class="menu" onmouseover="showMenu('sort')" onmouseout="hideMenu('sort')" style="display:none;">
	<table width="120" cellspacing="1" cellpadding="5">
	<tr><th class="h">ͳ������</th></tr>
	<tr><td class="f_one"><a href="sort.jsp">����ͳ����Ϣ</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">����IPͳ��</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">�����Ŷ�</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">����ͳ��</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">����ͳ��</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">��Ա����</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">�������</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">��������</a></td></tr>
</table>
</div>

</div>
