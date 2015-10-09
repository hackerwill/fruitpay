'use strict';
appRouter.$inject = ['$stateProvider','$urlRouterProvider'];
var app = angular.module('app',[
	'ui.router',
	'shell',
	'checkout',
	'login'])
	.config(appRouter);

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
		.state('index.login', {
            url: "/login",
            templateUrl: 'login/login.html',
            controller:'loginController'
        })

}
