<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.core.*, java.util.*"%>

<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
<script src="js/topic.js" type="text/javascript"></script>

</head>
<body>

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>
 
<DIV id=main>

<%
	RoleService rs = RoleService.getInstance();
	Map<String, Role> roleMap = rs.getRoleMap();
	Map<String, SysRole> sysRoleMap = rs.getSysRoleMap();
	Topic topic = (Topic)request.getAttribute("topic");
	int pageCount = topic.getPageCount();
	String pageIndexStr = request.getParameter("page_index");
	int pageIndex = pageIndexStr == null ? 1 : Integer.parseInt(pageIndexStr);
	Forum forum = (Forum) request.getAttribute("forum");
	String masterRight = (String) request.getAttribute("master_right");
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge社区</A>&raquo; 
	<a href="viewforum.jsp?forum_id=<%=forum.getId()%>"><%=forum.getName()%></a>&raquo; 
	<a href="viewtopic.jsp?topic_id=<%=topic.getId()%>"><%=topic.getTitle()%></a>
	</B>
	</TD>
  </TR>
</TABLE>
</DIV>

<div style="margin-top:15px;"></div>

<%@include file="include/topicnavigate.jsp"%>
<%@include file="include/showtopic.jsp"%>
<%@include file="include/topicnavigate.jsp"%>

<div style="margin-top:15px;"></div>

<%	if(logined && user.getStatus()>=0 && topic.getStatus()>=0 ) { %>

<form name="topicForm" method="post" action="topicpost.do" onSubmit="return checkpost(document.topicForm);">
<div class="t" style="margin-top:8px">
<table cellspacing="0" cellpadding="0" align="center" width="100%">
	<tr>
		<td class="h" colspan="2"><b>快速发帖</b></td>
		<td class="h" style="text-align:right"><a href="javascript:scroll(0,0)"><b>顶端</b></a></td>
	</tr>
	<tr class="tr2"><td colspan="100" style="border-bottom:0"></td></tr>
	<tr>
		<td valign="top" width="20%" class="f_one" style="padding:7px">
		<b>内容</b><br /><br />
		<font face="verdana">  HTML 代码可用</font>
		<br />
		<input type="checkbox" name="atc_anonymous" value="1" disabled />匿名帖
		<br />
		<input type="checkbox" name="atc_hide" value="1" disabled />隐藏附件
		<br /><br />
		</td>
		
		<td width="60%" class="f_one" style="padding:7px">
		<div>
		<input type="text" class="input" name="title" value="Re:<%=topic.getTitle()%>" size="50" />
		</div>
		
		<div>
		<input type="checkbox" name="atc_usesign" value="1" checked disabled />使用签名
		<input type="checkbox" name="atc_convert" value="1" unchecked disabled />Code自动转换
		<a class="abtn" onClick="javascript:checklength('50000');">字数检查</a>
		<a class="abtn" onClick="loadData('msg');">恢复数据</a>
		<a class="abtn" onClick="savedraft();">存为草稿</a>
		<a class="abtn" id="newdraft" onClick="opendraft(this.id);">草稿箱</a>
		</div>
		<textarea onKeyDown="quickpost(event)" name="content" rows="10" style="width:96%"></textarea>
		<input type="hidden" value="reply" name="action" />
		<input type="hidden" value="<%=forum.getId()%>" name="forum_id" />
		<input type="hidden" value="<%=topic.getId()%>" name="topic_id" />

		<div id="attach" style="margin-top:5px">
		<div style="margin:5px 0"></div>
		<div style="padding:4px 10px 0 0;float:left;color:#FF0000">按 Ctrl+Enter 直接提交</div>
		<input class="btn" type="submit" name="Submit" value=" 提 交 " />
		</div>
		</td>
		
		<td width="20%" class="f_one">
		<div style="padding:3px; text-align:center; width:200px;">
		<fieldset id="smiliebox" style="border:1px solid #D4EFF7">
			<legend>表情</legend>
			<div id="menu_show"></div>
			<span style="float:right; margin:3px 10px 5px;">[更多]</span>
			<span style="float:left; margin:3px 10px 5px;">[个性表情]</span>
		</fieldset>
		</div>
		<div id="menu_generalface" class="menu" style="display:none;"></div>
		<div id="menu_face" class="menu" style="display:none;"></div>
		</td>
	</tr>
</table>
</div>
</form>

<%	}	%>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>