define(['angular', 'app'],
    function (angular, app) {
        function ltqInputCtrl($scope) {
            const ctrl = this;

            ctrl.id = $scope.$id;
            $('[data-toggle="tooltip"]').tooltip();
        }

        app.component('ltqInput', {
            require: {
                form: "^form"
            },
            restrict: 'E',
            templateUrl: '/public/javascripts/templates/ltqInput.html',
            controller: ltqInputCtrl,
            bindings: {
                ngModel: '=',
                name: '@'
            }
        });
    }
);
