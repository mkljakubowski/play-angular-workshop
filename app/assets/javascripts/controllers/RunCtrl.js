define(['angular', 'app'],
    function (angular, app) {
        app.controller('RunCtrl', ['$scope', '$http', '$websocket',
            function ($scope, $http, $websocket) {
                $scope.statuses = [];

                $scope.stopCalc = function () {
                    $http.get(jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.DummyController.stop().url);
                };

                $scope.startCalc = function () {
                    var dataStream = $websocket('ws://' + window.location.host + jsRoutes.com.leonteq.productmetricsengine.frontend.controllers.DummyController.run().url);

                    dataStream.onMessage(function (message) {
                        var msg = JSON.parse(message.data);
                        $scope.statuses.push(msg);
                    });
                };
            }
        ]);
    }
);
