<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*, org.longrm.forum.util.*"%>

<form name="editForm" action="editselfinfo.do" method="post" onSubmit="return editcheck(this)">
<div class="t"><table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><td class="h" colspan="2">会员核心数据</td></tr>

	<tr class="tr3 f_one">
		<th> 原密码</th>
		<th><input class="input" type="password" size="20" maxlength="50" name="oldpwd" value="" /> 修改 <font color=blue>密码</font> 或 <font color=blue>邮箱</font> 时需要密码验证</th>
	</tr>
	
	<tr class="tr3 f_two">
	<th> 新密码<br />英文字母或数字等不少于6位</th>
	<th style="vertical-align:middle">
	<input class="input" type="password" size="20" maxlength="50" name="password" id="password" onChange="checkpwd();" />
	&nbsp;<span id="pwd_info"></span>
	</th>
	</tr>

	<tr class="tr3 f_one">
	<th> 确认密码</th>
	<th><input class="input" type="password" size="20" maxlength="50" name="regpwdrepeat" id="regpwdrepeat" onChange="checkpwdrepeat();" />&nbsp;&nbsp;<span id="pwdrepeat_info"></span></th>
	</tr>
	
	<tr class="tr3 f_two">
		<th> Email</th>
		<th>
		<input class="input" type="text" size="20" value="<%=user.getEmail()%>" maxlength="75" name="email" id="email" onChange="checkemail();" />
		&nbsp;<input type="checkbox" name="ispublic" value="Y" <%=user.getIspublic()==null?"":"checked"%> />是否公开
		&nbsp;<span id="email_info"></span>
		</th>
	</tr>

	<tr class="tr3 f_one">
	<th>安全问题<br />如果启用了安全问题，需要填写正确的答案才能登录论坛</th>
	<th>
		<select name="question" onChange="showcustomquest(this.value)" style="width:150px">
			<option value="nochange">不修改安全问题和答案</option>
			<option value="">无安全问题</option>
			<option value="我爸爸的出生地">我爸爸的出生地</option>
			<option value="我妈妈的出生地">我妈妈的出生地</option>
			<option value="我的小学校名">我的小学校名</option>
			<option value="我的中学校名">我的中学校名</option>
			<option value="我最喜欢的运动">我最喜欢的运动</option>
			<option value="我最喜欢的歌曲">我最喜欢的歌曲</option>
			<option value="我最喜欢的电影">我最喜欢的电影</option>
			<option value="我最喜欢的电影">我最喜欢的颜色</option>
			<option value="自定义问题">自定义问题</option>
		</select>
		<input id="customquestion" style="display:none" type="text" maxlength="15" name="customquestion" class="input">
	</th>
	</tr>

	<tr class="tr3 f_one">
		<th>您的答案</th>
		<th><input type="text" name="answer" class="input"></th>
	</tr>

	<tr><td class="h" colspan="2"> 会员基本数据 </td></tr>

	<tr class="tr3 f_one">
		<th> 性别</th>
		<th>
		<select name="sex">
		<option value="保密" <%=user.getSex().equals("保密")?"selected":""%>>保密</option>
		<option value="男" <%=user.getSex().equals("男")?"selected":""%>>男</option>
		<option value="女" <%=user.getSex().equals("女")?"selected":""%>>女</option>
		</select>
		</th>
	</tr>
	<tr class="tr3 f_two">
		<th> 生日</th>
		<th><input class="input" type="text" size="20" maxlength="10" name="birthday" value="<%=user.getBirthday()==null?"":user.getBirthday()%>" onFocus="setday(this)"/></th>
		</th>
	</tr>
	<tr class="tr3 f_one">
		<th> 来自</th>
		<th><input class="input" type="text" size="20" maxlength="14" name="hometown" value="<%=ServletUtils.replaceIfMissing(user.getHometown(),"")%>" /></th>
	</tr>
	<tr class="tr3 f_two">
		<th width="35%">QQ</th>
		<th><input class="input" type="text" size="20" maxlength="14" name="qq" value="<%=ServletUtils.replaceIfMissing(user.getQq(),"")%>" /></th>
	</tr>
	<tr class="tr3 f_one">
		<th>blog、个人网站</th>
		<th><input class="input" type="text" size="50" maxlength="75" name="blog" value="<%=ServletUtils.replaceIfMissing(user.getBlog(),"")%>" /></th>
	</tr>

	<tr><td class="h" colspan="2">附加信息</td></tr>

	<tr class="tr3 f_one">
		<th>你是如何知道本站的？ <br />为了方便网站的发展，请填写此项，谢谢配合！</th>
		<th><select name="howdoknow" disabled><option value="<%=user.getHowdoknow()%>" selected><%=user.getHowdoknow()%></option></select> <b>不可修改</b></th>
	</tr>

	<tr><td class="h" colspan="2">会员可定义数据<a name="face"></a></td></tr>
	<tr class="tr3 f_one">
		<th valign="top"><br />论坛头像<br /></th>
		<th>
		  <div style="float:left;"><img src="<%=user.getHead()%>" width="110" height="110" name="useravatars" /></div>
		</th>
	</tr>
	<tr class="tr3 f_one">
		<th>头像地址</th>
		<th>
		  <input class="input" type="text" size="40" name="head" value="<%=user.getHead()%>" />
		</th>
	</tr>

	<tr class="tr3 f_two">
		<th>个性签名（将在首页和帖子页面用户信息处显示）<br>--少于100字节</th>
		<th><input class="input" type="text" size="40" name="self_sign" value="<%=ServletUtils.replaceIfMissing(user.getSelf_sign(),"")%>" /></th>
	</tr>

	<tr class="tr3 f_one">
		<th> 帖子签名（将在每个帖子下方显示）<br>--少于1000字节
		</th>
		<th><textarea name="topic_sign" rows="4" cols="50"><%=ServletUtils.replaceIfMissing(user.getTopic_sign(),"")%></textarea></th>
	</tr>
</table>
</div>

<div style="text-align:center;margin-bottom:25px;">
<input class="btn" type="submit" value="确认修改" />
</div>

<script src="js/dateselect.js" type="text/javascript"></script>
<script src="js/register.js" type="text/javascript"></script>

</form>
