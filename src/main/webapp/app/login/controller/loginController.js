'use strict';
angular.module('login')
	.controller('loginController', loginController);

loginController.$inject = 
	['$rootScope', 
	 '$scope', 
	 '$location', 
	 'userService', 
	 'authenticationService', 
	 'flashService',  
	 'logService',
	 'sharedProperties',
	 "facebookLoginService"];	

function loginController(
		$rootScope, $scope, $location, userService, 
		authenticationService, flashService, 
		logService, sharedProperties, facebookLoginService){
		$scope.isLoginPage = true;
		$scope.user = {};

		
		(function(){
			if($rootScope.globals.currentUser || null){
				$location.path('/index/user');
			}
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
			
	        authenticationService.login(user)
	        .then(function(result){
	            if (result) {
	            	sharedProperties.setUser(result);
	                $location.path('/index/user');
	                location.reload();
	            } else {
	                flashService.error(result);
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
	                    logService.showSuccess("歡迎您成為我們的會員，請再次點選登入");
						$scope.isLoginPage = true;
	                } else {
	                    flashService.error(success);
	                    user.dataLoading = false;
	                }
	            });
	    }
		
		/**臉書註冊登入**/
	    $scope.checkLoginState = function() {
	    	facebookLoginService.login()
	    		.then(function(response){
					if(response){
						var user = {};
						user.firstName = response.firstName ? response.firstName : response.name;
						user.email = response.email;
						user.fbId = response.id;
						console.log(1234);
						console.log(user)
						authenticationService.fbLogin(user);
					}
					
				});
        }
	    /**臉書註冊登出**/
	    $scope.FBlogout = function() {
	    	facebookLoginService.FBlogout();
        }
		
		/*function facebookLoginService() {
	
			var service = {};

			service.checkLoginState = checkLoginState;		
			service.FBlogout = FBlogout;
			
			return service;
	
			// This function is called when someone finishes with the Login
			// Button.  See the onlogin handler attached to it in the sample
			// code below.
			function checkLoginState(callback) {
				console.log("in checkLoginState");
				FB.getLoginStatus(function(response) {
					statusChangeCallback(response, callback);
				});
			}
	  
			function statusChangeCallback(response, callback) {		
				console.log('statusChangeCallback');
				console.log(response);
				// The response object is returned with a status field that lets the
				// app know the current login status of the person.
				// Full docs on the response object can be found in the documentation
				// for FB.getLoginStatus().
				if (response.status === 'connected') {
					// Logged into your app and Facebook.
					console.log('aleady login and authorized');
				} else if (response.status === 'not_authorized') {
					// The person is logged into Facebook, but not your app.
					console.log('Please log into this app.');	    	
				} else {
					// The person is not logged into Facebook, so we're not sure if
					// they are logged into this app or not.
					console.log('Please log intoFacebook');	    	
				}	
				FBlogin(callback);
			}
	  
			function FBlogin(callback){		  
				FB.login(function(response) {
	    	    if (response.authResponse) {
					console.log('Welcome!  Fetching your information.... ');
					FB.api('/me', function(response) {
						console.log('Good to see you, ' + response.name + '.');
						console.log(response.email);
						console.log(response);
						var access =  FB.getAuthResponse() ;
						console.log(access);
						callback(response);
					});
	    	    } else {
					console.log('User cancelled login or did not fully authorize.');
					callback(false);
	    	    }
	    	}, {scope: 'public_profile,email'});		
		}
	 
		function FBlogout(){
			console.log('in logged out ');
		  	FB.logout(function(response) {
	        // Person is now logged out
				console.log('already logged out ');
				console.log(response);
		    });
		}
	    
	}*/
	
}
