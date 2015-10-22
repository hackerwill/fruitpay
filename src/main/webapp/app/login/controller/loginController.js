'use strict';
angular.module('login')
	.controller('loginController', loginController);

loginController.$inject = ['$scope', '$location', '$timeout', 'userService', 'authenticationService', 'flashService', 'ngDialog', '$alert'];	
function loginController($scope, $location, $timeout, userService, authenticationService, flashService, ngDialog, $alert){
		$scope.isLoginPage = true;
		$scope.user = {};
		
		/*(function initController() {
	        // reset login status
	        authenticationService.clearCredentials();
	    })();*/

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
			
	        authenticationService.login(user)
	        .then(function(success){
	            if (success) {
	                $location.path('/index/user');
	            } else {
	                flashService.error(success);
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
			
	        userService.signup(user)
	            .then(function (success) {
	                if (success) {
	                    flashService.success('Registration successful', success);
	                    $alert({
	        				title: '歡迎您成為我們的會員，請再次點選登入',
	        				placement: 'top',
	        				type: 'success',
	        				duration: '3',
	        				animation: 'am-fade-and-scale'
	        			});
						$scope.isLoginPage = true;
	                } else {
	                    flashService.error(success);
	                    user.dataLoading = false;
	                }
	            });
	    }
}

