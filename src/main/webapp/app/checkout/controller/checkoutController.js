'use strict';
angular.module('checkout')
	.controller('checkoutController',["$scope", "$document", "$window", "commService", 
		function($scope, $document, $window, commService){
		
		$scope.order = {};
		$scope.user = {};
		
		$scope.slideToggle = slideToggle;
		$scope.itemClick = itemClick;
		$scope.scrollElement = scrollElement;
		$scope.scrollElement($document, $scope, $window, commService);
		$scope.onCheckoutSubmit = onCheckoutSubmit;
		$scope.change = change;
		
		function onCheckoutSubmit(){
			if ($scope.checkoutForm.$valid) {      
				//form is valid
				console.log("eee");
			}else {
				//if form is not valid set $scope.addContact.submitted to true     
				$scope.checkoutForm.submitted=true;  
				console.log("here");				
			};
		}
		
		function change(){
			if($scope.confirmed){
				if($scope.checkoutForm.$valid){
					$scope.order.receiverFirstName = $scope.user.firstName;
					$scope.order.receiverLastName = $scope.user.lastName;
					$scope.order.receiverPhone = $scope.user.cellphone;
					$scope.order.receiverAddress = $scope.user.address;
					$scope.order.receiverGender = $scope.user.gender;
				}else{
					$scope.checkoutForm.submitted=true;  
					$scope.confirmed = false;
				}
			}
		}
		
		function slideToggle(id){
				$scope.slideToggle1 = false;
				$scope.slideToggle2 = false;
				$scope.slideToggle3 = false;
				if('slideToggle1' == id){
					$scope.slideToggle1 = true;
				}else if('slideToggle2' == id){
					$scope.slideToggle2 = true;
				}else if('slideToggle3' == id){
					$scope.slideToggle3 = true;
				}
				$scope.apply();
		}
		
		function itemClick(itemName){
			var itemDivs = document.getElementsByClassName('itemDiv');
			$scope.order.itemName = itemName;
			document.getElementsByClassName('itemName');
			for(var i = 0; i < itemDivs.length ; i++){
				var itemDiv = itemDivs[i];
				if(itemDiv.id == itemName){
					if(itemDiv.className.indexOf(' selectedItem') == -1)
						itemDiv.className = itemDiv.className + ' selectedItem';
				}else{
					itemDiv.className = itemDiv.className.replace(' selectedItem', '');
				}
			}
		}
		
		function scrollElement($document, $scope, $window, commService){
			
			if(commService.getWindowSize().width < 1000)
				return;
			
			var onePageElements = document.getElementsByClassName("onepageElement");
			var returnObj = {};
			
			returnObj.scrollToNext = scrollToNext;
			returnObj.scrollToPrevious = scrollToPrevious;
			triggerScrollElement();
			
			return returnObj;
			
			function triggerScrollElement(){
				//bind scroll up and down event
				angular.element($window).bind('DOMMouseScroll mousewheel onmousewheel', 
					function(event) {
						event.returnValue = false;
						// for Chrome and Firefox
						if(event.preventDefault) {
							event.preventDefault();                        
						}
						// cross-browser wheel delta
						var event = window.event || event; // old IE support
						var delta = Math.max(-1, Math.min(1, (event.wheelDelta || -event.detail)));
				
						if(delta > 0) {
							returnObj.scrollToPrevious();   
						}else{
							returnObj.scrollToNext();
						}	
				});
			}
			
			function scrollToNext(){
				moveToAnotherOne(1);
			}
			
			function scrollToPrevious(){
				moveToAnotherOne(-1);
			}
			
			function currentActiveIndex(){
				for(var i = 0; i < onePageElements.length ; i++){
					var currentIndex = null;
					var onePageElement = onePageElements[i];
					if(onePageElement.className.indexOf("active") != -1){
						currentIndex = i;
						break;
					}	
				}
				return i;
			}
			
			function moveToAnotherOne(i){
				var activeIndex = currentActiveIndex();
				var currentElement = onePageElements[activeIndex];
				var nextElement = onePageElements[activeIndex+i];
				if(nextElement){
					activeIndex = activeIndex + i;
					$document.scrollToElementAnimated(onePageElements[activeIndex]);
					//set timeout to prevent the scroll event trigger many times
					setTimeout(function(){
						for(var i = 0; i < onePageElements.length ; i++){
							onePageElements[i].className = 
							replaceAll("active|\sactive", "", onePageElements[i].className);
						}
						nextElement.className = nextElement.className.trim();
						if(nextElement.className.indexOf("active") == -1)
							nextElement.className = nextElement.className + " active";
							
					}, 1000);
				}
			}
			
			function replaceAll(find, replace, str) {
				return str.replace(new RegExp(find, 'g'), replace);
			}

		}
		
	}]);