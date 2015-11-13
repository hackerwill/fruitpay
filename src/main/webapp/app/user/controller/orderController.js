'use strict';
angular.module('user')
	.controller('orderController',
			["$scope",
			 function($scope){
		
		$scope.getOrder = getOrder;
		
		function getOrder(){
			alert(1);
		}
		
		
		
	}]);
