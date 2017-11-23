(function(angular){
    'use strict';
    var resetApp = angular.module('resetApp',[]);
    resetApp.controller('MainController',['$scope',function($scope){

    }]);
    resetApp.controller('formController',['$scope','$http','$interval',function($scope,$http,$interval){
        $scope.check_accountname_exist = function () {
            var usernameRes = true;
            $http({
                method: 'post',
                url: '/users/'+$scope.accountnameValue+'/check_exist'
            }).then(function successCallback(response) {
                usernameRes = response.data.success;
            }, function errorCallback(response) {
                console.log(response);
            });
        }
        $scope.text = "发送";
        $scope.getCode = function () {
            $http({
                method: 'post',
                url: '/emails/send_captcha'
            }).then(function successCallback(response) {
                console.log("getCode " + response.data.success);
            }, function errorCallback(response) {
                console.log(response);
                //假设没有这种出错的情况，，，后期补，相信杨安宇的后台！
            });
            $scope.time = 3;
            $scope.sendIsable = true;
            var timer = $interval(function () {
                $scope.time--;
                $scope.text = $scope.time + 's';
                if ($scope.time == 0) {
                    $interval.cancel(timer);
                    $scope.sendIsable = false;
                    $scope.text = "发送"
                }
            }, 1000);
        }
        $scope.formIf = [true,false,false];
        $scope.captchaUrl = "/captchas?ran" + Math.random();
        $scope.refreshCaptcha = function(){
            $scope.captchaUrl = "/captchas?ran" + Math.random();
        }
        $scope.first_to_second = function(){
            $scope.formIf = [false,true,false];
        }
        $scope.second_to_third = function(){
            $scope.formIf = [false,false,true];
            $http({
                method :'post',
                url : '/users/reset_password',
                data:{
                    "confirmedPassword": $scope.cPasswordValue,
                    "password":$scope.passwordValue
                }
            }).then(function(response){
                console.log(response);
            },function(response){
                console.log(response);
            });
        }
    }]);

})(angular)