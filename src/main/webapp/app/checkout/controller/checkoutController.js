'use strict';
angular.module('checkout')
	.controller('checkoutController',["$scope", "$document", "$window",function($scope, $document, $window){
		var scrollElement = scrollElement();
		
		//bind scroll up and down event
		angular.element($window).bind('DOMMouseScroll mousewheel onmousewheel', function(event) {
			
				// cross-browser wheel delta
				var event = window.event || event; // old IE support
				var delta = Math.max(-1, Math.min(1, (event.wheelDelta || -event.detail)));
                
				if(delta > 0) {
					event.returnValue = false;
					// for Chrome and Firefox
					if(event.preventDefault) {
						event.preventDefault();                        
					}
					scrollElement.scrollToPrevious();   
				}else{
					event.returnValue = false;
					// for Chrome and Firefox
					if(event.preventDefault) {
						event.preventDefault();                        
					}
					scrollElement.scrollToNext();
				}
		});
		
		function scrollElement(){
			var onePageElements = document.getElementsByClassName("onepageElement");
			
			
			var returnObj = {};
			
			returnObj.scrollToNext = scrollToNext;
			returnObj.scrollToPrevious = scrollToPrevious;
			
			return returnObj;
			
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
					currentElement.className = currentElement.className.replace("active", " ");
					activeIndex = activeIndex + i;
					nextElement.className = nextElement.className + " active";
					$document.scrollToElementAnimated(onePageElements[activeIndex]);
				}
			}
		}
		
	}]);