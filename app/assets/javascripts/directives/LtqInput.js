define(['angular', 'app'],
    function (angular, app) {
        app.directive('ltqInput', function () {
            let controller = ['$scope', function ($scope) {
                $scope.id = Math.random().toString(36).substring(7);
            }];

            return {
                restrict: 'E',
                templateUrl: '/public/javascripts/templates/ltqInput.html',
                controller: controller,
                scope: {
                    ngModel: '=',
                    type: '@',
                    name: '@'
                }
            }
        });
    }
);
