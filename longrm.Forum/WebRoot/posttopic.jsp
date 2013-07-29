<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="java.util.*, org.longrm.forum.bean.*"%>
<html>
<head>
<%@include file="include/head.jsp"%>
<link rel="stylesheet" type="text/css" href="css/default.css" />
<script src="js/common.js" type="text/javascript"></script>
<script src="js/topic.js" type="text/javascript"></script>

</head>
<body onLoad="javascript:document.topicForm.title.focus();">

<div id="header"><%@include file="include/top.jsp"%></div>
<div id="menu"><%@include file="include/menu.jsp"%></div>

<DIV id=main>

<%
	Forum forum = (Forum)request.getAttribute("forum");
	String action = (String)request.getAttribute("action");
	Topic topic = null;
	ArrayList fileList = null;
	String topic_id = (String)request.getAttribute("topic_id");
	String retopic_id = (String)request.getAttribute("retopic_id");
	String title = (String)request.getAttribute("title");
	String content = (String)request.getAttribute("content");
	String masterRight = (String) request.getAttribute("master_right");
	if( action!=null && (action.equals("reply") || action.equals("edit_topic")) ) {
		topic = (Topic)request.getAttribute("topic");
		topic_id = topic.getId();
	}
	// ���ϴ��ļ�
	if( action!=null && (action.equals("edit_topic") || action.equals("edit_retopic")) )
		fileList = (ArrayList) request.getAttribute("file_list");
%>

<DIV class=t3>
<TABLE cellSpacing=0 cellPadding=0 width="100%" align=center>
  <TR>
    <TD align=left><IMG src="images/home.gif" align=absBottom> 
	<B><A href="index.jsp">javaedge����</A>
		&raquo;<A href="viewforum.jsp?forum_id=<%=forum.getId()%>"><%=forum.getName()%></A>
	<%	if( action!=null && (action.equals("reply")||action.equals("edit_topic")) ) {	%>
		&raquo;<A href="viewtopic.jsp?topic_id=<%=topic.getId()%>"><%=topic.getTitle()%></A>
	<%	} %>
	</B></TD>
  </TR>
</TABLE>
</DIV>

<DIV style="MARGIN-TOP: 15px"></DIV>

<form name="topicForm" method="post" action="topicpost.do">
<div class="t f_one">
<table cellPadding="0" cellSpacing="0" align="center">
	<tr><th class="h" colspan="3"><b>��������</b></th></tr>
	<tr class="f_one tal">
		<th colspan="3">
		<div style="width:700px;margin:.5em auto">
		<span class="smalltxt gray">
		��������⣺
		</span>
		<input type="text" style="border:0;font:14px/20px Tahoma;width:415px;" name="title" value="<%=title%>" />
		</div>
		</th>
	</tr>
		
	<tr>
		<td valign="top" class="f_one" style="padding:7px">
		<b>����</b><br /><br />
		<font face="verdana">  HTML �������</font>
		<br />
		<input type="checkbox" name="atc_anonymous" value="1" disabled />������
		<br />
		<input type="checkbox" name="atc_hide" value="1" disabled />���ظ���
		<br /><br />
		</td>
		
		<td class="f_one" style="padding:7px">
		<div style="width:600px;margin:.5em auto">
		
		<textarea onKeyDown="quickpost2(event)" name="content" style="border:0px;height:300px;width:600px;"><%=content%></textarea>
		<input type="hidden" value="<%=forum.getId()%>" name="forum_id" />
		<input type="hidden" value="<%=action%>" name="action" />
		<input type="hidden" value="<%=topic_id%>" name="topic_id" />
	<%	if(action!=null && action.equals("edit_retopic")) { %>
		<input type="hidden" value="<%=retopic_id%>" name="retopic_id" />
	<%	} %>
	
		<DIV style="MARGIN-TOP: 5px">
		<input type="checkbox" name="atc_usesign" value="1" checked disabled />ʹ��ǩ��
		<input type="checkbox" name="atc_convert" value="1" unchecked disabled />Code�Զ�ת��
		<a class="abtn" onClick="checklength('50000');">�������</a>
		<a class="abtn" onClick="loadData('msg');">�ָ�����</a>
		<a class="abtn" onClick="savedraft();">��Ϊ�ݸ�</a>
		<a class="abtn" id="newdraft" onClick="opendraft(this.id);">�ݸ���</a>
		</div>
		<DIV style="MARGIN-TOP: 5px"></DIV>
		</td>
		
		<td class="f_one">
		<div style="text-align:center;margin-left:6px; width:200px;">
		<fieldset id="smiliebox" style="border:1px solid #e6e6e6">
			<legend>����</legend>
			<div id="menu_show"></div>
			<span style="float:right; margin:3px 10px 5px;">[����]</span>
			<span style="float:left; margin:3px 10px 5px;">[���Ա���]</span>
		</fieldset>
		</div>
		<div id="menu_generalface" class="menu" style="display:none;"></div>
		<div id="menu_face" class="menu" style="display:none;"></div>
		</td>
	</tr>
