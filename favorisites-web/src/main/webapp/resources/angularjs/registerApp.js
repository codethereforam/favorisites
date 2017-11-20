(function(angular){
    var registerApp = angular.module('registerApp',[]);
    registerApp.controller('MainController',['$scope','$interval','$http',function($scope,$interval,$http){

    }]);
    registerApp.controller('formController',['$scope','$http','$interval',function($scope,$http,$interval){
        $scope.check_email_exist = function(){
            var emailRes = true;
            $http({
                method:'post',
                url:'/emails/'+$scope.emailValue+'/check_exist'
            }).then(function successCallback(response) {
                emailRes = response.data.success;
            }, function errorCallback(response) {
                console.log(response);
                //假设没有这种出错的情况，，，后期补，相信杨安宇的后台！
            });
        }
        $scope.check_username_exist = function(){
            var usernameRes = true;
            $http({
                method:'post',
                url:'/usernames/'+$scope.usernameValue+'/check_exist'
            }).then(function successCallback(response) {
                usernameRes = response.data.success;
            }, function errorCallback(response) {
                console.log(response);
            });
        }
        $scope.text = "发送";
        $scope.getCode =  function(){
            $http({
                method:'post',
                url:'/emails/'+$scope.emailValue+'/send_captcha'
            }).then(function successCallback(response) {
                console.log("getCode "+response.data.success);
            }, function errorCallback(response) {
                console.log(response);
                //假设没有这种出错的情况，，，后期补，相信杨安宇的后台！
            });
            $scope.time = 3;
            $scope.sendIsable = true;
            var timer = $interval(function(){
                $scope.time --;
                $scope.text = $scope.time + 's';
                if($scope.time == 0 ){
                    $interval.cancel(timer);
                    $scope.sendIsable = false;
                    $scope.text = "发送"
                }
            },1000);
        }
        $scope.postJson = function(){
            $http({
                 "method":'post',
                 "url":'/users',
                 "confirmedPassword": $scope.cpasswordValue,
                 "email":$scope.emailValue ,
                 "emailCaptcha": $scope.codeValue,
                  "password": $scope.passwordValue,
                  "sex": 1,
                  "username": $scope.usernameValue
            }).then(function successCallback(response) {
                console.log(response.data.errors);
            }, function errorCallback(response) {
                console.log(11);
                console.log(response);
            });
        }
    }]);
/*    registerApp.controller('formController',['$scope',function($scope){
        $scope.usernameErrorList = {
            errorVal:'default',
            errorText:[
                {name:'required',tip:'用户名不能为空'},
                {name:'length',tip:'用户名字符长度应为闭区间 [5-15]'},
                {name:'format',tip:'只支持英文字母（不区分大小写）、数字和下划线'},
                {name:'ifExist',tip:'真抱歉！这个ID太抢手已经被人先注册啦，换一个试试吧'}
            ],
            change:function(err){
                }
            }

            passwordError1:'密码不能为空或带有空格',
            passwordError2:'密码字符长度应为闭区间 [6-16]',
            passwordError3:'只支持英文字母（不区分大小写）、数字和下划线',
            passwordError5:'两次输入的密码不一致',
            cpasswordError1:'此处不能为空',
            cpasswordError2:'两次输入的密码不一致',
            emailError1:'邮箱不能为空且不能带有空格',
            emailError2:'邮箱的长度不能超过50',
            emailError3:'输入的邮箱格式不正确',
            emailError4:'该邮箱已经被注册'

    }])*/
})(angular);