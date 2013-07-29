// JavaScript Document
function showMenu(obj) {
	var menu = document.getElementById(obj);
	var divmenu = document.getElementById("menu_"+obj);
	divmenu.style.left = menu.offsetLeft+5;
	divmenu.style.top=108;
	divmenu.style.display="";
}

function hideMenu(obj) {
	var divmenu = document.getElementById("menu_"+obj);
	divmenu.style.display="none";
}

function IndexDeploy(ID){
    obj = document.getElementById("cate_" + ID);
    img = document.getElementById("img_" + ID);
    if (obj.style.display == "none") {
        obj.style.display = "";
        img_re = new RegExp("_open\\.gif$");
        img.src = img.src.replace(img_re, '_fold.gif');
    }
    else {
        obj.style.display = "none";
        img_re = new RegExp("_fold\\.gif$");
        img.src = img.src.replace(img_re, '_open.gif');
    }
    return false;
}

function Addtoie(value, title){
    window.external.AddFavorite(value, title);
}

function createXMLHttpRequest(){
    var request = false;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
        if (request.overrideMimeType) {
            request.overrideMimeType('text/xml');
        }
    }
    else 
        if (window.ActiveXObject) {
            var versions = ['Microsoft.XMLHTTP', 'MSXML.XMLHTTP', 'Microsoft.XMLHTTP', 'Msxml2.XMLHTTP.7.0', 'Msxml2.XMLHTTP.6.0', 'Msxml2.XMLHTTP.5.0', 'Msxml2.XMLHTTP.4.0', 'MSXML2.XMLHTTP.3.0', 'MSXML2.XMLHTTP'];
            for (var i = 0; i < versions.length; i++) {
                try {
                    request = new ActiveXObject(versions[i]);
                    if (request) {
                        return request;
                    }
                } 
                catch (e) {
                }
            }
        }
    return request;
}

function forumjump(id){
    window.location.href = "viewforum.jsp?forum_id=" + id;
}

function redirect(url){
    window.location.href = url;
}

function DrawImage(ImgD, iwidth, iheight){
	var wid = ImgD.width;
	var hei = ImgD.height;
    if (wid > 0 && hei > 0) {
        if (wid / hei >= iwidth / iheight) {
            if (wid > iwidth) {
               ImgD.width = iwidth;
                ImgD.height = (hei * iwidth) / wid;
            }
        }
        else {
            if (hei > iheight) {
                ImgD.height = iheight;
                ImgD.width = (wid * iheight) / hei;
            }
        }
    }
}
