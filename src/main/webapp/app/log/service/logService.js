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
	service.showDanger = showDanger;
	service.showInfo = showInfo;
		
	return service;
	
	function showInfo(message){
		$alert({
			title: message,
			placement: 'top',
			type: 'info',
			duration: '3',
			animation: 'am-fade-and-scale'
		});
	}
	
	function showDanger(message){
		$alert({
			title: message,
			placement: 'top',
			type: 'danger',
			duration: '3',
			animation: 'am-fade-and-scale'
		});
	}
	
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
		if(returnData.errorCode == '0'){
			return returnData.object;
		}else{
			if(returnData == null || returnData.length == 0){
				returnData = {message : "請檢查網路連線"};
			}
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