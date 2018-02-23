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
            functions.focusInputBack(this, "0px -55px");
            functions.resetOutline(this);
            functions.hideCheckSpan(this);
        };
        oUserinput.onblur = function () {
            functions.blurInputBack(this, "0px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.showWrongSpanAndMessage(this, "请填写账户名");
                usernameValid = false;
            } else {
                functions.showRightSpan(this);
                usernameValid = true;
            }
            checkLoginButton();
        }
    };

    functions.passwordJudge = function () {
        var oPasswordinput = document.getElementById("password");
        oPasswordinput.onfocus = function () {
            functions.focusInputBack(this, "-325px -55px");
            functions.resetOutline(this);
            functions.hideCheckSpan(this);
        };
        oPasswordinput.onblur = function () {
            functions.focusInputBack(this, "-325px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.showWrongSpanAndMessage(this, "请输入密码");
                passwordValid = false;
            } else {
                functions.temppassword = thisValue;
                functions.showRightSpan(this);
                functions.showRightSpan(functions.aInputs[2]);
                passwordValid = true;
            }
            checkLoginButton();
        }
    };

    functions.codeJudge = function () {
        var oCodeinput = document.getElementById("captcha");
        oCodeinput.onfocus = function () {
            functions.focusInputBack(this, "-975px -55px");
            functions.resetOutline(this);
            functions.hideCheckSpan(this);
        };

        oCodeinput.onblur = function () {
            functions.focusInputBack(this, "-975px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.showWrongSpanAndMessage(this, "请输入验证码");
                captchaValid = false;
            } else if (thisValue.length !== 4) {
                functions.showWrongSpanAndMessage(this, "验证码长度应该为4位");
                captchaValid = false;
            } else {
                functions.showRightSpan(this);
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
