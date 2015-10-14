'use strict';
var app = angular.module('app',[
	'ui.router',
	'ngCookies',
	'shell',
	'checkout',
	'login',
	'user'])
	.config(appRouter)
	.run(run);

appRouter.$inject = ['$stateProvider','$urlRouterProvider'];
function appRouter($stateProvider, $urlRouterProvider){
      $urlRouterProvider.otherwise("/index")

      $stateProvider
        .state('index', {
            url: "/index",
            templateUrl: 'layout/shell.html',
			controller:'shellController'
        })
		.state('index.checkout', {
            url: "/checkout",
            templateUrl: 'checkout/checkout.html',
            controller:'checkoutController'
        })
		.state('index.user', {
            url: "/user",
            templateUrl: 'user/user.html',
            controller:'userController'
        })
		.state('index.login', {
            url: "/login",
            templateUrl: 'login/login.html',
            controller:'loginController'
        })

}

run.$inject = ['$rootScope', '$location', '$cookies', '$http', '$timeout'];
function run( $rootScope, $location, $cookies, $http, $timeout) {
    // keep user logged in after page refresh
    $rootScope.globals = $cookies.get('globals') || {};
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
    }

	/**
	 *  redirect to login page if not logged in and trying to access a restricted page
	 */
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        var restrictedPage = inArray($location.path(), ['/user']);
        var loggedIn = $rootScope.globals.currentUser;
        if (restrictedPage && !loggedIn) {
			$timeout(function () {
				$location.path('/index/login');
			});
        }
        
        function inArray(path, comparePaths){
        	for(var i = 0; i < comparePaths.length; i++){
        		if(path.indexOf(comparePaths[i]) != -1){
        			return true;
        		}
        	}
        	return false;
        }
    });
}


