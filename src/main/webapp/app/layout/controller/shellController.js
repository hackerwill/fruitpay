angular.module('shell')
	.controller('shellController',["$rootScope", "$scope", "$location",
	                               function($rootScope, $scope, $location){
		
		$scope.isActive = function (viewLocation) { 
			return viewLocation === $location.path();
		};
		
		var loggedIn = $rootScope.globals.currentUser || null;
		if(loggedIn){
			$scope.dropdown = [
        	                   {
        	                     "text": "登出",
        	                     "href": "#/index/logout"
        	                   }
        	               ];
		}else{
			$scope.dropdown = [
        	                   {
        	                     "text": "登入",
        	                     "href": "#/index/login"
        	                   }
        	               ];
		}
		
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