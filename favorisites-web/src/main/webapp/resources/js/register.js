var usernameValid = false;
var usernameNotExist = true;
var passwordValid = false;
var confirmedPasswordValid = false;
var emailValid = false;
var emailCaptchaValid = false;

function checkEmailSendButton() {
    document.getElementById("codebutton").disabled = !emailValid;
}

function checkRegisterButton() {
    console.log({
        "usernameValid": usernameValid,
        "usernameNotExist": usernameNotExist,
        "passwordValid": passwordValid,
        "confirmedPasswordValid": confirmedPasswordValid,
        "emailValid": emailValid,
        "emailCaptchaValid": emailCaptchaValid
    });
    document.getElementById("submitbutton").disabled = !(usernameValid && passwordValid &&
        confirmedPasswordValid && emailValid && emailCaptchaValid && usernameNotExist);
}

window.onload = function () {
    functions.userJudge = function () {
        var oUserinput = document.getElementById("username");

        oUserinput.onfocus = function () {
            functions.focusInputback(this, "0px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };

        oUserinput.onkeyup = function () {
            functions.blurInputback(this, "0px 0px");
            var thisValue = this.value;
            //非空,不能有空格
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0 || thisValue.indexOf(" ") !== -1) {
                functions.doWrongfunction(this, "此处不能留空");
                usernameValid = false;
                // 5-15个字符
            } else if (thisValue.length < 5 || thisValue.length > 15) {
                functions.doWrongfunction(this, "长度应为5-15个字符，请勿包含姓名/身份证/银行卡等隐私信息");
                usernameValid = false;
                // 仅中、英文字母（不区分大小写）、数字和下划线
            } else if (/^[0-9a-zA-Z\u4e00-\u9fa5_]{5,15}$/.test(thisValue) === false) {
                functions.doWrongfunction(this, "用户名仅支持中英文、数字和下划线");
                usernameValid = false;
            } else {
                usernameValid = true;
            }
            if (usernameValid && usernameNotExist) {
                functions.doRightfunction(this, "usernameSuccess");
            }
            checkRegisterButton();
        }
    };

    functions.passwordJudge = function () {
        var oPasswordinput = document.getElementById("password");

        oPasswordinput.onfocus = function () {
            functions.focusInputback(this, "-325px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };

        oPasswordinput.onkeyup = oPasswordinput.onblur = function () {
            functions.focusInputback(this, "-325px 0px");
            var thisValue = this.value;
            //不能全为空格，首尾允许有空格
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "此处不能留空");
                passwordValid = false;
                //6~16个字符
            } else if (thisValue.length < 6 || thisValue.length > 16) {
                functions.doWrongfunction(this, "长度应为6-16个字符");
                passwordValid = false;
                //仅字母（区分大小写）、数字及英文标点
            } else if (/^[0-9a-zA-Z\u4e00-\u9fa5_]{6,16}$/.test(thisValue) === false) {
                functions.doWrongfunction(this, "密码仅支持字母、数字及标点符号");
                passwordValid = false;
            } else if (thisValue !== functions.aInputs[2].value) {
                functions.temppassword = thisValue;
                functions.doWrongfunction(functions.aInputs[2], "两个密码不匹配");
                functions.showRightspan(this);
                functions.resetOutline(this);
                passwordValid = false;
            } else {
                functions.temppassword = thisValue;
                functions.doRightfunction(this, "passwordSuccess");
                functions.doRightfunction(functions.aInputs[2], "cpasswordSuccess");
                passwordValid = true;
            }
            checkRegisterButton();
        }
    };

    functions.cpasswordJudge = function () {
        var oCpasswordinput = document.getElementById("confirmedPassword");

        oCpasswordinput.onfocus = function () {
            functions.focusInputback(this, "-325px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };

        oCpasswordinput.onkeyup = oCpasswordinput.onblur = function () {
            functions.focusInputback(this, "-325px 0px");
            var thisValue = this.value;
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "此处不能留空");
                confirmedPasswordValid = false;
            } else if (functions.temppassword !== thisValue) {
                functions.doWrongfunction(this, "两个密码不匹配");
                confirmedPasswordValid = false;
            } else {
                functions.doRightfunction(this, "cpasswordSuccess");
                confirmedPasswordValid = true;
                passwordValid = true;
            }
            checkRegisterButton();
        }
    };

    functions.emailJudge = function () {
        var oEmailinput = document.getElementById("email");

        oEmailinput.onfocus = function () {
            functions.focusInputback(this, "-650px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };

        oEmailinput.onkeyup = oEmailinput.onblur = function () {
            functions.focusInputback(this, "-650px 0px");
            var thisValue = this.value;
            // 非空
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "此处不能留空");
                emailValid = false;
                // 邮箱长度不超过50
            } else if (thisValue.length > 50) {
                functions.doWrongfunction(this, "邮箱长度不能超过50");
                emailValid = false;
                // 邮箱格式验证
            } else if (functions.checkEmail(thisValue) === false) {
                functions.doWrongfunction(this, "邮箱格式不符合要求");
                emailValid = false;
                // 不能与已有邮箱重复
            } else {
                functions.doRightfunction(this, "emailSuccess");
                emailValid = true;
            }
            checkEmailSendButton();
        }
    };

    functions.codeJudge = function () {
        var oCodeinput = document.getElementById("emailCaptcha");

        oCodeinput.onfocus = function () {
            functions.focusInputback(this, "-975px -55px");
            functions.resetOutline(this);
            functions.hideCheckspan(this);
        };

        oCodeinput.onkeyup = oCodeinput.onblur = function () {
            functions.focusInputback(this, "-975px 0px");
            var thisValue = this.value;
            //非空
            if (thisValue == null || thisValue.replace(/\s/g, "").length === 0) {
                functions.doWrongfunction(this, "此处不能留空");
                emailCaptchaValid = false;
            } else if (thisValue.length !== 6) {
                functions.doWrongfunction(this, "验证码长度为6位");
                emailCaptchaValid = false;
            } else if (/^[0-9a-zA-Z]+$/.test(thisValue) === false) {
                functions.doWrongfunction(this, "验证码只能包括字母或数字");
                emailCaptchaValid = false;
            } else {
                functions.doRightfunction(this, "codeSuccess");
                emailCaptchaValid = true;
            }
            checkRegisterButton();
        }
    };

    functions.userJudge();
    functions.passwordJudge();
    functions.cpasswordJudge();
    functions.emailJudge();
    functions.codeJudge();
};
