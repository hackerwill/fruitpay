'use strict';
angular.module('login', [
	'ngAnimate',
    'ngSanitize',
    'mgcrea.ngStrap' 
]);

angular.module('login')
	.controller('loginController', ['$scope', '$http', function($scope, $http) {
		$scope.isLoginPage = true;
		$scope.user = {};
		
		/**
		 * 點擊切換註冊及登入頁面
		 * */
		$scope.togglePage = function(){
			$scope.isLoginPage = !$scope.isLoginPage;
		}
		/**
		 * 登入提交動作
		 * */
		$scope.onLoginSubmit = function(){	
			
			if($scope.loginform.$invalid){
				return;
			}
			console.log($scope.user);
			
			var response = $http.post('loginController/login', $scope.user);
			response.success(function(data, status, headers, config) {
				
				console.log(data);
			});
			response.error(function(data, status, headers, config) {
				alert( "Exception details: " + JSON.stringify({data: data}));
			});
		}
		
		/**
		 * 註冊提交動作
		 * */
		$scope.onSignupSubmit = function(){	
			
			if($scope.signupForm.$invalid){
				return;
			}
			console.log($scope.user);
			
			var response = $http.post('loginController/signup', $scope.user);
			response.success(function(data, status, headers, config) {
				
				console.log(data);
			});
			response.error(function(data, status, headers, config) {
				alert( "Exception details: " + JSON.stringify({data: data}));
			});
			
			
			
		}
		
	}]);