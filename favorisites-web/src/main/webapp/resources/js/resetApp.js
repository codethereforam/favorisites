(function (window) {
    window.angular.module('resetApp', [])
        .controller('resetCtrl', function ($scope, $http) {
            // init page
            $scope.step = [true, false];

            $scope.sendCaptcha = function () {
                // check valid
                // if (!emailValid) {
                //     return;
                // }

                // check exist
                $http.post("/users/" + $scope.accountName + "/check_exist", null)
                    .then(function (res) {
                        var element = document.getElementById("accountName");
                        if (res.data.success) {
                            // emailNotExist = true;
                            sendEmailCaptcha();
                        } else {
                            // emailNotExist = false;
                            functions.showWrongSpanAndMessage(element, res.data.errors[0].errorMsg);
                        }
                        // if (emailValid && emailNotExist) {
                        //     functions.showRightSpan(element);
                        // }
                        // checkRegisterButton();
                    }, function (err) {
                        alert(err.data);
                    });
            };

            function sendEmailCaptcha() {
                $http.post("/emails/send_captcha", null)
                    .then(function (res) {
                        console.log(res.data);
                        if (res.data.success) {
                            functions.showRightSpanAndMessage(document.getElementById("accountName"), "验证码已发送，请查看并填写");
                        } else {
                            functions.showWrongSpanAndMessage(document.getElementById("accountName"), "server went wrong");
                        }
                    }, function (err) {
                        alert(err.data);
                    });
            }

            function retrieveAndSetUsername() {
                $http.get("/sessions/user")
                    .then(function (res) {
                        console.log(res.data);
                        if (res.data !== null) {
                            $scope.username = res.data.username;
                        } else {
                            $scope.step = [true, false];
                        }
                    }, function (err) {
                        alert(err.data);
                    });
            }

            $scope.forget = function () {
                $http.post("/users/forget_password", JSON.stringify({
                    emailCaptcha: $scope.emailCaptcha
                })).then(function (res) {
                    console.log(res.data);
                    if (res.data.success) {
                        // jump to next step
                        $scope.step = [false, true];
                        retrieveAndSetUsername();
                    } else {
                        functions.showWrongSpanAndMessage(document.getElementById("emailCaptcha"), res.data.errors[0].errorMsg);
                    }
                }, function (err) {
                    alert(err.data);
                });
            };

            $scope.resetPassword = function () {
                $http.post("/users/reset_password", JSON.stringify({
                    password: $scope.password,
                    confirmedPassword: $scope.confirmedPassword
                })).then(function (res) {
                    console.log(res.data);
                    if (res.data.success) {
                        window.location.href = "/login.html";
                    } else {
                        functions.showWrongSpanAndMessage(document.getElementById("password"), res.data.errors[0].errorMsg);
                    }
                }, function (err) {
                    alert(err.data);
                });
            };
        })
})(window);