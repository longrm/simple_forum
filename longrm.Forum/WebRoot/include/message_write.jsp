<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*"%>

<form name="messageForm" action="sendmessage.do" method="post">
<input type="hidden" name="action" value="write" />
<input type="hidden" name="friends" id="friends" value="">
<div class="t">
<table width="100%" cellspacing="0" cellpadding="0">
	<tr><td class="h" colspan="2"><b>����Ϣ</b></td></tr>
	<tr class="tr3">
		<th width="20%"><b>�û���</b>�����û�֮����";"�Ÿ�����</th>
		<th><input class="input" type="text" name="receivers" maxlength="100" size="73" /></th>
	</tr>
	<tr class="tr3">
		<th width="20%">
			<b>������ϢȺ��</b>
		</th>
		<th>
		<%
			ArrayList<Friend> friendList = (ArrayList<Friend>)request.getAttribute("friend_list");
			for(int i=0; i<friendList.size(); i++) {
				String friendname = friendList.get(i).getFriend();
		%>
			<input type="checkbox" name="friendarray[]" value="<%=friendname%>" /><%=friendname%>
		<%	}	%>
			<br />
			<input class="btn" type="button" name="chkall" value="ȫ ѡ" onclick="CheckAll(this.form)" />
		</th>
	</tr>
	<tr class="tr3">
		<th><b>����</b></th>
		<th><input class="input" type="text" name="title" maxlength="100" size="73" /></th>
	</tr>
	<tr class="tr3">
		<th valign="top"><b>����</b>
		<br /><br />
		<div style="padding:3px;text-align:center;width:200px;">
		<fieldset id="smiliebox" style="border:1px solid #e6e6e6">
			<legend>����</legend>
			<div id="menu_show"></div>
			<span style="float:right; margin:3px 10px 5px;">
			<a id="td_face" style="cursor:pointer;" onClick="" align="absmiddle">[����]</a>
			</span>
		</fieldset>
		</div>
		<th>
		<textarea name="content" cols="100" rows="20" style="overflow:auto;" onkeydown="quickpost(event)"></textarea>
		<div style="margin:4px 0 2px">
		<input type="checkbox" name="issave" value="Y" />���浽��������
		<a class="abtn" onClick="checklength('2000');">�������</a>
		</div>
		</th>
	</tr>
</table>
</div>
<table>
<tr>
	<td width="60%"></td>
  <td>
  	<br>
		<div style="padding:4px 10px 0 0;float:left;color:#FF0000">�� Ctrl+Enter ֱ���ύ</div>
		<input class="btn" type="button" id="postBut" onclick="message_post();" value=" �� �� " />
	</td>
</tr>
</table>
</form>
