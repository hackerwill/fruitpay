'use strict';
angular.module('user')
	.controller('userController',
			["$scope", "sharedProperties", "$location", "authenticationService",
			 function($scope, sharedProperties, $location, authenticationService){
		var user = sharedProperties.getUser(); 	
		//有登入資料
		if(user){
			setUserData(user);
		//有驗證資料
		}else if(authenticationService.isCredentialsMatch()){ 
			 authenticationService.login(authenticationService.getDecodedUser())
		        .then(function(result){
		            if (result) {
		            	user = result;
		            	sharedProperties.setUser(result);
		            	setUserData(user);
		            } else {
		                flashService.error(result);
		                user.dataLoading = false;
		            }
		        });
		}else{
			$location.path('/index');
		}
		
		function setUserData(user){
			console.log(user);
			$scope.user = user;
		}
		
	}]);