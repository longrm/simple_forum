// JavaScript Document
var xmlHttp;
var msg=new Array(
	"",
	"<font color=\"blue\">����У����Ե�...</font>",
	"<font color=\"red\">�û������ȴ���</font>",
	"<font color=\"red\">���û����������ɽ����ַ��򱻹���Ա����,��ѡ�������û���!</font>",
	"<font color=\"red\">Ϊ�˱�����̳�û�������,�û����н�ֹʹ�ô�д��ĸ,��ʹ��Сд��ĸ!</font>",
	"<font color=\"red\">���û����Ѿ���ע�ᣬ��ѡ�������û���!</font>",
	"<font color=\"green\">��ϲ�������û�����δ��ע�ᣬ������ʹ������û���ע�ᣡ</font>",
	"<font color=\"red\">ϵͳ�����쳣�����ʧ�ܣ�</font>"
);
function checkname() {
	var username = document.getElementById("name").value;
	if(username == ""){
		return;
	}
	retmsg(0);
	xmlHttp = createXMLHttpRequest();
	xmlHttp.open("POST", "validate.ajax");
	xmlHttp.onreadystatechange = checknamestatus;
	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlHttp.send("name="+username);
}
function checknamestatus() {
	if(xmlHttp.readyState==4&&xmlHttp.status==200) {
		var reslut = xmlHttp.responseText;
		if(reslut=="existIllegal")
		  retmsg(3);
		else if(reslut=="existUpper")
		  retmsg(4);
		else if(reslut=="isUsed")
		  retmsg(5);
		else if(reslut=="success")
		  retmsg(6);
		else if(reslut=="failure")
		  retmsg(7);
	}
	else
	  retmsg(1);
}
function retmsg(id){
	document.getElementById("name_info").innerHTML = msg[id];
}
function showcustomquest(qid){
	if(qid=="�Զ�������"){
		document.getElementById("customquestion").style.display="";
	}else{
		document.getElementById("customquestion").style.display="none";
	}
}
function showimage(imgpath,value){
	if(value!= '') {
		document.images.useravatars.src=imgpath+'/face/'+value;
	} else{
		document.images.useravatars.src=imgpath+'/face/none.gif';
	}
}
function regcheck(formct){
	if (formct.name.value=='' || formct.password.value=='' || formct.regpwdrepeat.value==''){
		alert('�û���������Ϊ��,����д');
		return false;
	}
	if(formct.email.value==''){
		alert('EmailΪ��,����д');
		return false;
	}
	if (formct.password.value!=formct.regpwdrepeat.value){
		alert('������������벻һ�£����������');
		return false;
	}
	if (formct.password.value.length<6){
		alert('����̫�٣�����6λ����');
		return false;
	}
	formct.regsubmit.disabled=true;
}
function checkpwd(){
	var pwd = document.getElementById("password").value;
	var pwdrepeat = document.getElementById("regpwdrepeat").value;
	if (pwd.length<6){
		document.getElementById("pwd_info").innerHTML = "<font color=\"red\">����̫�٣�����6λ����</font>";
	} else{
		document.getElementById("pwd_info").innerHTML = "";
	}
	if(pwdrepeat){
		checkpwdrepeat();
	}
}
function checkpwdrepeat(){
	var pwd = document.getElementById("password").value;
	var pwdrepeat = document.getElementById("regpwdrepeat").value;
	if (pwdrepeat==pwd){
		document.getElementById("pwdrepeat_info").innerHTML = "";
	} else{
		document.getElementById("pwdrepeat_info").innerHTML = "<font color=\"red\">������������벻һ�£���������ԡ�</font>";
	}
}
function checkemail(){
	var email = document.getElementById("email").value;
	var myReg = /^[-a-zA-Z0-9_\.]+@([0-9A-Za-z][0-9A-Za-z-]+\.)+[A-Za-z]{2,5}$/; 
	if(myReg.test(email)){
		document.getElementById("email_info").innerHTML = "";
    	xmlHttp = createXMLHttpRequest();
    	xmlHttp.open("POST", "validate.ajax");
	    xmlHttp.onreadystatechange = checkemailstatus;
    	xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	    xmlHttp.send("email="+email);
	} else{
		document.getElementById("email_info").innerHTML = "<font color=\"red\">Email ��ʽ����!</font>";
	}
}
function checkemailstatus() {
	if(xmlHttp.readyState==4&&xmlHttp.status==200) {
		var reslut = xmlHttp.responseText;
		if(reslut=="isUsed"){
		  document.getElementById("email_info").innerHTML = "<font color=\"red\">��Email�Ѿ���ʹ�ã���ѡ������Email!</font>";
		}
		else if(reslut=="success"){
		  document.getElementById("email_info").innerHTML = "<font color=\"green\">��ϲ����������ʹ�����Email��</font>";
		}
		else if(reslut=="failure"){
		  document.getElementById("email_info").innerHTML = "<font color=\"red\">ϵͳ�����쳣�����ʧ�ܣ�</font>";
		}
	}
	else
	  document.getElementById("email_info").innerHTML = "<font color=\"blue\">����У����Ե�...</font>";
}

function editcheck(formct){
	if(formct.oldpwd.value=='')
		return true;
	if (formct.password.value=='' || formct.regpwdrepeat.value==''){
		alert('������Ϊ��,����д');
		return false;
	}
	if(formct.email.value==''){
		alert('EmailΪ��,����д');
		return false;
	}
	if (formct.password.value!=formct.regpwdrepeat.value){
		alert('������������벻һ�£����������');
		return false;
	}
	if (formct.password.value.length<6){
		alert('����̫�٣�����6λ����');
		return false;
	}
	formct.regsubmit.disabled=true;
}