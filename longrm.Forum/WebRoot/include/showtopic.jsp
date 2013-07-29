<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, org.longrm.forum.util.*, java.util.*"%>

<script language="javascript">
function topicManage(act) {
	document.topicmanageForm.action.value=act;
	document.topicmanageForm.submit();
}
</script>

<%
    String URL = request.getRequestURL().toString();
	String fullURL = URL + "?topic_id=" + topic.getId() + "&page_index=" + pageIndex;
%>

<div class="t" style="margin-bottom:0px; border-bottom:0">
<table cellspacing="0" cellpadding="0" width="100%">
	<tr>
	<th class="h"><b>本页主题:</b>&nbsp;<%=topic.getTitle()%></th>
	<td class="h" style="text-align:right;">
	<span>
	<a class="fn" href=""> 打印</a>
	| <a class="fn" style="cursor:pointer;" onclick="Addtoie('<%=fullURL%>','javaedge社区--<%=topic.getTitle()%>');"> 加为IE收藏</a> 
	| <a style="cursor:pointer;" title="<%=fullURL%>" onclick="clipboardData.setData('Text','<%=fullURL%>');alert('帖子链接已复制到剪贴板!')">复制链接</a> 
	| <a class="fn" style="cursor:pointer;" onclick="redirect('favouritemanage.do?action=add&topic_id=<%=topic.getId()%>')" id="favor">收藏主题</a> 
	| <a href="" class="fn">上一主题</a>
	| <a href="" class="fn">下一主题</a>
	</span>
	</td>
	</tr>

<%	if(masterRight.equals("Y")) {	%>
	<tr class="tr2">
	<td colspan="2" class="tar">
		<form action="managetopicoption.jsp" method="post" name="topicmanageForm">
		<input type="hidden" name="forum_id" value="<%=forum.getId()%>">
		<input type="hidden" name="forum_name" value="<%=forum.getName()%>">
		<input type="hidden" name="topic_id[]" value="<%=topic.getId()%>">
		<input type="hidden" name="action" value="">
		<input type="hidden" name="url" value="">
		<a style="cursor:pointer" onclick="topicManage('top')" title="置顶帖子">[置顶]</a> 
		<a style="cursor:pointer" onclick="topicManage('soul')" title="精华帖子">[精华]</a> 
		<a style="cursor:pointer" onclick="topicManage('light')" title="编辑标题颜色">[加亮]</a> 
		<a style="cursor:pointer" onclick="topicManage('open')" title="锁定帖子">[打开]</a> 
		<a style="cursor:pointer" onclick="topicManage('lock')" title="锁定帖子">[锁定]</a> 
		<a style="cursor:pointer" onclick="topicManage('close')" title="关闭帖子">[关闭]</a> 
		<a style="cursor:pointer" onclick="topicManage('push')" title="提前帖子">[提前]</a> 
		<a style="cursor:pointer" onclick="topicManage('down')" title="将帖子往后压">[压帖]</a>
		<a style="cursor:pointer" onclick="topicManage('move')" title="移动帖子">[移动]</a> 
		<a style="cursor:pointer" onclick="topicManage('delete')" title="删除帖子">[删除]</a> 
		</form>
	</td>
	</tr>
<%	}	%>
</table>
</div>

