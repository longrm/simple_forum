<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.User, java.sql.Date"%>
<%@page import="org.longrm.forum.util.Constant"%>
<%
  User user = (User) session.getAttribute("SESSION_USER");
  boolean logined = (user!=null);
%>
<!-- 菜单 -->
<div id="header">

<div id="guide" class="guide">
<span class="s3">&raquo;</span>
  <%
    if (!logined) {
  %>
  您尚未　<a href="login.jsp">登录</a> &nbsp;<a href="registerdeclare.jsp"><b>注册</b></a>
  <%
    } else {
  %>
  <span style="font-weight:bold" class="s3"><%=user.getName()%></span>
  &nbsp;<a href="logout.do">退出</a> 
	| <a id="profile" href="profile.jsp" onmouseover="showMenu(this.id)" onmouseout="hideMenu(this.id)">控制面板<img src="images/menu-down.gif"/></a>
	| <a id="message" href="message.jsp" onmouseover="showMenu(this.id)" onmouseout="hideMenu(this.id)">短消息<img src="images/menu-down.gif" /></a>
	| <a href="member.jsp">会员</a>
	<%
	  if(!user.getSys_role_id().equals(Constant.GENERAL_USER)) {
	%>
	| <a href="admin/index.jsp" target="_blank">系统设置</a>
	<%
	  }
	%>
	| <a id="sort" href="sort.jsp" onmouseover="showMenu(this.id)" onmouseout="hideMenu(this.id)">统计排行<img src="images/menu-down.gif" /></a>
  <%
    }
  %>
    | <a href="tag.jsp">标签</a>
	| <a href="show.jsp">展区</a>
	| <a href="search.jsp">搜索</a>
	| <a href="blog.jsp">博客</a>
	| <a href="help.jsp">帮助</a>
	| <a href="search.do?date_start=<%=new Date(System.currentTimeMillis())%>" target="_blank">订阅本站更新</a>
</div>

<div id="menu_message" class="menu" onmouseover="showMenu('message')" onmouseout="hideMenu('message')" style="display:none;">
<table width="120" cellspacing="1" cellpadding="5">
	<tr><th class="h">短消息</th></tr>
	<tr><td class="f_one"><a href="message.jsp?action=receive">收件箱</a></td></tr>
	<tr><td class="f_one"><a href="message.jsp?action=send">发件箱</a></td></tr>
	<tr><td class="f_one"><a href="message.jsp?action=write">写新消息</a></td></tr>
</table>
</div>
<div id="menu_profile" class="menu" onmouseover="showMenu('profile')" onmouseout="hideMenu('profile')" style="display:none;">
<table width="120" cellspacing="1" cellpadding="5">
	<tr><th class="h">控制面板</th></tr>
	<tr><td><a href="profile.jsp">控制面板首页</a></td></tr>
	<tr><td><a href="profile.jsp?action=edit_information">编辑个人资料</a></td></tr>
	<tr><td><a href="profile.jsp?action=view_information">查看个人资料</a></td></tr>
	<tr><td><a href="profile.jsp?action=friend_list">好友列表</a></td></tr>
	<tr><td><a href="profile.jsp?action=view_authorization">用户权限查看</a></td></tr>
	<tr><td><a href="profile.jsp?action=favorite">收藏夹</a></td></tr>
	<tr><td><a href="search.do?poster=<%=logined?user.getName():""%>">我的主题</a></td></tr>
</table>
</div>
<div id="menu_sort" class="menu" onmouseover="showMenu('sort')" onmouseout="hideMenu('sort')" style="display:none;">
	<table width="120" cellspacing="1" cellpadding="5">
	<tr><th class="h">统计排行</th></tr>
	<tr><td class="f_one"><a href="sort.jsp">基本统计信息</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">到访IP统计</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">管理团队</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">管理统计</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">在线统计</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">会员排行</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">版块排行</a></td></tr>
	<tr><td class="f_one"><a href="sort.jsp?">帖子排行</a></td></tr>
</table>
</div>

</div>
