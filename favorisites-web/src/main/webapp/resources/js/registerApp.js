(function (window) {
    window.angular.module('registerApp', [])
        .controller('registerCtrl', function ($scope, $http) {
            $scope.checkUsernameExist = function () {
                //check before send
                if (!usernameValid) {
                    return;
                }
                $http.post("/usernames/" + $scope.username + "/check_exist", null)
                    .then(function (res) {
                        var element = document.getElementById("username");
                        if (res.data.success) {
                            usernameNotExist = true;
                        } else {
                            usernameNotExist = false;
                            functions.showWrongSpanAndMessage(element, res.data.errors[0].errorMsg);
                        }
                        if (usernameValid && usernameNotExist) {
                            functions.showRightSpan(element);
                        }
                        checkRegisterButton();
                    }, function (err) {
                        alert(err.data);
                    });
            };

            $scope.sendCaptcha = function () {
                // check valid
                if (!emailValid) {
                    return;
                }
                // check exist
                $http.post("/emails/" + $scope.email + "/check_exist", null)
                    .then(function (res) {
                        var element = document.getElementById("email");
                        if (res.data.success) {
                            emailNotExist = true;
                            sendEmailCaptcha($scope.email);
                        } else {
                            emailNotExist = false;
                            functions.showWrongSpanAndMessage(element, res.data.errors[0].errorMsg);
                        }
                        if (emailValid && emailNotExist) {
                            functions.showRightSpan(element);
                        }
                        checkRegisterButton();
                    }, function (err) {
                        alert(err.data);
                    });
            };

            function sendEmailCaptcha(email) {
                $http.post("/emails/" + email + "/send_captcha", null)
                    .then(function (res) {
                        console.log(res.data);
                        if (res.data.success) {
                            functions.showRightSpanAndMessage(document.getElementById("email"), "验证码已发送，请查看并填写");
                        } else {
                            functions.showWrongSpanAndMessage(document.getElementById("email"), "server went wrong");
                        }
                    }, function (err) {
                        alert(err.data);
                    });
            }

            $scope.register = function () {

            };
        })
})(window);