<%
	ArrayList<TopicFile> topicfileList = (ArrayList<TopicFile>) request.getAttribute("topicfile_list");
	ArrayList<Retopic> retopicList = (ArrayList<Retopic>) request.getAttribute("retopic_list");
	HashMap retopicFileMap = (HashMap) request.getAttribute("retopicfile_map");
	User author = null;
	UserInfo author_info = null;
	String rolename = null;
	int kind = 0;
	if(pageIndex==1) {
		author = topic.getAuthor();
		author_info = topic.getAuthor_info();
		String sys_role_id = author.getSys_role_id(); 
		if(author.getStatus()==-1)
			kind = 0;
		else if(sys_role_id.equals(Constant.GENERAL_USER)) {
			rolename = ((Role)roleMap.get(author.getRole_id())).getName();
			kind = Integer.parseInt(author.getRole_id());
		}
		else {
			rolename = ((SysRole)sysRoleMap.get(sys_role_id)).getName();
			if(sys_role_id.equals(Constant.ADMIN))
				kind = 999;
			else if(sys_role_id.equals(Constant.SUPER_MASTER))
				kind = 99;
			else if(sys_role_id.equals(Constant.SINGLE_MASTER))
				kind = 9;
		}
%>

<div class="t t2" style="border-top:0">
<table cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;border-top:0">
	<tr class="tr1">
	<th style="width:20%" rowspan="2" class="r_two">
	<b><%=author.getName()%></b>
	<div style="margin:.3em 0 .4em .2em"><%=author.getSelf_sign()==null?"":author.getSelf_sign()%></div>

	<div class="user-pic">
	<table style="border:0">
	<tr><td width="1">
	<img class="pic" src="<%=author.getHead()%>" border="0" onload="DrawImage(this,'220','500')" />
	</td>
	<td style="vertical-align:top"><span id="sf_0"></span></td>
	</tr>
	</table>
	</div>
		
	级别: <font color="#555555"><%=rolename%></font><br />
	<img style="margin:.2em 0 .6em" src="images/level/<%=kind%>.gif" /><br />
	<img src="images/topic/profile.gif" title="查看作者资料" onclick="redirect('profile.jsp?action=view_information&user_id=<%=author.getId()%>')" style="cursor:pointer"/>
	<img src="images/topic/message.gif" title="发送短消息" onclick="" style="cursor:pointer" />
	<img onclick="redirect('friendmanage.do?action=add&friend=<%=author.getName()%>&url=<%=fullURL%>')" style="cursor:pointer" src="images/topic/friends.gif" title="加为好友" /><br />
	精华: <%=author_info.getSouls()%><br />
	主题: <%=author_info.getTopics()%><br />
	发帖: <%=author_info.getNotes()%><br />
	<%=author_info.getMk1_name()%>: <%=author_info.getMk1()%> <%=author_info.getMk1_unit()%><br />
	<%=author_info.getMk2_name()%>: <%=author_info.getMk2()%> <%=author_info.getMk2_unit()%><br />
	<%=author_info.getMk3_name()%>: <%=author_info.getMk3()%> <%=author_info.getMk3_unit()%><br /><br />
	</th>
		
	<th height="100%" class="r_one" valign="top" id="td_tpc" style="padding:5px 15px 0 15px; border:0;width:80%; overflow:hidden">
		<div class="tiptop">
			<span class="fr">
			<a style="cursor:pointer" onclick="fontsize('small','tpc')">小</a> 
			<a style="cursor:pointer" onclick="fontsize('middle','tpc')">中</a> 
			<a style="cursor:pointer" onclick="fontsize('big','tpc')">大</a>
			</span>
			<a onClick="redirect('posttopic.jsp?action=reply_quote_topic&forum_id=<%=forum.getId()%>&topic_id=<%=topic.getId()%>')" style="cursor:pointer">
				<img src="images/topic/quote.gif" title="引用回复这个帖子" />
			</a>
			<a style="cursor:pointer" id="recommend_tpc" onclick=""><img src="images/topic/emailto.gif" title="推荐此帖" /></a> 
			<a onClick="redirect('posttopic.jsp?action=edit_topic&forum_id=<%=forum.getId()%>&topic_id=<%=topic.getId()%>')" style="cursor:pointer">
				<img src="images/topic/edit.gif" />
			</a>
			<a onclick="" id="report_tpc" style="cursor:pointer"><img src="images/topic/report.gif" /></a>
			<a href="" title="只看楼主的所有帖子"><img src="images/topic/only.png" /></a>
		</div>
			
		<h1 class="fl"><%=topic.getTitle()%></h1>

		<div class="c"></div>

		<div class="tpc_content"><pre><%=topic.getContent()%></pre></div>
	</th>
	</tr>
	
	<tr class="tr1 r_one">
	<th style="vertical-align:bottom;padding-left:15px;border:0">
	
	<%
		for(int i=0; i<topicfileList.size(); i++) {
			TopicFile tf = topicfileList.get(i);
			String filename = tf.getFilename();
			String lowname = filename.toLowerCase();
			float size = (float)tf.getFilesize()/1000;
	%>
		<div style="margin:5px">
		<%	if(lowname.endsWith(".jpg") || lowname.endsWith(".gif") 
				|| lowname.endsWith(".jpeg") || lowname.endsWith(".bmp")) {	%>
			<%=filename%><br/>
			<img src="<%=request.getContextPath() + Constant.DEFAULT_UPLOAD + filename%>" 
			onload="DrawImage(this,'800','1500')" />
		</div>
			<%	continue;
			} %>
			
			附件： <img src="images/file/zip.gif" align="absbottom" /> 
			<a href="filedownload.do?filename=<%=filename%>&topic=Y">
			<font color="red"><%=filename%></font>
			</a> (<%=size%> K) 下载次数:<%=tf.getClick()%>
		</div>
	<%	}	%>

		<div><img src="images/topic/sigline.gif" align="absbottom" /></div>
		<div class="signature" style="max-height:px;maxHeight:px; overflow:hidden;">
		<table width="100%"><tr><td><%=author.getTopic_sign()==null?"":author.getTopic_sign()%><td></tr></table>
		</div>

		<div class="tipad">
		<span style="float:right">
	<%	if(masterRight.equals("Y")) {	%>
		<a id="showping_tpc" style="cursor:pointer" onclick=""><img src="images/topic/rate.gif" title="评分" /></a>
		<a id="banuser_tpc" style="cursor:pointer" onclick=""><img src="images/topic/postban.gif" title="禁言" /></a>
		<a id="shield_tpc" style="cursor:pointer" onclick=""> <img src="images/topic/shield.gif" title="屏蔽单帖" /></a>
		<a id="remind_tpc" style="cursor:pointer" onclick=""> <img src="images/topic/remind.gif" title="提醒管理" /></a>
	<%	}	%>
		<a href="javascript:scroll(0,0)"><img src="images/topic/top.gif" title="顶端" /></a> 
		</span>
		<span class="gray">Posted: <%=topic.getPost_time().toString().substring(0, 16)%> | IP: <%=topic.getPost_ip()%></span>

		<span>
		<a class="s3" title="回复此楼" style="cursor:pointer;" onclick="postreply('回 楼主(<%=author.getName()%>) 的帖子');">[楼 主]</a>
		</span>
		</div>
	</th>
	</tr>
	</table>
</div>

<%	}	%>

