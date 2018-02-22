(function (window) {
    window.angular.module('loginApp', [])
        .controller('loginCtrl', function ($scope, $http) {
            $scope.updateCaptcha = function () {
                $scope.captchaURL = "/captchas?id=" + new Date().getTime();
            };
            //init captcha url
            $scope.updateCaptcha();
        })
})(window);