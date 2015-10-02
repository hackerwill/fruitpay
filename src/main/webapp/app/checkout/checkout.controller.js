'use strict';
angular.module('checkout', [
	'ngAnimate',
	'ngSanitize',
	'mgcrea.ngStrap' 
]);

angular.module('checkout')
	.controller('checkoutController',["$scope",function($scope){
		$scope.test="1111113331"; 
	}]);