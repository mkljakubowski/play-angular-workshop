define(['angular', 'app'],
    function (angular, app) {
        app.controller('SearchCtrl', ['$scope', '$http',
            function ($scope, $http) {
                $scope.query = {};

                $scope.search = function (query) {
                    return $http.post(jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.UserController.search().url, query);
                };
            }
        ]);
    }
);
