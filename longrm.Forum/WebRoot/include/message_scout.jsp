<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*"%>

<%
	ArrayList<Message> messageList = (ArrayList<Message>)request.getAttribute("message_list");
%>

<form name="messageForm" action="deletemessage.do" method="post" onSubmit="return submitcheck(this);">
<input type="hidden" name="box" value="send">
<div class="t">
	<table width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="h" style="text-align:center;width:6%">ID</td>
		<td class="h" style="text-align:center;width:40%">����</td>
		<td class="h" style="text-align:center;width:10%">������</td>
		<td class="h" style="text-align:center;width:10%">�ռ���</td>
		<td class="h" style="text-align:center;width:20%">ʱ��</td>
		<td class="h" style="text-align:center;width:5%">�Ѷ�</td>
		<td class="h" style="text-align:center;width:5%">ѡ��</td>
	</tr>
	<tr class="tr2">
		<td colspan="7" style="border-top-color:#fff">
			<b>������Ϣ</b>
		</td>
	</tr>
<%
	for(int i=0; i<messageList.size(); i++) {
		Message message = messageList.get(i);
%>
	<tr class="tr3">
		<td class="tac"><%=i+1%></td>
		<td class="tac"><a href="message.jsp?action=read&message_id=<%=message.getId()%>&box=receive"><%=message.getTitle()%></a></td>
		<td class="tac"><a href="profile.jsp?action=view_information&name=<%=message.getSender()%>"><%=message.getSender()%></a></td>
		<td class="tac"><a href="profile.jsp?action=view_information&name=<%=message.getReceiver()%>"><%=message.getReceiver()%></a></td>
		<td class="tac"><%=message.getTime().toString().substring(0, 16)%></td>
		<td class="tac"><%=message.getIsread()%></td>
		<td class="tac"><input type="checkbox" name="message_id[]" value="<%=message.getId()%>" /></td>
	</tr>
<%	}	%>
	</table>
</div>
<center>
	<input class="btn" type="button" name="chkall" value="ȫѡ" onclick="CheckAll(this.form)" />
	<input class="btn" type="submit" value="�ύ" />
	<input name="towhere" type="hidden" value="receivebox" />
	<input name="action" type="radio" value="del" checked />ɾ��
</center>
</form>