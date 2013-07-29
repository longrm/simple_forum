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
        alert("请输入或选择要发送的用户");
        return false;
    }
    if (obj.title.value == "") {
        alert("标题不能为空");
        obj.title.focus();
        return false;
    }
    else 
        if (obj.title.value.length > 100) {
            alert("标题超过最大长度 100 个字节");
            obj.title.focus();
            return false;
        }
    if (obj.content.value.length < 2) {
        alert("信息内容少于 2 个字节");
        obj.content.focus();
        return false;
    }
    else 
        if (obj.content.value.length > 2000) {
            alert("信息内容大于2000 个字节");
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
    alert('您的信息已有字节数： ' + document.messageForm.content.value.length + ' 字节\n' + postmaxchars + ' 字节');
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
        alert("请选择要操作的信息");
	return hasChecked;
}
