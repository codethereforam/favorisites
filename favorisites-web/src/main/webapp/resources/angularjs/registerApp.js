(function(angular){
    var registerApp = angular.module('registerApp',[]);
    registerApp.controller('MainController',['$scope','$interval',function($scope,$interval){
        $scope.isDisable = false;
        var isNow = 30;
        console.log(11);
        $scope.codebuttonText = "发送";
        $scope.getCode = function($scope){

            $scope.isDisable = true;
            var timer = $interval(function(){
                $scope.codebuttonText = isNow +'s';
                isNow--;
                if(isNow===0){
                    $interval.cancel(timer);
                    $scope.isDisabled = false;
                    $scope.codebuttonText = "重新发送"
                }
            },1000);
        }

    }]);
})(angular);