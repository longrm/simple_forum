var cnt = 0;
var ifcheck = true;

function CheckAll(form){
    for (var i = 0; i < form.elements.length - 2; i++) {
        var e = form.elements[i];
        if (e.type == 'checkbox' && e.name != 'ifsave') 
            e.checked = ifcheck;
    }
    ifcheck = ifcheck == true ? false : true;
}

function quickpost(event){
    if (event.ctrlKey && window.event.keyCode == 13) 
        message_post();
}

function message_post(){
    if (!checkpost(document.messageForm)) 
        return;
    document.messageForm.submit();
}

function checkpost(obj){
    var friends = "";
    for (var i = 0; i < obj.elements.length - 2; i++) {
        var e = obj.elements[i];
        if (e.type == 'checkbox' && e.name != 'ifsave' && e.checked == true) {
            friends = friends + ";" + e.value;
        }
    }
    if (obj.receivers.value == "" && friends == "") {
        alert("�������ѡ��Ҫ���͵��û�");
        return false;
    }
    if (obj.title.value == "") {
        alert("���ⲻ��Ϊ��");
        obj.title.focus();
        return false;
    }
    else 
        if (obj.title.value.length > 100) {
            alert("���ⳬ����󳤶� 100 ���ֽ�");
            obj.title.focus();
            return false;
        }
    if (obj.content.value.length < 2) {
        alert("��Ϣ�������� 2 ���ֽ�");
        obj.content.focus();
        return false;
    }
    else 
        if (obj.content.value.length > 2000) {
            alert("��Ϣ���ݴ���2000 ���ֽ�");
            obj.content.focus();
            return false;
        }
    document.getElementById("postBut").disabled = true;
    cnt++;
    if (cnt != 1) {
        alert('Submission Processing. Please Wait');
        return false;
    }
    obj.friends.value = friends;
    return true;
}

function checklength(postmaxchars){
    alert('������Ϣ�����ֽ����� ' + document.messageForm.content.value.length + ' �ֽ�\n' + postmaxchars + ' �ֽ�');
}

function submitcheck(form){
    var hasChecked = false;
    for (var i = 0; i < form.elements.length - 2; i++) {
        var e = form.elements[i];
        if (e.type == 'checkbox' && e.name != 'ifsave' && e.checked==true) {
            hasChecked = true;
            break;
        }
    }
    if (!hasChecked)
        alert("��ѡ��Ҫ��������Ϣ");
	return hasChecked;
}
