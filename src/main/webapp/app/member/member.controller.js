'use strict';
angular.module('member', [
	'ngAnimate',
	'ngSanitize',
	'mgcrea.ngStrap' 
]);

angular.module('member')
	.controller('memberController',["$scope",function($scope){
		$scope.test="1111113331"; 
	}]);