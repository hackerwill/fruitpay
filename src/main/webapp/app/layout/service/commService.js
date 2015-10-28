'use strict';
angular.module('app')
	.factory('commService', commService);

commService.$inject = [];
function commService() {
	var service = {};
	
	service.windowResizeFunc = windowResizeFunc;
	service.getWindowSize = getWindowSize;
	return service;
	
	function getWindowSize(){
		var w = window,
		d = document,
		e = d.documentElement,
		g = d.getElementsByTagName('body')[0],
		x = w.innerWidth || e.clientWidth || g.clientWidth,
		y = w.innerHeight|| e.clientHeight|| g.clientHeight;
		return {
			width : x,
			height : y
		}
	}

	function windowResizeFunc(divideSize, $scope, maxThanFunc, minThanFunc){
		var data = {
			divideValue : divideSize,
			widthTemp : 0,
			maxThanDevideValue : function(){
				return this.widthTemp < this.divideValue && window.innerWidth >= this.divideValue;
			},
			minThanDevideValue : function(){
				return this.widthTemp > this.divideValue && window.innerWidth <= this.divideValue;
			}
		};
				
		return function(){
			$scope.$apply(function(){
				if(data.maxThanDevideValue()){
					maxThanFunc();
				};
				if(data.minThanDevideValue()){
					minThanFunc();
				};
				data.widthTemp = window.innerWidth;
			});						
		}	
	}
}
