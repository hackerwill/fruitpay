'use strict';
angular
.module('app')
.factory('logService', logService);

logService.$inject = ['$alert'];
function logService($alert){
	
	var service = {};
	service.successCallback = successCallback;
	service.errorCallback = errorCallback;
	service.showSuccess = showSuccess;
		
	return service;
	
	function showSuccess(message){
		$alert({
			title: message,
			placement: 'top',
			type: 'success',
			duration: '3',
			animation: 'am-fade-and-scale'
		});
	}
	
	function successCallback(response){
		var returnData = response.data;
		console.log(returnData);
		if(returnData.errorCode == '0'){
			return true;
		}else{
			console.log(returnData);
			$alert({
				title: returnData.message,
				placement: 'top',
				type: 'danger',
				duration: '3',
				animation: 'am-fade-and-scale'
			});
			return false;
		}
	}
			
	function errorCallback(response){
			console.log(response);
			$alert({
				title: '請確認網路連線',
				placement: 'top',
				type: 'danger',
				duration: '3',
				animation: 'am-fade-and-scale'
			});
			return false;
		}
}