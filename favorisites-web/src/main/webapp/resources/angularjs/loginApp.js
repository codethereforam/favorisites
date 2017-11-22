/**
 * Created by Administrator on 2017/11/21.
 */
(function(angular){
    'use strict';
    var loginApp = angular.module('loginApp',[]);
    loginApp.controller('MainController',['$scope','$http',function($scope,$http){
        $scope.user = {};
    }]);
    loginApp.controller('formController',['$scope','$http',function($scope,$http){
        $scope.captchaUrl = "/captchas?ran" + Math.random();
        $scope.refreshCaptcha = function(){
            $scope.captchaUrl = "/captchas?ran" + Math.random();
        }
        $scope.user.checkValue = '0';
        $scope.login = function(){
            $http({
                method: 'post',
                url: '/sessions',
                data: {
                    "accountName": $scope.user.usernameValue,
                    "captcha": $scope.user.codeValue,
                    "password": $scope.user.passwordValue,
                    "rememberMe": $scope.user.checkValue
                }
            }).then(function successCallback(response) {
                console.log(response.data.errors);
            }, function errorCallback(response) {
                console.log(response);
            });
        }
    }]);
})(angular);