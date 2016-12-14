define(['angular', 'config', 'angular-route', 'jsMessages', 'jsRoutes', 'angular-websocket'], function (angular) {
    var app = angular.module('ngApp', ['ngRoute', 'ngWebSocket']);
    return app;
});
