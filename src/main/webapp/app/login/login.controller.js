'use strict';
angular.module('login', [
	'ngAnimate',
    'ngSanitize',
    'mgcrea.ngStrap' 
]);

angular.module('login')
	.controller('loginController', ['$scope', '$http', function($scope, $http) {
		$scope.user = {};
		 
		$scope.onSubmit = function(){	
			
			if($scope.loginform.$invalid){
				return;
			}
			
			
			var response = $http.post('loginOneCustomer');
			response.success(function(data, status, headers, config) {
				alert(1);
				console.log(data);
			});
			response.error(function(data, status, headers, config) {
				alert( "Exception details: " + JSON.stringify({data: data}));
			});
			
			
			/*
			var response = $http.post('sendLoginData', $scope.user);
			response.success(function(data, status, headers, config) {
				$scope.user = data;
			});
			response.error(function(data, status, headers, config) {
				alert( "Exception details: " + JSON.stringify({data: data}));
			});*/
		}
		
	}]);