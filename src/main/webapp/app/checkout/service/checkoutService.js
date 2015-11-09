'use strict';
 
	angular
        .module('app')
		.factory('checkoutService', checkoutService);
	
	checkoutService.$inject = ['$http','logService'];
	function checkoutService($http, logService){
		var service = {};
		
		service.checkout = checkout;
		service.checkoutTest = checkoutTest;
		
		return service;
		
		function checkout(user, order){
			
		}
		
		function checkoutTest(){
			var sendObj = {};
			
			var user = {
					  "lastName": "徐",
					  "firstName": "瑋志",
					  "gender": "M",
					  "email": "u9734017@gmail.com",
					  "password": "123456",
					  "confirmPassword": "123456",
					  "cellphone": "0933370691",
					  "address": "同安村西畔巷66弄40號",
					  "village": {
					    "countyCode":10007,
					    "countyName":"彰化縣",
					    "towershipCode": "1000716",
					    "towershipName":"永靖鄉",
					    "villageCode":"1000716-019",
					    "villageName":"同安村"
					  },
					  "birthday": "1990-06-04"
					};
			
			var order = {
					  "orderProgram": {
						    "programId": 1,
						    "programName": "小農箱-家庭",
						    "programDesc": "含進口水果",
						    "price": 699,
						    "periodId": 1
						  },
						  "receiverFirstName": "瑋志",
						  "receiverLastName": "徐",
						  "receiverPhone": "0933370691",
						  "receiverAddress": "九鄰西畔巷66弄40號",
						  "receiverGender": "M",
						  "orderPlatform":{
						    "platformId": 1
						  },
						  "village": {
						    "countyCode": 10007,
						    "countyName": "彰化縣",
						    "towershipCode": "1000716",
						    "towershipName": "永靖鄉",
						    "villageCode": "1000716-019",
						    "villageName": "同安村"
						  },
						  "paymentMode": {
						    "paymentModeId" : 1
						  },
						  "orderDate": "2015-11-08",
						  "shipmentDay": {
						    "shipmentDaysId": 2
						  },
						  "orderStatus" : {
						    "orderStatusId": 1
						  }
						};
			
			sendObj.customer = user;
			sendObj.customerOrder = order;
			return $http.post('checkoutCtrl/checkout', sendObj)
			.then(logService.successCallback, logService.errorCallback);
		}
	}