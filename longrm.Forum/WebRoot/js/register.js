// JavaScript Document
var xmlHttp;
var msg=new Array(
	"",
	"<font color=\"blue\">检测中，请稍等...</font>",
	"<font color=\"red\">用户名长度错误！</font>",
	"<font color=\"red\">此用户名包含不可接受字符或被管理员屏蔽,请选择其它用户名!</font>",
	"<font color=\"red\">为了避免论坛用户名混乱,用户名中禁止使用大写字母,请使用小写字母!</font>",
	"<font color=\"red\">该用户名已经被注册，请选用其他用户名!</font>",
	"<font color=\"green\">恭喜您，该用户名还未被注册，您可以使用这个用户名注册！</font>",
	"<font color=\"red\">系统出现异常，检测失败！</font>"
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
	if(qid=="自定义问题"){
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
		alert('用户名或密码为空,请填写');
		return false;
	}
	if(formct.email.value==''){
		alert('Email为空,请填写');
		return false;
	}
	if (formct.password.value!=formct.regpwdrepeat.value){
		alert('两次输入的密码不一致，请检查后重试');
		return false;
	}
	if (formct.password.value.length<6){
		alert('密码太少，请用6位以上');
		return false;
	}
	formct.regsubmit.disabled=true;
}
function checkpwd(){
	var pwd = document.getElementById("password").value;
	var pwdrepeat = document.getElementById("regpwdrepeat").value;
	if (pwd.length<6){
		document.getElementById("pwd_info").innerHTML = "<font color=\"red\">密码太少，请用6位以上</font>";
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
		document.getElementById("pwdrepeat_info").innerHTML = "<font color=\"red\">两次输入的密码不一致，请检查后重试。</font>";
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
		document.getElementById("email_info").innerHTML = "<font color=\"red\">Email 格式错误!</font>";
	}
}
function checkemailstatus() {
	if(xmlHttp.readyState==4&&xmlHttp.status==200) {
		var reslut = xmlHttp.responseText;
		if(reslut=="isUsed"){
		  document.getElementById("email_info").innerHTML = "<font color=\"red\">该Email已经被使用，请选用其他Email!</font>";
		}
		else if(reslut=="success"){
		  document.getElementById("email_info").innerHTML = "<font color=\"green\">恭喜您，您可以使用这个Email！</font>";
		}
		else if(reslut=="failure"){
		  document.getElementById("email_info").innerHTML = "<font color=\"red\">系统出现异常，检测失败！</font>";
		}
	}
	else
	  document.getElementById("email_info").innerHTML = "<font color=\"blue\">检测中，请稍等...</font>";
}

function editcheck(formct){
	if(formct.oldpwd.value=='')
		return true;
	if (formct.password.value=='' || formct.regpwdrepeat.value==''){
		alert('新密码为空,请填写');
		return false;
	}
	if(formct.email.value==''){
		alert('Email为空,请填写');
		return false;
	}
	if (formct.password.value!=formct.regpwdrepeat.value){
		alert('两次输入的密码不一致，请检查后重试');
		return false;
	}
	if (formct.password.value.length<6){
		alert('密码太少，请用6位以上');
		return false;
	}
	formct.regsubmit.disabled=true;
}