define(['angular', 'app'],
    function (angular, app) {
        app.controller('ListUserCtrl', ['$scope', '$http',
            function ($scope, $http) {
                $scope.users = [];
                $scope.req = {
                    take: 50,
                    skip: 0,
                    obj: {}
                };
                $scope.pages = [];

                $scope.genPages = function () {
                    $scope.pages = [];
                    for (var i = 0; i * $scope.req.take < $scope.limit; i++) {
                        $scope.pages.push(i);
                    }
                };

                $scope.goto = function (to) {
                    $scope.req.skip = to;
                    $scope.search();
                };

                $scope.search = function () {
                    $http.post(
                        jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.UserController.search().url,
                        $scope.req
                    ).then(function successCallback(response) {
                        $scope.error = undefined;
                        $scope.users = response.data.result;
                        $scope.limit = response.data.limit;
                        $scope.genPages();
                    }, function errorCallback(response) {
                        $scope.error = response.data;
                    });
                };

                $scope.search();
            }
        ]);
    }
);
