
window.onload = function(){
    var functions = {};
    functions.temppassword = "";
    functions.buttonflag = false;
    functions.cursorPosition = [0,0];
    functions.oFormplace = document.getElementById("formplace");
    functions.oErrorinfo = document.getElementById("error-info");
    functions.oErrorbox = document.getElementById("error-box");
    functions.aInputs = functions.oFormplace.getElementsByTagName("input");

    functions.showRightspan = function(obj){
        var oSpan = obj.parentNode.parentNode.getElementsByTagName("span")[0] ;
        if(oSpan) {
            oSpan.className = "checkspan checkright";
        }
    }
    functions.showWrongspan = function(obj){
        var oSpan = obj.parentNode.parentNode.getElementsByTagName("span")[0] ;
        if(oSpan){
            oSpan.className = "checkspan checkwrong" ;
        }
    }
    functions.hideCheckspan = function(obj){
        var oSpan = obj.parentNode.parentNode.getElementsByTagName("span")[0] ;
        if(oSpan){
            oSpan.className = "" ;
        }
    }
    functions.changeOutline = function(obj){
        obj.style['border'] = "1px solid #ff7c87";
        obj.style['box-shadow'] = "0px 0px 20px #ffb3b2";
    }
    functions.resetOutline = function(obj){
        obj.style['border'] = "0px";
        obj.style['box-shadow'] = "none";
    }
    functions.putErrormessage = function(str){
        functions.oErrorinfo.innerHTML = str;
    }
    functions.showErrorbox = function(obj){
        var tempLeft = 0;
        var tempTop = 0;

        while ( obj != undefined) {//等效 obj = obj.offsetParent;while (obj != undefined)
            tempLeft += obj.offsetLeft; //叠加父容器的上边距
            tempTop += obj.offsetTop; //叠加父容器的左边距
            obj = obj.offsetParent;
        }
        console.log(tempTop);
        console.log(tempLeft);
        functions.oErrorbox.style['left'] = tempLeft+280+"px";
        functions.oErrorbox.style['top'] = tempTop-60+"px";
        functions.oErrorbox.style['display'] = "block";
        functions.oErrorinfo.style['display'] = "block";
    }
    functions.hideErrorbox = function(){
        functions.oErrorbox.style['display'] = "none";
        functions.oErrorinfo.style['display'] = "none";
    }

    functions.matchMysql = function(obj,str){

    }
    functions.isEmail = function(strEmail) {
        //声明邮箱正则
        var myreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
        if(!myreg.test(strEmail))//对输入的值进行判断
        {
            console.log("fail");
            return false;
        }
        // return true;
    }
    functions.putTojson = function(str){

    }
    functions.focusInputback =function(obj,str){
        obj.parentNode.style['background-position'] = str;

    }
    functions.blurInputback =function(obj,str){
        obj.parentNode.style['background-position'] = str;
    }
    functions.doRightfunction = function(obj,str){
        functions.buttonflag = true;
        functions.showRightspan(obj);
        functions.resetOutline(obj);
        functions.hideErrorbox(obj);
        functions.putTojson(obj.value);
        console.log(str);
    }
    functions.doWrongfunction = function(obj,str){
        functions.buttonflag = false;
        functions.showWrongspan(obj);
        functions.changeOutline(obj);
        functions.putErrormessage(str);
        functions.showErrorbox(obj);
    }
    String.prototype.trim = function() {
        return this.replace(/(^\s*)|(\s*$)/g, '');
    };
    functions.userJudge = function (){
        var oUserinput = document.getElementById("userinput");
        oUserinput.onfocus = function(){
            functions.focusInputback(this,"0px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        }
        oUserinput.onblur = function(){
            functions.blurInputback(this,"0px 0px");
            var thisValue = this.value ;
            if( thisValue== null || thisValue.replace(" ","").length == 0 || thisValue.indexOf(" ") != "-1"){
                functions.doWrongfunction(this,"用户名不能为空，或者带有空格");
                console.log(01);
            }else if(thisValue.length < 5 || thisValue.length > 15){
                functions.doWrongfunction(this,"用户名字符长度应为闭区间 [5-15]");
                console.log(02);
            }else if(  /^[0-9a-zA-Z\u4e00-\u9fa5_]{5,15}$/.test(thisValue) == false  ){
                functions.doWrongfunction(this,"只支持英文字母（不区分大小写）、数字和下划线，请不要输入非法字符");
                console.log(03);
            }else if( functions.matchMysql(this,thisValue) == false){
                functions.doWrongfunction(this,"真抱歉！这个ID太抢手已经被人先注册啦，换一个试试吧");
                console.log(04);
            }else{
                functions.doRightfunction(this,"usernameSuccess");
            }
        }
    }
    functions.passwordJudge = function (){
        var oPasswordinput = document.getElementById("passwordinput");
        oPasswordinput.onfocus = function(){
            functions.focusInputback(this,"-325px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        }
        oPasswordinput.onblur = function(){
            functions.focusInputback(this,"-325px 0px");
            var thisValue = this.value ;
            if( thisValue== null || thisValue.replace(" ","").length == 0 || thisValue.indexOf(" ") != "-1"){
                functions.doWrongfunction(this,"密码不能为空或者全为空格");
                console.log(11);
            }else if(thisValue.length < 6 || thisValue.length > 16){
                functions.doWrongfunction(this,"密码字符长度应为闭区间 [6-16] ");
                console.log(12);
            }else if(  /^[0-9a-zA-Z\u4e00-\u9fa5_]{6,16}$/.test(thisValue) == false  ){
                functions.doWrongfunction(this,"只支持英文字母（不区分大小写）、数字和下划线，请不要输入非法字符");
                console.log(13);
                //这个正则表达式有为题，需要修改
            }else{
                functions.temppassword = thisValue;
                functions.doRightfunction(this,"passwordSuccess");
                functions.doRightfunction(functions.aInputs[2],"cpasswordSuccess");
                // functions.doRightfunction(functions.aInputs[2],"cpasswordSuccess");
            }
        }
    }
    functions.codeJudge = function(){
        var oCodeinput = document.getElementById("codeinput");
        oCodeinput.onfocus = function(){
            functions.focusInputback(this,"-975px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        }
        oCodeinput.onblur = function(){
            functions.focusInputback(this,"-975px 0px");
            var thisValue = this.value ;
            if( thisValue== null || thisValue.replace(" ","").length == 0){
                functions.doWrongfunction(this,"验证码不能为空或者含有空格");
                console.log(51);
            }else if(thisValue.length != 6){
                functions.doWrongfunction(this,"验证码长度应该为6位");
            }else if( /^[0-9a-zA-Z]+$/ .test(thisValue) == false){
                functions.doWrongfunction(this,"验证码只能是英文或者数字");
            }else{
                functions.doRightfunction(this,"codeSuccess");
            }

            // 验证码错误，请检查邮箱地址或点击重新发送 ,这个是发送之后才会判断的
        }
    }
    functions.buttonsJudge = function(){
        if(functions.buttonflag == false){
            document.getElementById("submitbutton").disabled = true;
        }else {
            document.getElementById("submitbutton").disabled = false;
        }
        console.log(document.getElementById("submitbutton").disabled);
    }
    functions.userJudge();
    functions.passwordJudge();
    functions.codeJudge();
    functions.buttonsJudge();

}
