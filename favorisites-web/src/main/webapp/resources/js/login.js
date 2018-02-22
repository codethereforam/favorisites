var usernameRight = false;
var passwordRight = false;
var captchaRight = false;

window.onload = function () {
    functions.userJudge = function () {
        var oUserinput = document.getElementById("accountName");
        oUserinput.onfocus = function () {
            functions.focusInputback(this, "0px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };
        oUserinput.onblur = function () {
            functions.blurInputback(this, "0px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "请填写账户名");
                usernameRight = false;
            } else {
                functions.doRightfunction(this, "usernameSuccess");
                usernameRight = true;
            }
            functions.buttonsJudge();
        }
    };

    functions.passwordJudge = function () {
        var oPasswordinput = document.getElementById("password");
        oPasswordinput.onfocus = function () {
            functions.focusInputback(this, "-325px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };
        oPasswordinput.onblur = function () {
            functions.focusInputback(this, "-325px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "请输入密码");
                passwordRight = false;
            } else {
                functions.temppassword = thisValue;
                functions.doRightfunction(this, "passwordSuccess");
                functions.doRightfunction(functions.aInputs[2], "cpasswordSuccess");
                passwordRight = true;
            }
            functions.buttonsJudge();
        }
    };

    functions.codeJudge = function () {
        var oCodeinput = document.getElementById("captcha");
        oCodeinput.onfocus = function () {
            functions.focusInputback(this, "-975px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };

        oCodeinput.onblur = function () {
            functions.focusInputback(this, "-975px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "请输入验证码");
                captchaRight = false;
            } else if (thisValue.length !== 4) {
                functions.doWrongfunction(this, "验证码长度应该为4位");
                captchaRight = false;
            } else {
                functions.doRightfunction(this, "codeSuccess");
                captchaRight = true;
            }
            functions.buttonsJudge();
        };

        oCodeinput.onkeyup = function () {
            captchaRight = this.value.length === 4;
            functions.buttonsJudge();
        };
    };

    functions.buttonsJudge = function () {
        document.getElementById("submitbutton").disabled = !(usernameRight && passwordRight && captchaRight);
    };

    functions.userJudge();
    functions.passwordJudge();
    functions.codeJudge();

    document.getElementById("registerBtn").onclick = function () {
        window.location.href = "/register.html";
    };
};
