// JavaScript Document
function showcustomquest(qid){
	if(qid=="�Զ�������"){
		document.getElementById("customquestion").style.display="";
	}else{
		document.getElementById("customquestion").style.display="none";
	}
}
function logcheck(formct){
	if (formct.name.value=='' || formct.password.value==''){
		alert('�û���������Ϊ��,����д');
		return false;
	}
	formct.logsubmit.disabled=true;
}
