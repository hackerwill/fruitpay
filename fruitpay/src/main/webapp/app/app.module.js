'use strict';
angular.module('app',[
	'ui.router',
	'checkout'
]); 
 
appRouter.$inject = ['$stateProvider','$urlRouterProvider'];
angular.module('app')
	.config(appRouter);
 
function appRouter($stateProvider, $urlRouterProvider){
      $urlRouterProvider.otherwise("/index")

      $stateProvider
        .state('index', {
            url: "/index",
            templateUrl: 'app/layout/shell.html'
        })
		.state('index.checkout', {
            url: "/checkout",
            templateUrl: 'app/checkout/checkout.html',
            controller:'check'
        })
		.state('index.login', {
            url: "/login",
            templateUrl: 'app/login/login.html'
        })

}
