define(['angular', 'config'],
    function (angular, config) {
        return function($routeProvider) {
                           $routeProvider.
                           when('/run', {
                               templateUrl: config.templatePrefix + 'run.html',
                               controller: 'RunCtrl'
                           }).
                           when('/user/add', {
                               templateUrl: config.templatePrefix + 'addUser.html',
                               controller: 'AddUserCtrl'
                           }).
                           when('/user/edit/:id', {
                               templateUrl: config.templatePrefix + 'addUser.html',
                               controller: 'EditUserCtrl'
                           }).
                           when('/user/list', {
                               templateUrl: config.templatePrefix + 'listUser.html',
                               controller: 'ListUserCtrl'
                           }).
                           otherwise({
                               templateUrl: config.templatePrefix + 'home.html',
                               controller: 'HomePageCtrl'
                           });
                       };
    }
);
