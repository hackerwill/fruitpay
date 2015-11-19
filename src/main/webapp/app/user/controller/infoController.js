'use strict';
angular.module('user')
	.controller('infoController',
			["$scope",
			 "orderService",
			 'authenticationService',
			 function($scope, orderService, authenticationService){
					
		$scope.getInfo = getInfo;
		
		function getInfo(){
			authenticationService.getUser()
				.then(function(user){
					if(user){
						$scope.user = user;
						console.log(user);
					}else{
						$location.path('/index');
					}
				});
		}
		
	}]);
