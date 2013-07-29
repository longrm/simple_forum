// JavaScript Document
var ifcheck = true;
function CheckAll(form){
	for(var i=0;i<form.elements.length-12;i++){
		var e = form.elements[i];
		if(e.type=='checkbox') e.checked = ifcheck;
	}
	ifcheck = ifcheck == true ? false : true;
}

function submitcheck(form){
	var hasChecked = false;
	for(var i=0;i<form.elements.length-12;i++){
		var e = form.elements[i];
		if(e.type=='checkbox' && e.checked==true){
			hasChecked = true;
			break;
		}
	}
	if(!hasChecked){
		alert("请选择要操作的帖子");
	}
	return hasChecked;
}
