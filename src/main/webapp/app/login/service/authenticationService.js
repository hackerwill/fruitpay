'use strict';
 
angular.module('app')
	.factory('authenticationService', authenticationService);

authenticationService.$inject = [ '$http', '$rootScope', '$timeout', 'userService' ];
function authenticationService($http, $rootScope, $timeout, userService) {
	var service = {};

	service.fbLogin = fbLogin;
	service.login = login;
	service.loginById = loginById;
	service.clearCredentials = clearCredentials;
	service.isCredentialsMatch = isCredentialsMatch;
	
	return service;
	
	function fbLogin(user, callback) {

		return userService.fbLogin(user).then(function(result) {
			if(result)
				setCredentials(result);
			return result;
		});
	}
	
	function loginById(callback) {
		var user = getDecodedUser();
		return userService.loginById(user).then(function(result) {
			if(result)
				setCredentials(result);
			return result;
		});
	}

	function login(user, callback) {
		/* Dummy authentication for testing, uses $timeout to simulate api call
		 ----------------------------------------------*/
		/*$timeout(function() {
			var response;
			userService.getByUserEmail(email).then(function(user) {
				if (user !== null && user.password === password) {
					response = {
						success : true
					};
				} else {
					response = {
						success : false,
						message : 'Email or password is incorrect'
					};
				}
				callback(response);
			});
		}, 1000);*/

		/* Use this for real authentication
		 ----------------------------------------------*/
		return userService.login(user).then(function(result) {
			if(result)
				setCredentials(result);
			return result;
		});
	}
	
	function isCredentialsMatch(){
		var match = false;
		if($rootScope.globals && $http.defaults.headers.common['Authorization']){
			var authData = 'Basic ' + $rootScope.globals.currentUser.authdata;
			var auth = $http.defaults.headers.common['Authorization'];
			if(authData == auth)
				match = true;
		}
		
		return match;
	}
	
	function getDecodedUser(){
		var authData = $rootScope.globals.currentUser.authdata;
		var decoded = Base64.decode(authData).split(":");
		var user = {};
		user.customerId = decoded[0];
		user.password = decoded[1];
		return user;		
	}

	function setCredentials(user) {
		var username = user.customerId;
		var fbId = user.fbId;
		var firstName = user.firstName;
		var password = user.password;
		
		var authdata = Base64.encode(username + ':' + password);

		$rootScope.globals = {
			currentUser : {
				fbId : fbId,
				firstName : firstName,
				username : username,
				authdata : authdata
			}
		};
		
		localStorage.fruitpayGlobals =  JSON.stringify($rootScope.globals);
		$http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
	}

	function clearCredentials() {
		$rootScope.globals = {};
		localStorage.fruitpayGlobals = null;
		$http.defaults.headers.common.Authorization = 'Basic ';
	}
}

// Base64 encoding service used by AuthenticationService
var Base64 = {

	keyStr : 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=',

	encode : function(input) {
		var output = "";
		var chr1, chr2, chr3 = "";
		var enc1, enc2, enc3, enc4 = "";
		var i = 0;

		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);

			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;

			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}

			output = output + this.keyStr.charAt(enc1)
					+ this.keyStr.charAt(enc2) + this.keyStr.charAt(enc3)
					+ this.keyStr.charAt(enc4);
			chr1 = chr2 = chr3 = "";
			enc1 = enc2 = enc3 = enc4 = "";
		} while (i < input.length);

		return output;
	},

	decode : function(input) {
		var output = "";
		var chr1, chr2, chr3 = "";
		var enc1, enc2, enc3, enc4 = "";
		var i = 0;

		// remove all characters that are not A-Z, a-z, 0-9, +, /, or =
		var base64test = /[^A-Za-z0-9\+\/\=]/g;
		if (base64test.exec(input)) {
			window
					.alert("There were invalid base64 characters in the input text.\n"
							+ "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n"
							+ "Expect errors in decoding.");
		}
		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		do {
			enc1 = this.keyStr.indexOf(input.charAt(i++));
			enc2 = this.keyStr.indexOf(input.charAt(i++));
			enc3 = this.keyStr.indexOf(input.charAt(i++));
			enc4 = this.keyStr.indexOf(input.charAt(i++));

			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;

			output = output + String.fromCharCode(chr1);

			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}

			chr1 = chr2 = chr3 = "";
			enc1 = enc2 = enc3 = enc4 = "";

		} while (i < input.length);

		return output;
	}
};