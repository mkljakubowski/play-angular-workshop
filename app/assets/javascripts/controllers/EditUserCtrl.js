define(['angular', 'app'],
    function (angular, app) {
        app.controller('EditUserCtrl', ['$scope', '$http', '$routeParams',
            function ($scope, $http, $routeParams) {
                $scope.user = {
                    id: $routeParams.id
                };

                $http.get(jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.UserController.get($scope.user.id).url).then(
                    function successCallback(response) {
                        $scope.user = response.data;
                    },
                    function errorCallback(response) {
                    }
                );

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
