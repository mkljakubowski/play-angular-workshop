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

require(['jquery', 'angular', 'app', 'routes', 'config', 'bootstrap', 'controllers', 'directives', 'factories', 'services'], function($, angular, app, routes, config) {

    app.config(['$routeProvider', routes]);

    app.config(function ($locationProvider) {
        $locationProvider.html5Mode(true);
    });

    angular.element(document).ready(function() {
        angular.bootstrap(document, ['ngApp']);
    });
});