<form action="deleteretopic.do" method="post" name="retopicForm">
<input type="hidden" name="forum_id" value="<%=forum.getId()%>">
<input type="hidden" name="topic_id" value="<%=topic.getId()%>">
<%	
	for(int i=0; i<retopicList.size(); i++) {
		Retopic retopic = (Retopic)retopicList.get(i);
		ArrayList retopicfileList = (ArrayList)retopicFileMap.get(retopic.getId());
		int floor;
		if(pageIndex==1)
			floor = i+1;
		else
			floor = (pageIndex-1)*Constant.TOPIC_PAGESIZE+i;
		author = retopic.getAuthor();
		author_info = retopic.getAuthor_info();
		String sys_role_id = author.getSys_role_id();
		if(author.getStatus()==-1)
			kind = 0;
		else if(sys_role_id.equals(Constant.GENERAL_USER)) {
			rolename = ((Role)roleMap.get(author.getRole_id())).getName();
			kind = Integer.parseInt(author.getRole_id());
		}
		else {
			rolename = ((SysRole)sysRoleMap.get(sys_role_id)).getName();
			if(sys_role_id.equals(Constant.ADMIN))
				kind = 999;
			else if(sys_role_id.equals(Constant.SUPER_MASTER))
				kind = 99;
			else if(sys_role_id.equals(Constant.SINGLE_MASTER))
				kind = 9;
		}
%>

<div class="t t2" style="border-top:0">
<table cellspacing="0" cellpadding="0" width="100%" style="table-layout:fixed;border-top:0">
	<tr class="tr1">
	<th style="width:20%" rowspan="2" class="r_two">
	<b><%=author.getName()%></b>
	<div style="margin:.3em 0 .4em .2em"><%=author.getSelf_sign()==null?"":author.getSelf_sign()%></div>
	
	<div class="user-pic">
	<table style="border:0">
	<tr><td width="1">
	<img class="pic" src="<%=author.getHead()%>" border="0" onload="DrawImage(this,'220','500')"  /></a>
	</td>
	<td style="vertical-align:top"><span id="sf_0"></span></td>
	</tr>
	</table>
	</div>
		
	级别: <font color="#555555"><%=rolename%></font><br />
	<img style="margin:.2em 0 .6em" src="images/level/<%=kind%>.gif" /><br />
	<img src="images/topic/profile.gif" title="查看作者资料" onclick="redirect('profile.jsp?action=view_information&user_id=<%=author.getId()%>')" style="cursor:pointer"/>
	<img src="images/topic/message.gif" title="发送短消息" onclick="" style="cursor:pointer" />
	<img onclick="redirect('friendmanage.do?action=add&friend=<%=author.getName()%>&url=<%=fullURL%>')" style="cursor:pointer" src="images/topic/friends.gif" title="加为好友" /><br />
	精华: <%=author_info.getSouls()%><br />
	主题: <%=author_info.getTopics()%><br />
	发帖: <%=author_info.getNotes()%><br />
	<%=author_info.getMk1_name()%>: <%=author_info.getMk1()%> <%=author_info.getMk1_unit()%><br />
	<%=author_info.getMk2_name()%>: <%=author_info.getMk2()%> <%=author_info.getMk2_unit()%><br />
	<%=author_info.getMk3_name()%>: <%=author_info.getMk3()%> <%=author_info.getMk3_unit()%><br /><br />
	</th>
		
	<th height="100%" class="r_one" valign="top" id="td_tpc" style="padding:5px 15px 0 15px; border:0;width:80%; overflow:hidden">
		<div class="tiptop">
			<span class="fr">
			<a style="cursor:pointer" onclick="fontsize('small','tpc')">小</a> 
			<a style="cursor:pointer" onclick="fontsize('middle','tpc')">中</a> 
			<a style="cursor:pointer" onclick="fontsize('big','tpc')">大</a>
			</span>
			<a onClick="redirect('posttopic.jsp?action=reply_quote_retopic&forum_id=<%=forum.getId()%>&topic_id=<%=topic.getId()%>&retopic_id=<%=retopic.getId()%>&floor=<%=floor%>')" style="cursor:pointer">
				<img src="images/topic/quote.gif" title="引用回复这个帖子" />
			</a>
			<a style="cursor:pointer" id="recommend_tpc" onclick=""><img src="images/topic/emailto.gif" title="推荐此帖" /></a> 
			<a onClick="redirect('posttopic.jsp?action=edit_retopic&forum_id=<%=forum.getId()%>&topic_id=<%=topic.getId()%>&retopic_id=<%=retopic.getId()%>')" style="cursor:pointer">
				<img src="images/topic/edit.gif" />
			</a>
			<a onclick="" id="report_tpc" style="cursor:pointer"><img src="images/topic/report.gif" /></a>
			<a href="" title="只看楼主的所有帖子"><img src="images/topic/only.png" /></a>
		<%	if(masterRight.equals("Y")) {	%>
			&nbsp;<input type="checkbox" align="right" name="retopic_id[]" value="<%=retopic.getId()%>" title="删除选定回复" />
			<input class="btn" type="button" value="删除选定回复" onclick="deleteRetopic(this.form);" />
		<%	}	%>
		</div>
		
	<%	if(!retopic.getTitle().equalsIgnoreCase("Re:" + topic.getTitle())) {	%>
		<h1 id="subject_tpc" class="fl">
		<%=retopic.getTitle()%>
		</h1>
	<%	}	%>

		<div id="p_tpc" class="c"></div>

		<div class="tpc_content" id="read_tpc"><pre><%=retopic.getContent()%></pre></div>
	</th>
	</tr>
	
	<tr class="tr1 r_one">
	<th style="vertical-align:bottom;padding-left:15px;border:0">

	<%	for(int j=0; j<retopicfileList.size(); j++) {
			RetopicFile rtf = (RetopicFile)retopicfileList.get(j);
			float size = (float)rtf.getFilesize()/1000;
			String filename = rtf.getFilename();
			String lowname = filename.toLowerCase();
	%>
		<div style="margin:5px">
		<%	if(lowname.endsWith(".jpg") || lowname.endsWith(".gif") 
				|| lowname.endsWith(".jpeg") || lowname.endsWith(".bmp")) {	%>
			<%=filename%><br/>
			<img src="<%=request.getContextPath() + Constant.DEFAULT_UPLOAD + filename%>" 
			onload="DrawImage(this,'800','1500')" />
		</div>
			<%	continue;
			} %>

			附件： <img src="images/file/zip.gif" align="absbottom" /> 
			<a href="filedownload.do?filename=<%=filename%>">
			<font color="red"><%=filename%></font>
			</a> (<%=size%> K) 下载次数:<%=rtf.getClick()%>
		</div>
	<%	}	%>

		<div><img src="images/topic/sigline.gif" align="absbottom" /></div>
		<div class="signature" style="max-height:px;maxHeight:px; overflow:hidden;">
		<table width="100%"><tr><td><%=author.getTopic_sign()==null?"":author.getTopic_sign()%><td></tr></table>
		</div>

		<div class="tipad">
		<span style="float:right">
	<%	if(masterRight.equals("Y")) {	%>
		<a id="showping_tpc" style="cursor:pointer" onclick=""><img src="images/topic/rate.gif" title="评分" /></a>
		<a id="banuser_tpc" style="cursor:pointer" onclick=""><img src="images/topic/postban.gif" title="禁言" /></a>
		<a id="shield_tpc" style="cursor:pointer" onclick=""> <img src="images/topic/shield.gif" title="屏蔽单帖" /></a>
		<a id="remind_tpc" style="cursor:pointer" onclick=""> <img src="images/topic/remind.gif" title="提醒管理" /></a>
	<%	}	%>
		<a href="javascript:scroll(0,0)"><img src="images/topic/top.gif" title="顶端" /></a> 
		</span>
		<span class="gray">Posted: <%=retopic.getPost_time().toString().substring(0, 16)%> | IP: <%=retopic.getPost_ip()%></span>

		<span>
		<a class="s3" title="回复此楼" style="cursor:pointer;" onclick="postreply('回 <%=floor%> 楼(<%=author.getName()%>) 的帖子');">[<%=floor%> 楼]</a>
		</span>
		</div>
	</th>
	</tr>
	</table>
</div>
<%	}	%>
</form>
