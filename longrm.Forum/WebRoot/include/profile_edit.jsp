<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="org.longrm.forum.bean.*, java.util.*, org.longrm.forum.util.*"%>

<form name="editForm" action="editselfinfo.do" method="post" onSubmit="return editcheck(this)">
<div class="t"><table width="100%" cellspacing="0" cellpadding="0" align="center">
	<tr><td class="h" colspan="2">��Ա��������</td></tr>

	<tr class="tr3 f_one">
		<th> ԭ����</th>
		<th><input class="input" type="password" size="20" maxlength="50" name="oldpwd" value="" /> �޸� <font color=blue>����</font> �� <font color=blue>����</font> ʱ��Ҫ������֤</th>
	</tr>
	
	<tr class="tr3 f_two">
	<th> ������<br />Ӣ����ĸ�����ֵȲ�����6λ</th>
	<th style="vertical-align:middle">
	<input class="input" type="password" size="20" maxlength="50" name="password" id="password" onChange="checkpwd();" />
	&nbsp;<span id="pwd_info"></span>
	</th>
	</tr>

	<tr class="tr3 f_one">
	<th> ȷ������</th>
	<th><input class="input" type="password" size="20" maxlength="50" name="regpwdrepeat" id="regpwdrepeat" onChange="checkpwdrepeat();" />&nbsp;&nbsp;<span id="pwdrepeat_info"></span></th>
	</tr>
	
	<tr class="tr3 f_two">
		<th> Email</th>
		<th>
		<input class="input" type="text" size="20" value="<%=user.getEmail()%>" maxlength="75" name="email" id="email" onChange="checkemail();" />
		&nbsp;<input type="checkbox" name="ispublic" value="Y" <%=user.getIspublic()==null?"":"checked"%> />�Ƿ񹫿�
		&nbsp;<span id="email_info"></span>
		</th>
	</tr>

	<tr class="tr3 f_one">
	<th>��ȫ����<br />��������˰�ȫ���⣬��Ҫ��д��ȷ�Ĵ𰸲��ܵ�¼��̳</th>
	<th>
		<select name="question" onChange="showcustomquest(this.value)" style="width:150px">
			<option value="nochange">���޸İ�ȫ����ʹ�</option>
			<option value="">�ް�ȫ����</option>
			<option value="�Ұְֵĳ�����">�Ұְֵĳ�����</option>
			<option value="������ĳ�����">������ĳ�����</option>
			<option value="�ҵ�СѧУ��">�ҵ�СѧУ��</option>
			<option value="�ҵ���ѧУ��">�ҵ���ѧУ��</option>
			<option value="����ϲ�����˶�">����ϲ�����˶�</option>
			<option value="����ϲ���ĸ���">����ϲ���ĸ���</option>
			<option value="����ϲ���ĵ�Ӱ">����ϲ���ĵ�Ӱ</option>
			<option value="����ϲ���ĵ�Ӱ">����ϲ������ɫ</option>
			<option value="�Զ�������">�Զ�������</option>
		</select>
		<input id="customquestion" style="display:none" type="text" maxlength="15" name="customquestion" class="input">
	</th>
	</tr>

	<tr class="tr3 f_one">
		<th>���Ĵ�</th>
		<th><input type="text" name="answer" class="input"></th>
	</tr>

	<tr><td class="h" colspan="2"> ��Ա�������� </td></tr>

	<tr class="tr3 f_one">
		<th> �Ա�</th>
		<th>
		<select name="sex">
		<option value="����" <%=user.getSex().equals("����")?"selected":""%>>����</option>
		<option value="��" <%=user.getSex().equals("��")?"selected":""%>>��</option>
		<option value="Ů" <%=user.getSex().equals("Ů")?"selected":""%>>Ů</option>
		</select>
		</th>
	</tr>
	<tr class="tr3 f_two">
		<th> ����</th>
		<th><input class="input" type="text" size="20" maxlength="10" name="birthday" value="<%=user.getBirthday()==null?"":user.getBirthday()%>" onFocus="setday(this)"/></th>
		</th>
	</tr>
	<tr class="tr3 f_one">
		<th> ����</th>
		<th><input class="input" type="text" size="20" maxlength="14" name="hometown" value="<%=ServletUtils.replaceIfMissing(user.getHometown(),"")%>" /></th>
	</tr>
	<tr class="tr3 f_two">
		<th width="35%">QQ</th>
		<th><input class="input" type="text" size="20" maxlength="14" name="qq" value="<%=ServletUtils.replaceIfMissing(user.getQq(),"")%>" /></th>
	</tr>
	<tr class="tr3 f_one">
		<th>blog��������վ</th>
		<th><input class="input" type="text" size="50" maxlength="75" name="blog" value="<%=ServletUtils.replaceIfMissing(user.getBlog(),"")%>" /></th>
	</tr>

	<tr><td class="h" colspan="2">������Ϣ</td></tr>

	<tr class="tr3 f_one">
		<th>�������֪����վ�ģ� <br />Ϊ�˷�����վ�ķ�չ������д���лл��ϣ�</th>
		<th><select name="howdoknow" disabled><option value="<%=user.getHowdoknow()%>" selected><%=user.getHowdoknow()%></option></select> <b>�����޸�</b></th>
	</tr>

	<tr><td class="h" colspan="2">��Ա�ɶ�������<a name="face"></a></td></tr>
	<tr class="tr3 f_one">
		<th valign="top"><br />��̳ͷ��<br /></th>
		<th>
		  <div style="float:left;"><img src="<%=user.getHead()%>" width="110" height="110" name="useravatars" /></div>
		</th>
	</tr>
	<tr class="tr3 f_one">
		<th>ͷ���ַ</th>
		<th>
		  <input class="input" type="text" size="40" name="head" value="<%=user.getHead()%>" />
		</th>
	</tr>

	<tr class="tr3 f_two">
		<th>����ǩ����������ҳ������ҳ���û���Ϣ����ʾ��<br>--����100�ֽ�</th>
		<th><input class="input" type="text" size="40" name="self_sign" value="<%=ServletUtils.replaceIfMissing(user.getSelf_sign(),"")%>" /></th>
	</tr>

	<tr class="tr3 f_one">
		<th> ����ǩ��������ÿ�������·���ʾ��<br>--����1000�ֽ�
		</th>
		<th><textarea name="topic_sign" rows="4" cols="50"><%=ServletUtils.replaceIfMissing(user.getTopic_sign(),"")%></textarea></th>
	</tr>
</table>
</div>

<div style="text-align:center;margin-bottom:25px;">
<input class="btn" type="submit" value="ȷ���޸�" />
</div>

<script src="js/dateselect.js" type="text/javascript"></script>
<script src="js/register.js" type="text/javascript"></script>

</form>
