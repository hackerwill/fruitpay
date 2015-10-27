'use strict';
var app = angular.module('app',[
	'ui.router',
	'ngCookies',
	'ngDialog',
	'ngAnimate',
	'duScroll',
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
        .state('index.logout', {
            url: "/logout",
            templateUrl: 'login/logout.html',
            controller:'logoutController'
        })

}

run.$inject = ['$rootScope', '$location', '$http', '$timeout'];
function run( $rootScope, $location, $http, $timeout) {
    // keep user logged in after page refresh
	$rootScope.globals = {};
	if(localStorage.fruitpayGlobals){
		$rootScope.globals = JSON.parse(localStorage.fruitpayGlobals) || {};
	}
	
    if ($rootScope.globals.currentUser) {
        $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata; // jshint ignore:line
    }

	/**
	 *  redirect to login page if not logged in and trying to access a restricted page
	 */
    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        var restrictedPage = inArray($location.path(), ['/user']);
        var loggedIn = $rootScope.globals.currentUser || null;
        
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

app.animation('.slide-toggle', ['$animateCss', function($animateCss) {
    return {
        addClass: function(element, className, doneFn) {
            if (className == 'ng-hide') {
                var animator = $animateCss(element, {                    
                    to: {height: '0px', opacity: 0}
                });
                if (animator) {
                    return animator.start().finally(function() {
                        element[0].style.height = '';
                        doneFn();
                    });
                }
            }
            doneFn();
        },
        removeClass: function(element, className, doneFn) {
            if (className == 'ng-hide') {
                var height = element[0].offsetHeight;
                var animator = $animateCss(element, {
                    from: {height: '0px', opacity: 0},
                    to: {height: height + 'px', opacity: 1}
                });
                if (animator) {
                 return animator.start().finally(doneFn);
                }
            }
            doneFn();
        }
    };
}]);

