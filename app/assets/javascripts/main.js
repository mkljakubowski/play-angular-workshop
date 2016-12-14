requirejs.config({
    baseUrl: '/public/javascripts',
    paths: {
        jquery: '../lib/jquery/jquery.min',
        bootstrap: '../lib/bootstrap/js/bootstrap.min',
        angular: '../lib/angularjs/angular.min',
        'angular-route': '../lib/angularjs/angular-route.min',
        'angular-websocket': 'angular-websocket/angular-websocket.min',
        jsMessages: '../jsMessages',
        jsRoutes: '../jsRoutes'
    },
    shim: {
        bootstrap: ['jquery'],
        angular: {
            requires: ['jquery'],
            exports: 'angular'
        },
        'angular-route': ['angular'],
        'angular-websocket': ['angular'],
        jsMessages: {
            exports: 'jsMessages'
        },
        jsRoutes: {
            exports: 'jsRoutes'
        }
    }
});

require(['jquery', 'angular', 'app', 'config', 'bootstrap', 'controllers', 'directives'], function($, angular, app, config) {

    app.config(['$routeProvider',
        function($routeProvider) {
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
        }]);

    app.config(function ($locationProvider) {
        $locationProvider.html5Mode(true);
    });

    angular.element(document).ready(function() {
        angular.bootstrap(document, ['ngApp']);
    });
});