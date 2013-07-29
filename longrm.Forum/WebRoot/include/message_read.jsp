<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*"%>

<%
	Message message = (Message)request.getAttribute("message");
%>

<div class="t" style="width:50%">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr><td class="h" colspan="2">查看信息</td></tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">作者:</font></th>
		<th><%=message.getSender()%></th>
	</tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">收信人:</font></th>
		<th><%=message.getReceiver()%></th>
	</tr>
	<tr class="tr3">
	<th width="12%"><font color="#5A6633">标题:</font></th>
		<th><%=message.getTitle()%></th>
	</tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">时间:</font></th>
		<th><%=message.getTime().toString().substring(0, 16)%></th>
	</tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">内容:</font></th>
		<th><%=message.getContent()%></th>
	</tr>
	<tr class="tr3">
		<th><font color="#5A6633">选项:</font></th>
		<th><font color="#5A6633">
			[<a href="deletemessage.do?message_id[]=<%=message.getId()%>&box=<%=request.getParameter("box")%>">删除</a>]
			[<a href="javascript:history.go(-1);">返回</a>]
			</font>
		</th>
	</tr>
</table>
</div>