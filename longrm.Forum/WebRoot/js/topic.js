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
	// ����ļ����Ƿ��ϴ��ļ�
	var files = document.getElementsByName("file");
	if(files.length>0) {
		document.getElementById("msg").innerHTML = "<font color=blue>�ļ������ϴ������Ժ�......</font>";
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
		alert("���ⲻ��Ϊ��");
		obj.title.focus();
		return false;
	} else if(obj.title.value.length>100){
		alert("���ⳬ����󳤶� 100 ���ֽ�");
		obj.title.focus();
		return false;
	}
	if(obj.content.value.length<2){
		alert("������������ 2 ���ֽ�");
		obj.content.focus();
		return false;
	} else if(obj.content.value.length>50000){
		alert("�������ݴ��� 50000 ���ֽ�");
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
	alert('�������������ֽ����� '+document.topicForm.content.value.length+' �ֽ�\n'+postmaxchars+' �ֽ�');
}

function callback(msg)
{
	if(msg=="success"){
		document.getElementById("msg").innerHTML = "<font color=green>�ļ��ϴ��ɹ���</font>";
		document.fileForm.loading.style.display="none";
		document.topicForm.submit();
	}
	else if(msg=="failed"){
		document.getElementById("msg").innerHTML = "<font color=red>�ļ��ϴ�ʧ�ܣ�</font>";
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
        str=str+"<input type="+'"'+"button"+'"'+" value="+'"'+"ɾ��"+'"'+" onclick='deleteitem(this,"+'"'+"fileTable"+'"'+");'>"
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
		alert("��ѡ��Ҫ����������");
		return;
	}
	form.submit();
}
