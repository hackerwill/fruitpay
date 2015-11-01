'use strict';
angular.module('user')
	.controller('userController',
			["$scope", "sharedProperties", "$location", "authenticationService", "flashService",
			 function($scope, sharedProperties, $location, authenticationService, flashService){
		var user = sharedProperties.getUser(); 	
		//有登入資料
		if(user){
			setUserData(user);
		//有驗證資料
		}else if(authenticationService.isCredentialsMatch()){ 
			 authenticationService.loginById()
		        .then(function(result){
		            if (result) {
		            	user = result;
		            	sharedProperties.setUser(result);
		            	setUserData(user);
		            } else {
		                flashService.error(result);
		            }
		        });
		}else{
			$location.path('/index');
		}
		
		function setUserData(user){
			$scope.user = user;
		}
		
	}]);