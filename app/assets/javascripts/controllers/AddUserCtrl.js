define(['angular', 'app'],
    function (angular, app) {
        app.controller('AddUserCtrl', ['$scope', '$http', 'User',
            function ($scope, $http, User) {

                $scope.user = new User();

                $scope.save = function () {
                    $scope.user.save().then(function successCallback(response) {
                        $scope.success = 'Saved';
                        $scope.error = undefined;
                    }, function errorCallback(response) {
                        $scope.success = undefined;
                        $scope.error = response.data;
                    });
                };
            }
        ]);
    }
);
