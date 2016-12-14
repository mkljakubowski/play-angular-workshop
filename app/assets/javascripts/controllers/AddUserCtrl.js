define(['angular', 'app'],
    function (angular, app) {
        app.controller('AddUserCtrl', ['$scope', '$http',
            function ($scope, $http) {

                $scope.user = {};

                $scope.save = function () {
                    $http.post(
                        jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.UserController.save().url,
                        $scope.user
                    ).then(function successCallback(response) {
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
