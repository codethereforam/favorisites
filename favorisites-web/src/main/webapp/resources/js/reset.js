
window.onload = function(){
    functions.userJudge = function (){
        var oUserinput = document.getElementById("userinput");
        oUserinput.onfocus = function(){
            functions.focusInputBack(this,"0px -55px");
            functions.resetOutline(this);
            functions.hideCheckSpan(this);
        }
        oUserinput.onblur = function(){
            functions.blurInputBack(this,"0px 0px");
            var thisValue = this.value ;
            if( thisValue== null || thisValue.replace(" ","").length == 0 || thisValue.indexOf(" ") != "-1"){
                functions.showWrongSpanAndMessage(this,"用户名不能为空，或者带有空格");
                console.log(01);
            }else if(thisValue.length < 5 || thisValue.length > 15){
                functions.showWrongSpanAndMessage(this,"用户名字符长度应为闭区间 [5-15]");
                console.log(02);
            }else if(  /^[0-9a-zA-Z\u4e00-\u9fa5_]{5,15}$/.test(thisValue) == false  ){
                functions.showWrongSpanAndMessage(this,"只支持英文字母（不区分大小写）、数字和下划线，请不要输入非法字符");
                console.log(03);
            }else{
                functions.showRightSpan(this);
            }
        }
    }
    functions.emailJudge = function(){
        var oEmailinput = document.getElementById("emailinput");
        oEmailinput.onfocus = function(){
            functions.focusInputBack(this,"-650px -55px");
            functions.resetOutline(this);
            functions.hideCheckSpan(this);
        }
        oEmailinput.onblur = function(){
            functions.focusInputBack(this,"-650px 0px");
            var thisValue = this.value ;
            if( thisValue== null || thisValue.replace(" ","").length == 0){
                functions.showWrongSpanAndMessage(this,"邮箱不能为空且不能带有空格");
                console.log(41);
            }else if(thisValue.length > 50){
                functions.showWrongSpanAndMessage(this,"邮箱的长度不能超过50");
                console.log(42);
            }else if(functions.checkEmail(thisValue) == false){
                functions.showWrongSpanAndMessage(this,"输入的邮箱格式不正确");
                console.log(43);
            }else{
                functions.showRightSpan(this);
            }
        }
    }
    functions.codeJudge = function(){
        var oCodeinput = document.getElementById("codeinput");
        oCodeinput.onfocus = function(){
            functions.focusInputBack(this,"-975px -55px");
            functions.resetOutline(this);
            functions.hideCheckSpan(this);
        }
        oCodeinput.onblur = function(){
            functions.focusInputBack(this,"-975px 0px");
            var thisValue = this.value ;
            if( thisValue== null || thisValue.replace(" ","").length == 0){
                functions.showWrongSpanAndMessage(this,"验证码不能为空或者含有空格");
                console.log(51);
            }else if(thisValue.length != 6){
                functions.showWrongSpanAndMessage(this,"验证码长度应该为6位");
            }else if( /^[0-9a-zA-Z]+$/ .test(thisValue) == false){
                functions.showWrongSpanAndMessage(this,"验证码只能是英文或者数字");
            }else{
                functions.showRightSpan(this);
            }

            // 验证码错误，请检查邮箱地址或点击重新发送 ,这个是发送之后才会判断的
        }
    }
    /*functions.buttonsJudge = function(){
        if(functions.buttonflag == false){
            document.getElementById("submitbutton").disabled = true;
        }else {
            document.getElementById("submitbutton").disabled = false;
        }
        console.log(document.getElementById("submitbutton").disabled);
    }*/
    functions.userJudge();
    functions.emailJudge();
    functions.codeJudge();

}
