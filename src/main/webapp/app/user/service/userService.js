'use strict';
 
	angular
        .module('app')
		.factory('userService', userService);
	
	userService.$inject = ['$http','logService', '$rootScope'];
	function userService($http, logService, $rootScope){
		var service = {};
		
		service.signup = signup;
		service.login = login;
		service.fbLogin = fbLogin;
		service.loginById = loginById;
		service.update = update;
		service.changePassword = changePassword;
		service.isLoggedIn = isLoggedIn;
		service.isEmailExisted = isEmailExisted;
		
		return service;
		
		function isLoggedIn(){
			return $rootScope.globals != null && $rootScope.globals.currentUser != null;
		}
		
		function changePassword(pwd){
			return $http.post('loginCtrl/changePassword', pwd)
				.then(logService.successCallback, logService.errorCallback);
		}
		
		function fbLogin(user){
			return $http.post('loginCtrl/fbLogin', user)
			.then(logService.successCallback, logService.errorCallback);
		}
		
		function signup(user){
			return $http.post('loginCtrl/signup', user)
			.then(logService.successCallback, logService.errorCallback);
		}
		
		function login(user){
			return $http.post('loginCtrl/login', user)
			.then(logService.successCallback, logService.errorCallback);
		}
		
		function loginById(user){
			return $http.post('loginCtrl/loginById', user)
			.then(logService.successCallback, logService.errorCallback);
		}
		
		function update(user){
			return $http.post('customerDataCtrl/update', user)
			.then(logService.successCallback, logService.errorCallback);
		}

		function isEmailExisted(email){
			return $http.get('customerDataCtrl/isEmailExisted/'+ email + '/')
				.then(logService.successCallback, logService.errorCallback);
		}
		
	}