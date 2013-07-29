<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.User, java.util.*"%>
<%@page import="org.longrm.forum.util.Constant"%>

<%
	Vector activeSessions = (Vector) application.getAttribute("activeSessions");
	if (activeSessions == null) {
		activeSessions = new Vector();
		application.setAttribute("activeSessions",activeSessions);
	}
	int userCount = activeSessions.size();
	Iterator it = activeSessions.iterator();
 %>
 
<tr class="tr4">
	<td colspan="2">&raquo; 在线用户<a name="online"></a> -  共 <%=userCount%> 人在线</td>
</tr>

<tr class="tr3">
	<td class="f_two tac" style="padding:0" width="4%"><img src="images/index/online.gif" /></td>
	<td class="f_one tal" style="padding:15px 20px">
	
	<div>
		<img src="images/group/3.gif" align="absmiddle" /> 管理员&nbsp; &nbsp; 	

		<img src="images/group/4.gif" align="absmiddle" /> 总版主&nbsp; &nbsp; 	

		<img src="images/group/5.gif" align="absmiddle" /> 论坛版主&nbsp; &nbsp; 	

		<img src="images/group/16.gif" align="absmiddle" /> 荣誉会员&nbsp; &nbsp; 	

		<img src="images/group/6.gif" align="absmiddle" /> 普通会员

		<a name="online"></a>
	</div>
	
	<div style="padding:5px 0"><hr class="hr" color="#D3EAF0" size="1"></div>

	<div>	
	<table align="center" cellspacing="0" cellpadding="0" width="99%">
	
<%
  for(int i=0; i<userCount; i++) {
    HttpSession sess = (HttpSession)it.next();
    User tmpUser = (User)sess.getAttribute("SESSION_USER");
    String sys_role_id = tmpUser.getSys_role_id();
    int kind=6;
    if(sys_role_id.equals(Constant.ADMIN)) kind=3;
    else if(sys_role_id.equals(Constant.SUPER_MASTER)) kind=4;
    else if(sys_role_id.equals(Constant.SINGLE_MASTER)) kind=5;
    else if(sys_role_id.equals(Constant.HONOR_USER)) kind=16;
    else if(sys_role_id.equals(Constant.GENERAL_USER)) kind=6;
    
    if(i%10==0) {
 %>
	<tr>
<%  } %>
	<td style="border:0;width:14%">
	<img src='images/group/<%=kind%>.gif' align='bottom'>
	<a href="profile.jsp?action=view_information&name=<%=tmpUser.getName()%>"><%=tmpUser.getName()%></a>
	</td>
<%
	if(i%10==9||i==userCount) {
%>
	</tr>
<%  } %>

<%}%>
	
	</table>
	</div>
	
	</td>
</tr>
