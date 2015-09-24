'use strict';
angular.module('checkout', [
	'ngAnimate',
	'ngSanitize',
	'mgcrea.ngStrap' 

]);
angular.module('checkout')
	.controller('check',["$scope",function($scope){
		$scope.test="1111111"; 
	}]);