'use strict';
appRouter.$inject = ['$stateProvider','$urlRouterProvider'];
var app = angular.module('app',[
	'ui.router',
	'checkout',
	'login'])
	.directive('resizemenu', function ($window) {
	    return function (scope, element) {
	    	console.log(1);
	        var w = angular.element($window);
	        scope.getWindowDimensions = function () {
	            return {
	                'h': window.innerHeight,
	                'w': window.innerWidth
	            };
	        };
	        scope.$watch(scope.getWindowDimensions, function (newValue, oldValue) {
	        	console.log(111);
	            scope.windowHeight = newValue.h;
	            scope.windowWidth = newValue.w;
	            
	            scope.style = function () {
	                return {
	                    'height': (newValue.h - 100) + 'px',
	                    'width': (newValue.w - 100) + 'px'
	                };
	            };

	        }, true);

	        window.onresize = function(){
				scope.$apply(scope.getWindowDimensions);
	        };
	    }
	})
	.config(appRouter);

function appRouter($stateProvider, $urlRouterProvider){
      $urlRouterProvider.otherwise("/index")

      $stateProvider
        .state('index', {
            url: "/index",
            templateUrl: 'layout/shell.html'
        })
		.state('index.checkout', {
            url: "/checkout",
            templateUrl: 'checkout/checkout.html',
            controller:'checkoutController'
        })
		.state('index.login', {
            url: "/login",
            templateUrl: 'login/login.html',
            controller:'loginController'
        })

}

//check whether to show menu when resize
