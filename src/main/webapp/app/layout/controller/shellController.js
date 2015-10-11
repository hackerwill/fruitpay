angular.module('shell')
	.controller('shellController',["$scope",function($scope){
		
		/**
		 * check whether to show menu when resize the window
		 */
		window.onresize = function(){
				var data = {
					divideValue : 768,
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
							$scope.isShowMenu = false;
						};
						if(data.minThanDevideValue()){
							$scope.isShowMenu = true;
						};
						data.widthTemp = window.innerWidth;
					});						
				}	
			}();
	}]);