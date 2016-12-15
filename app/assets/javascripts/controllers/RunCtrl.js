define(['angular', 'app'],
    function (angular, app) {
        app.controller('RunCtrl', ['$scope', '$http', '$websocket',
            function ($scope, $http, $websocket) {
                var dataStream;
                $scope.statuses = [];

                $scope.stopCalc = function () {
                    $http.get(jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.DummyController.stop().url);
                    dataStream && dataStream.close();
                };

                $scope.startCalc = function () {
                    $scope.statuses = [];
                    dataStream = $websocket(jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.DummyController.run().webSocketURL());

                    dataStream.onMessage(function (message) {
                        var msg = JSON.parse(message.data);
                        $scope.statuses.push(msg);
                    });
                };
            }
        ]);
    }
);
