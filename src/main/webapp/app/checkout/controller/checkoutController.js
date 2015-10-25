'use strict';
angular.module('checkout')
	.controller('checkoutController',["$scope", "$document",function($scope, $document){
		var section3 = angular.element(document.getElementById('section3'));
		$scope.toSection3 = function() {
			$document.scrollToElementAnimated(section3);
		}
	}]);