// JavaScript Document
function showcustomquest(qid){
	if(qid=="自定义问题"){
		document.getElementById("customquestion").style.display="";
	}else{
		document.getElementById("customquestion").style.display="none";
	}
}
function logcheck(formct){
	if (formct.name.value=='' || formct.password.value==''){
		alert('用户名或密码为空,请填写');
		return false;
	}
	formct.logsubmit.disabled=true;
}