</table>
</div>

<%	if(fileList!=null && fileList.size()>0) {	%>
<div class="wysiwyg" style="padding:0x;border:1px solid #DDDDDD;margin-bottom:5px;">
<div style="MARGIN-TOP: 10px"></div>
<table width="100%">
<tr>
	<td width="20%"></td>
	<td width="30%"><font color="teal"><b>ɾ���ϴ�������</b></font></td>
	<td></td>
</tr>

<%	for(int i=0; i<fileList.size(); i++)	{
		if(i%2==0) {	%>
<tr><td width="20%"></td>
	<%}	BasicFile file = (BasicFile)fileList.get(i);	%>
	<td><input type="checkbox" name="file_delete_list" value="<%=file.getId()%>" /><font color="teal"><%=file.getFilename()%></font></td>
	<%	if(i%2==1) {	%>
</tr>
	<%	}
	}	%>
</table>
</div>
<%	}	%>

<%	if(action==null || action.equals("new")) {	%>
<div class="wysiwyg" style="padding:0x;border:1px solid #DDDDDD;margin-bottom:5px;">
<table cellPadding="0" cellSpacing="0" align="center">
	<tr>
	<td width="250px">&nbsp;</td>
	<td class="f_one" style="padding:7px">	
		<DIV style="MARGIN-TOP: 5px">
		<span>
		
		����Ȩ�ޣ�<input type="text" size="5" maxlength="3" name="authority"/>
		<span class="smalltxt gray">��ֵ���ܳ�������Ȩ��ֵ</span>
		<%	if(masterRight.equals("Y")) {	%>
		<input name="soul" type="radio" value="0" checked />��ͨ��
		<input name="soul" type="radio" value="1" />����1
		<input name="soul" type="radio" value="2" />����2
		<br><br>
		��ɫ��<input name="color" type="text" value="black" size="10" />
		<input type="checkbox" name="bold" value="Y" />����
		<input name="top" type="radio" value="0" checked />��ͨ��
		<input name="top" type="radio" value="1" />�ö�1
		<input name="top" type="radio" value="2" />�ö�2
		<input name="top" type="radio" value="3" />�ö�3
		<%	}	%>
		</span>
		</DIV>
	</td>
	</tr>
</table>
</div>
<%	}	%>
</form>

<form action="fileupload.do" name="fileForm" encType="multipart/form-data"  method="post" target="hidden_frame" >
<input type="hidden" value="<%=action%>" name="action" />
<input type="hidden" value="<%=topic_id%>" name="topic_id" />
<input type="hidden" value="<%=retopic_id%>" name="retopic_id" />
<div class="wysiwyg" style="padding:0x;border:1px solid #DDDDDD;margin-bottom:5px;">
<table width="100%">
<tr>
	<td width="20%"></td>
	<td>
	<DIV style="MARGIN-TOP: 10px"></DIV>
	<b>������</b>
	<a class="abtn" onClick="addFile('fileTable');">��� +</a>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span id="msg"></span>&nbsp;&nbsp;
	<img src="images/loading.gif" style="display:none" name="loading" />
	<DIV style="MARGIN-TOP: 10px"></DIV>
	<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>
	</td>
</tr>
<tr>
	<td width="20%"></td>
	<td>
		<table id="fileTable">
		</table>
	</td>
</tr>
</table>
</div>
</form>

<table>
<tr>
	<td width="60%"></td>
  <td>
  	<br>
		<div style="padding:4px 10px 0 0;float:left;color:#FF0000">�� Ctrl+Enter ֱ���ύ</div>
		<input class="btn" type="button" id="postBut" onclick="topic_file_post();" value=" �� �� " />
	</td>
</tr>
</table>

</DIV>

<%@include file="include/bottom.jsp"%>
</body>
</html>