(function (window) {
    window.angular.module('registerApp', [])
        .controller('registerCtrl', function ($scope, $http) {
            $scope.checkUsernameExist = function () {
                //check before send
                if(!usernameValid) {
                    return;
                }
                $http.post("/usernames/" + $scope.username + "/check_exist", null)
                    .then(function (res) {
                        var element = document.getElementById("username");
                        if (res.data.success) {
                            usernameNotExist = true;
                        } else {
                            usernameNotExist = false;
                            functions.doWrongfunction(element, res.data.errors[0].errorMsg);
                        }
                        if(usernameValid && usernameNotExist) {
                            functions.doRightfunction(element, "usernameSuccess");
                        }
                        checkRegisterButton();
                    }, function (err) {
                        alert(err.data);
                    });
            };

            $scope.sendCaptcha = function () {

            };

            $scope.register = function () {

            };
        })
})(window);