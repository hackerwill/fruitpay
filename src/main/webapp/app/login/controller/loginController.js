'use strict';
angular.module('login')
	.controller('loginController', loginController);

loginController.$inject = ['$scope', '$location', '$timeout', 'userService', 'authenticationService', 'flashService'];	
function loginController($scope, $location, $timeout, userService, authenticationService, flashService){
		$scope.isLoginPage = true;
		$scope.user = {};
		
		(function initController() {
	        // reset login status
	        authenticationService.clearCredentials();
	    })();

		/**
		 * 點擊切換註冊及登入頁面
		 * */
		$scope.togglePage = function(){
			$scope.isLoginPage = !$scope.isLoginPage;
		}
		
		/**
		 * 登入
		 * */
		$scope.onLoginSubmit = function() {
			var user = $scope.user;
			user.dataLoading = true;
			
			console.log(user);
			
	        authenticationService.login(user.email, user.password, function (response) {
				console.log(response);
	            if (response.success) {
	                authenticationService.setCredentials(user.email, user.password);
	                $location.path('/index/user');
	            } else {
	                flashService.error(response.message);
	                user.dataLoading = false;
	            }
	        });
	    };

	    /**
	     * 註冊
	     * */
	    $scope.onSignupSubmit = function() {
	    	var user = $scope.user;
	    	user.dataLoading = true;
			
			console.log(user);
			
	        userService.create(user)
	            .then(function (response) {
					console.log(response);
	                if (response.success) {
	                    flashService.success('Registration successful', true);
						$scope.isLoginPage = true;
	                } else {
	                    flashService.error(response.message);
	                    user.dataLoading = false;
	                }
	            });
	    }
}

