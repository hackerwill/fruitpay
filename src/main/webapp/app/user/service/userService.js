'use strict';
 
	angular
        .module('app')
		.factory('userService', userService);
	
	userService.$inject = ['$http','logService'];
	function userService($http, logService){
		var service = {};
		
		service.signup = signup;
		service.login = login;
		service.fbLogin = fbLogin;
		service.loginById = loginById;
		
		return service;
		
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
		
		
	}