angular.module('shell')
	.controller('shellController',["$rootScope", "$scope", "$location", "commService",
	                               function($rootScope, $scope, $location, commService){
		
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
		window.onresize = commService.windowResizeFunc(
		768, $scope, function(){$scope.isShowMenu = false;}, function(){$scope.isShowMenu = true;});
		
		
	}]);