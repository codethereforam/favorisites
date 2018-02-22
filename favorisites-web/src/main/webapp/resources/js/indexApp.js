(function (window) {
    window.angular.module('indexApp', [])
        .controller('indexCtrl', function ($scope, $http) {
            $scope.logout = function () {
                $http.delete("/sessions")
                    .then(function (res) {
                        if(res.data.success) {
                            window.location.href = "/login.html";
                        } else {
                            alert("server went wrong...");
                        }
                    }, function (err) {
                        alert(err.data);
                    });
            };
        })
})(window);