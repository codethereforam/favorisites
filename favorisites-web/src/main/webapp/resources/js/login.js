window.onload = function(){
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
            }else if(thisValue.length < 5 || thisValue.length > 15){
                functions.doWrongfunction(this,"用户名字符长度应为闭区间 [5-15]");
            }else if(  /^[0-9a-zA-Z\u4e00-\u9fa5_]{5,15}$/.test(thisValue) == false  ){
                functions.doWrongfunction(this,"只支持英文字母（不区分大小写）、数字和下划线，请不要输入非法字符");
            }else if( functions.matchMysql(this,thisValue) == false){
                functions.doWrongfunction(this,"你还没有注册");

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
            }else if(thisValue.length < 6 || thisValue.length > 16){
                functions.doWrongfunction(this,"密码字符长度应为闭区间 [6-16] ");
            }else if(  /^[0-9a-zA-Z\u4e00-\u9fa5_]{6,16}$/.test(thisValue) == false  ){
                functions.doWrongfunction(this,"只支持英文字母（不区分大小写）、数字和下划线，请不要输入非法字符");
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
            }else if(thisValue.length != 4){
                functions.doWrongfunction(this,"验证码长度应该为4位");
            }else if( /^[0-9a-zA-Z]+$/ .test(thisValue) == false){
                functions.doWrongfunction(this,"验证码只能是英文或者数字");
            }else{
                functions.doRightfunction(this,"codeSuccess");
            }

            // 验证码错误，请检查邮箱地址或点击重新发送 ,这个是发送之后才会判断的
        }
    }
    functions.userJudge();
    functions.passwordJudge();
    functions.codeJudge();

}
