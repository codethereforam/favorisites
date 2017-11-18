(function(angular){
    'use strict';
    var resetApp = angular.module('resetApp',[]);
    resetApp.controller('MainController',['$scope',function($scope){
        $scope.formIf =[true,false,false];
    }]);

})(angular)