var cnt = 0;

function postreply(txt){
	if(typeof document.topicForm != "undefined"){
		document.topicForm.title.value = txt;
		document.topicForm.content.focus();
	}
}

function topic_file_post() {
	if(!checkpost(document.topicForm))
		return;
	// 检查文件域，是否上传文件
	var files = document.getElementsByName("file");
	if(files.length>0) {
		document.getElementById("msg").innerHTML = "<font color=blue>文件正在上传，请稍后......</font>";
		document.fileForm.loading.style.display="";
		document.fileForm.submit();		
	}
	else
		document.topicForm.submit();
}

function quickpost(event) {
	if(event.ctrlKey && window.event.keyCode==13)
		document.topicForm.submit();
}

function quickpost2(event) {
	if(event.ctrlKey && window.event.keyCode==13)
		topic_file_post();
}

function checkpost(obj){
	if(obj.title.value==""){
		alert("标题不能为空");
		obj.title.focus();
		return false;
	} else if(obj.title.value.length>100){
		alert("标题超过最大长度 100 个字节");
		obj.title.focus();
		return false;
	}
	if(obj.content.value.length<2){
		alert("文章内容少于 2 个字节");
		obj.content.focus();
		return false;
	} else if(obj.content.value.length>50000){
		alert("文章内容大于 50000 个字节");
		obj.content.focus();
		return false;
	}
	document.getElementById("postBut").disabled=true;
	cnt++;
	if(cnt!=1){
		alert('Submission Processing. Please Wait');
		return false;
	}
	return true;
}

function checklength(postmaxchars){
	alert('您的帖子已有字节数： '+document.topicForm.content.value.length+' 字节\n'+postmaxchars+' 字节');
}

function callback(msg)
{
	if(msg=="success"){
		document.getElementById("msg").innerHTML = "<font color=green>文件上传成功！</font>";
		document.fileForm.loading.style.display="none";
		document.topicForm.submit();
	}
	else if(msg=="failed"){
		document.getElementById("msg").innerHTML = "<font color=red>文件上传失败！</font>";
		document.fileForm.loading.style.display="none";
		document.getElementById("postBut").disabled=false;
		cnt--;
	}
}

function addFile(tabid)
{
    var row,cell,str;
    row = eval("document.all["+'"'+tabid+'"'+"]").insertRow();
    if(row != null ){
        cell = row.insertCell();
        str="<input type="+'"'+"file"+'"'+" name="+'"'+"file"+'"'+" size="+'"'+"80"+'"'+">";
        str=str+"<input type="+'"'+"button"+'"'+" value="+'"'+"删除"+'"'+" onclick='deleteitem(this,"+'"'+"fileTable"+'"'+");'>"
        cell.innerHTML=str;
    }
}

function deleteitem(obj,tabid){
    var rowNum,curRow;
    curRow = obj.parentNode.parentNode;
    rowNum = eval("document.all."+tabid).rows.length - 1;
    eval("document.all["+'"'+tabid+'"'+"]").deleteRow(curRow.rowIndex);
}

function deleteRetopic(form) {
	var hasChecked = false;
	//var form = document.retopicForm;
	for(var i=0;i<form.elements.length;i++){
		var e = form.elements[i];
		if(e.type=='checkbox' && e.checked==true){
			hasChecked = true;
			break;
		}
	}
	if(!hasChecked){
		alert("请选择要操作的帖子");
		return;
	}
	form.submit();
}
