(function (window) {
    window.angular.module('loginApp', [])
        .controller('loginCtrl', function ($scope, $http) {
            // init rememberMe value
            $scope.rememberMe = true;

            $scope.updateCaptcha = function () {
                $scope.captchaURL = "/captchas?id=" + new Date().getTime();
            };

            //init captcha url
            $scope.updateCaptcha();

            //try to login
            $scope.login = function () {
                console.log("login data: " + $scope.accountName + "," + $scope.password
                    + "," + $scope.captcha + "," + $scope.rememberMe);
                var data = JSON.stringify({
                    accountName: $scope.accountName,
                    password: $scope.password,
                    captcha: $scope.captcha,
                    rememberMe: $scope.rememberMe === true
                });
                $http.post("/sessions", data)
                    .then(function (res) {
                        console.log(res.data);
                        if (res.data.success) {
                            window.location.href = "/index.html";
                        } else {
                            var errors = res.data.errors;
                            for (var i in errors) {
                                var error = errors[i];
                                if (error.field === null) {
                                    error.field = "accountName";
                                }
                                var element = document.getElementById(error.field);
                                functions.showWrongSpanAndMessage(element, error.errorMsg);
                                $scope.updateCaptcha();
                                document.getElementById("captcha").value = "";
                                captchaValid = false;
                                checkLoginButton();
                            }
                        }
                    }, function (err) {
                        alert(err.data);
                    });
            };

            $scope.forget = function () {
                window.location.href = "/reset.html";
            };
        })
})(window);