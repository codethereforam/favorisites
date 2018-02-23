var usernameValid = false;
var passwordValid = false;
var captchaValid = false;

function checkLoginButton() {
    document.getElementById("submitbutton").disabled = !(usernameValid && passwordValid && captchaValid);
}

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
                usernameValid = false;
            } else {
                functions.doRightfunction(this, "usernameSuccess");
                usernameValid = true;
            }
            checkLoginButton();
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
                passwordValid = false;
            } else {
                functions.temppassword = thisValue;
                functions.doRightfunction(this, "passwordSuccess");
                functions.doRightfunction(functions.aInputs[2], "cpasswordSuccess");
                passwordValid = true;
            }
            checkLoginButton();
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
                captchaValid = false;
            } else if (thisValue.length !== 4) {
                functions.doWrongfunction(this, "验证码长度应该为4位");
                captchaValid = false;
            } else {
                functions.doRightfunction(this, "codeSuccess");
                captchaValid = true;
            }
            checkLoginButton();
        };

        oCodeinput.onkeyup = function () {
            captchaValid = this.value.length === 4;
            checkLoginButton();
        };
    };

    functions.userJudge();
    functions.passwordJudge();
    functions.codeJudge();

    document.getElementById("registerBtn").onclick = function () {
        window.location.href = "/register.html";
    };
};
