var functions = {};
functions.temppassword = "";

functions.oFormplace = document.getElementById("formplace");
functions.oErrorinfo = document.getElementById("error-info");
functions.oErrorbox = document.getElementById("error-box");
functions.aInputs = functions.oFormplace.getElementsByTagName("input");

functions.checkEmail = function (strEmail) {
    //声明邮箱正则
    var emailRegex = /^([a-zA-Z0-9]+[_|.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
    if (!emailRegex.test(strEmail)) {
        console.log("invalid email");
        return false;
    }
    return true;
};

functions.showRightspan = function (obj) {
    var oSpan = obj.parentNode.parentNode.getElementsByTagName("span")[0];
    if (oSpan) {
        oSpan.className = "checkspan checkright";
    }
};

functions.showWrongspan = function (obj) {
    var oSpan = obj.parentNode.parentNode.getElementsByTagName("span")[0];
    if (oSpan) {
        oSpan.className = "checkspan checkwrong";
    }
};

functions.hideCheckspan = function (obj) {
    var oSpan = obj.parentNode.parentNode.getElementsByTagName("span")[0];
    if (oSpan) {
        oSpan.className = "";
    }
};

functions.changeOutline = function (obj) {
    obj.style['border'] = "1px solid #ff7c87";
    obj.style['box-shadow'] = "0px 0px 20px #ffb3b2";
};

functions.resetOutline = function (obj) {
    obj.style['border'] = "0px";
    obj.style['box-shadow'] = "none";
};

functions.putErrormessage = function (str) {
    functions.oErrorinfo.innerHTML = str;
};

functions.showErrorbox = function (obj) {
    var tempLeft = 0;
    var tempTop = 0;

    while (obj != undefined) {//等效 obj = obj.offsetParent;while (obj != undefined)
        tempLeft += obj.offsetLeft; //叠加父容器的上边距
        tempTop += obj.offsetTop; //叠加父容器的左边距
        obj = obj.offsetParent;
    }
    console.log(tempTop);
    console.log(tempLeft);
    functions.oErrorbox.style['left'] = tempLeft + 320 + "px";
    functions.oErrorbox.style['top'] = tempTop + 2 + "px";
    functions.oErrorbox.style['display'] = "block";
    functions.oErrorinfo.style['display'] = "block";
};

functions.hideErrorbox = function () {
    functions.oErrorbox.style['display'] = "none";
    functions.oErrorinfo.style['display'] = "none";
};

functions.focusInputback = function (obj, str) {
    obj.parentNode.style['background-position'] = str;

};

functions.blurInputback = function (obj, str) {
    obj.parentNode.style['background-position'] = str;
};

functions.doRightfunction = function (obj, str) {
    functions.showRightspan(obj);
    functions.resetOutline(obj);
    functions.hideErrorbox(obj);
    console.log(str);
};

functions.doWrongfunction = function (obj, str) {
    functions.showWrongspan(obj);
    functions.changeOutline(obj);
    functions.putErrormessage(str);
    functions.showErrorbox(obj);
};
