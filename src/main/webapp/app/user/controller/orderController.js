'use strict';
angular.module('user')
	.controller('orderController',
			["$scope",
			 "orderService",
			 function($scope, orderService){
		
		$scope.getOrder = getOrder;
		
		function getOrder(){
			orderService.getOrderByCustomerId(6)
				.then(function(result){
					console.log(result);
					if(result){
						$scope.userOrders = result;
					}
					
				});
		}
		
		
		
	}]);
