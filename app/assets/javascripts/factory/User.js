define(['angular', '../app'],
    function (angular, app) {
        app.factory('User', function ($http) {

            function User(first, last) {
                this.firstName = first;
                this.lastName = last;
            }

            User.prototype.save = function () {
                return $http.post(
                    jsRoutes.org.mkljakubowski.sbtngseed.frontend.controllers.UserController.save().url,
                    this
                )
            };
 
            return User;
        });
    }
);
