<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*"%>

<%
	Message message = (Message)request.getAttribute("message");
%>

<div class="t" style="width:50%">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr><td class="h" colspan="2">�鿴��Ϣ</td></tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">����:</font></th>
		<th><%=message.getSender()%></th>
	</tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">������:</font></th>
		<th><%=message.getReceiver()%></th>
	</tr>
	<tr class="tr3">
	<th width="12%"><font color="#5A6633">����:</font></th>
		<th><%=message.getTitle()%></th>
	</tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">ʱ��:</font></th>
		<th><%=message.getTime().toString().substring(0, 16)%></th>
	</tr>
	<tr class="tr3">
		<th width="12%"><font color="#5A6633">����:</font></th>
		<th><%=message.getContent()%></th>
	</tr>
	<tr class="tr3">
		<th><font color="#5A6633">ѡ��:</font></th>
		<th><font color="#5A6633">
			[<a href="deletemessage.do?message_id[]=<%=message.getId()%>&box=<%=request.getParameter("box")%>">ɾ��</a>]
			[<a href="javascript:history.go(-1);">����</a>]
			</font>
		</th>
	</tr>
</table>
</div>