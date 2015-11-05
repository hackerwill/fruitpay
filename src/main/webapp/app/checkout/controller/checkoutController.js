'use strict';
angular.module('checkout')
	.controller('checkoutController',["$scope", "$document", "$window", "commService", '$q', 
		function($scope, $document, $window, commService, $q){
		
		var slides = $scope.slides = [];
		$scope.myInterval = 5000;
		$scope.addSlide = function(imageLink, text) {
			console.log(imageLink + '?resize=200%2C150');
			slides.push({
				image: imageLink + '?resize=200%2C150',
			    text: text
			});
			console.log(slides.length);
			
		};
		  
		commService.getAllProducts()
			.then(function(result){
				  for (var i = 0; i< result.length; i++) {
					  if(result[i].imageLink != null && result[i].imageLink.length > 0 && result[i].productName.length > 0)
						  $scope.addSlide(result[i].imageLink, result[i].productName);
				  }
			});
		
		  
		 

		$scope.order = {};
		$scope.user = {};
		$scope.countyList = {};
		$scope.towershipList = {};
		$scope.villageList = {};
		
		$scope.receiverCountyList = {};
		$scope.receiverTowershipList = {};
		$scope.receiverVillageList = {};
		
		$scope.slideToggle = slideToggle;
		$scope.itemClick = itemClick;
		$scope.scrollElement = scrollElement;
		$scope.scrollElement($document, $scope, $window, commService);
		$scope.onCheckoutSubmit = onCheckoutSubmit;
		$scope.change = change;
		$scope.countyChange = countyChange;
		$scope.towershipChange = towershipChange;
		$scope.receiverCountyChange = receiverCountyChange;
		$scope.receiverTowershipChange = receiverTowershipChange;
		
		(function(){
			commService
				.getAllCounties()
				.then(setCountiesData)
				.then(setTowershipsData)
				.then(setVillagesData);
			
			commService
				.getAllCounties()
				.then(setReceiverCountiesData)
				.then(setReceiverTowershipsData)
				.then(setReceiverVillagesData);
		})();
		
		function setReceiverCountiesData(counties, county){
			$scope.receiverCountyList = counties;
			if(county){
				return county;
			}else{
				return setDefaultCounty(counties);
			}			
		}
		
		function setCountiesData(counties){
			$scope.countyList = counties;
			return setDefaultCounty(counties);
		}
		
		function setDefaultCounty(counties){
			for(var i = 0; i < counties.length; i ++){
				if(counties[i].id == 63)
					return counties[i];
			}
		}
		
		function setTowershipsData(county){
			$scope.county = county;
			return setTowership(county ,function(towerships){
				$scope.towershipList = towerships;
				$scope.towership = towerships[0];
			});
		}
		
		function setReceiverTowershipsData(county){
			$scope.receiverCounty = county;
			return setTowership(county ,function(towerships){
				$scope.receiverTowershipList = towerships;
				$scope.receiverTowership = towerships[0];
			});
		}
		
		function setTowership(county, callback){
			var anotherDeferred = $q.defer();
			commService
				.getTowerships(county.id)
				.then(function(towerships){
					callback(towerships);
					anotherDeferred.resolve(towerships[0]);
				});
			return anotherDeferred.promise;
		}
		
		function setVillagesData(towership){
			$scope.towership = towership;
			setVillage(towership, function(villages){
				$scope.villageList = villages;
				$scope.village = villages[0];
			});
		}
		
		function setReceiverVillagesData(towership){
			$scope.receiverTowership = towership;
			setVillage(towership, function(villages){
				$scope.receiverVillageList = villages;
				$scope.receiverVillage = villages[0];
			});
		}
		
		function setVillage(towership, callback){
			commService
				.getVillages(towership.id)
				.then(callback);
		}
		
		function countyChange(){
			$q.when($scope.county)
				.then(setTowershipsData)
				.then(setVillagesData);
		}
		
		function receiverCountyChange(){
			$q.when($scope.receiverCounty)
				.then(setReceiverTowershipsData)
				.then(setReceiverVillagesData);
		}

		function towershipChange(){
			$q.when($scope.towership)
				.then(setVillagesData);
		}
		
		function receiverTowershipChange(){
			$q.when($scope.receiverTowership)
				.then(setReceiverVillagesData);
		}
		
		function onCheckoutSubmit(){
			if ($scope.checkoutForm.$valid) {      
				
			}else {
				//if form is not valid set $scope.addContact.submitted to true     
				$scope.checkoutForm.submitted=true;  			
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
					$scope.order.receiverGender = $scope.user.gender;
					setFromUserToReceiverVillage();
				}else{
					$scope.checkoutForm.submitted=true;  
					$scope.confirmed = false;
				}
			}
		}
		
		function setFromUserToReceiverVillage(){
			$scope.receiverCounty = $scope.county;
			commService
				.getTowerships($scope.county.id)
				.then(function(towerships){
					$scope.receiverTowershipList = towerships;
					$scope.receiverTowership = $scope.towership;
					return $scope.towership;
				})
				.then(function(towership){
					commService
						.getVillages(towership.id)
						.then(function(villages){
							$scope.receiverVillageList = villages;
							$scope.receiverVillage = $scope.village;
						});
				});
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