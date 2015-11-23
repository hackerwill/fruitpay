'use strict';
angular.module('checkout')
	.controller('checkoutController',
			["$scope", "$document", "$window", "commService", '$q', "checkoutService",
			 "logService", "savedSessionService",
		function(
				$scope, $document, $window, commService, 
				$q, checkoutService, logService, savedSessionService){
		
		$scope.myInterval = false;
		$scope.imageNum = getImageNum();
		$scope.maxUnlikeCount = 10;
		$scope.order = {};
		$scope.user = {};
		$scope.countyList = {};
		$scope.towershipList = {};
		$scope.villageList = {};
		$scope.receiverCountyList = {};
		$scope.receiverTowershipList = {};
		$scope.receiverVillageList = {};
		$scope.order.slides = [];
		
		$scope.itemClick = itemClick;
		$scope.scrollElement = scrollElement;
		$scope.scrollElement($document, $scope, $window, commService);
		$scope.onCheckoutSubmit = onCheckoutSubmit;
		$scope.change = change;
		$scope.countyChange = countyChange;
		$scope.towershipChange = towershipChange;
		$scope.receiverCountyChange = receiverCountyChange;
		$scope.receiverTowershipChange = receiverTowershipChange;
		$scope.range = range;
		$scope.getImageName = getImageName;
		$scope.fruitLeft = fruitLeft;
		$scope.fruitRight = fruitRight;
		$scope.setLikeDegree = setLikeDegree;
		$scope.periodChoose = periodChoose;
		$scope.unselectAllRemoveProdcut = unselectAllRemoveProdcut;
		
		(function(){
			
			var oneChain = commService.getAllProducts()
				.then(function(result){
					var resizeAppend = '?resize=200%2C150';
					
					//check null elements and set resize parameter
					for (var i = 0; i< result.length; i++) {
						if(result[i].imageLink == null || result[i].imageLink.length == 0 || result[i].productName.length == 0)
							delete result[i];
					}
					
					var orderPreferences = [];
					for (var i = 0; i< result.length; i++) {
						if(result[i]){
							result[i].imageLink = result[i].imageLink + resizeAppend;
							
							var obj = {};
							obj.likeDegree = 5; 
							obj.product = result[i];
							obj.id = {};
							obj.id.productId = result[i].productId;
							orderPreferences.push(obj);
						}
					}
					
					/**
					 * 
					 * 計算方法範例
					 * 
					 * slides[0]['image1'] = result[0]; 0/3, 0%3 + 1 
					 * slides[0]['image2'] = result[1]; 1/3, 1%3 + 1
					 * slides[0]['image3'] = result[2]; 2/3, 2%3 + 1
					 * slides[1]['image1'] = result[3]; 3/3, 3%3 + 1
					 * slides[1]['image2'] = result[4]; 4/3, 4%3 + 1
					 * slides[1]['image3'] = result[5]; 5/3, 5%3 + 1
					 * 
					 * */
					for (var i = 0; i< orderPreferences.length; i++) {
						var index = Math.floor(i/$scope.imageNum);
						var num = i%$scope.imageNum + 1;
						if(!$scope.order.slides[index])
							$scope.order.slides[index] = {};
						$scope.order.slides[index]['image' + num] = orderPreferences[i];
					}
					$scope.order.totalImageAmount = orderPreferences.length;
					$scope.order.orderPreferences = orderPreferences;
				});
			
			var twoChain = commService
				.getAllCounties()
				.then(setCountiesData)
				.then(setTowershipsData)
				.then(setVillagesData);
			
			var threeChain = commService
				.getAllCounties()
				.then(setReceiverCountiesData)
				.then(setReceiverTowershipsData)
				.then(setReceiverVillagesData);
			
			var fourChain = commService
				.getAllOrderPlatforms()
				.then(function(result){
					$scope.order.orderPlatform = result[0];
				});
			
			var fiveChain = commService
				.getAllOrderPrograms()
				.then(function(result){
					$scope.orderPrograms = result;
				});
			
			var sixChain = commService
				.getAllOrderStatuses()
				.then(function(result){
					console.log(result);
				});
			
			var sevenChain = commService
				.getAllPaymentModes()
				.then(function(result){
					$scope.paymentModes = result;
				});
			
			var eightChain = commService
				.getAllShipmentPeriods()
				.then(function(result){
					$scope.shipmentPeriods = result;
					//週期預設單周配送
					$scope.order.shipmentPeriod = result[0];
				});
			
			var nineChain = commService
				.getConstant(1)
				.then(function(result){
					$scope.order.receiveWay = result.constOptions[0];
					$scope.receiveWay = result;
				});
			
			var tenChain = commService
				.getConstant(2)
				.then(function(result){
					$scope.order.shipmentTime = result.constOptions[0];
					$scope.shipmentTime = result;
				});
			
			var elevenChain = commService
				.getConstant(3)
				.then(function(result){
					$scope.order.comingFrom = result.constOptions[0];
					$scope.comingFrom = result;
				});
			
			$q.all([oneChain, twoChain, threeChain, fourChain, fiveChain,
			        sixChain, sevenChain, eightChain, nineChain, tenChain,
			        elevenChain])
			        .then(function(){
			        	if(savedSessionService.getObject("checkout.order")){
			    			$scope.order = savedSessionService.getObject("checkout.order");
			    			console.log($scope.order);
			    			setVillageLoop($scope.order.receiverCountyBackup, 
			    					$scope.order.receiverTowershipBackup, 
			    					$scope.order.receiverVillageBackup,
			    					"receiverCounty", "receiverTowershipList", "receiverTowership",
			    					"receiverVillageList", "receiverVillage");
			        	}
			    		
			    		if(savedSessionService.getObject("checkout.user")){
			    			$scope.user = savedSessionService.getObject("checkout.user");
			    			console.log($scope.user);
			    			setVillageLoop(
			    					$scope.user.countyBackup, 
			    					$scope.user.towershipBackup, 
			    					$scope.user.villageBackup,
			    					"county", "towershipList", "towership",
			    					"villageList", "village");
			    		}
			        });
			
		})();			
		
		function unselectAllRemoveProdcut(){
			if($scope.isAllChosen){
				for (var i = 0; i < $scope.order.orderPreferences.length; i++) {
					$scope.order.orderPreferences[i].likeDegree = 5; 
				}
			};
		}
		
		function periodChoose(periodId){
			for (var i = 0; i < $scope.shipmentPeriods.length; i++) {
				if(periodId == $scope.shipmentPeriods[i].periodId)
					$scope.order.shipmentPeriod = $scope.shipmentPeriods[i];
			}
		}
		
		function setLikeDegree(orderPreference){
			if(orderPreference.likeDegree == 5){
				if(isReachMaxUnlikeMount()){
					logService.showInfo("已到達上限囉!");
					return;
				}
				$scope.isAllChosen = false;
				orderPreference.likeDegree = 0;
			}else{
				orderPreference.likeDegree = 5;
			}
			for (var i = 0; i < $scope.order.orderPreferences.length; i++) {
				var thisPreference = $scope.order.orderPreferences[i];
				if(thisPreference.product.productId == orderPreference.product.productId)
					thisPreference.likeDegree = orderPreference.likeDegree;
			}
		}
		
		function isReachMaxUnlikeMount(){
			var totalUnlikeCount = 0;
			for (var i = 0; i < $scope.order.orderPreferences.length; i++) {
				if($scope.order.orderPreferences[i].likeDegree == 0)
					totalUnlikeCount ++;
			}
			if(totalUnlikeCount >= $scope.maxUnlikeCount){
				return true;
			}else{
				return false;
			}
		}
		
		function fruitLeft(){
			$scope.carouselIndex--;
		}
		
		function fruitRight(){
			$scope.carouselIndex++;
		}
		
		function getImageNum(){
			if(commService.getWindowSize().width > 991)
				return 18;
			else
				return 12;
		}
		
		function getImageName(n){
			return "image" + n;
		}
		
		function range(min, max, index, maxLimit){
			var totalAmount = maxLimit - index * max;
			var imageTotal = totalAmount > max ? max : totalAmount;
		    var input = [];
		    for (var i = min; i <= imageTotal; i += 1) {
		      input.push(i);
		    }
		    return input;
		  };
		
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
			//document.getElementById("orderId").value = 11;
			//document.getElementById("allpayCheckoutForm").submit();
			//return;
			//test
			if ($scope.checkoutForm.$valid) {   
				setSubmitData();
				savedSessionService.setObject("checkout.order", $scope.order);
				savedSessionService.setObject("checkout.user", $scope.user);
				return ;
				checkoutService.checkout($scope.user, $scope.order)
					.then(function(result){
						console.log(result);
						if(!isNaN(result)){
							document.getElementById("orderId").value = result;
							document.getElementById("allpayCheckoutForm").submit();
						}
							
					});
			
			}else {
				//if form is not valid set $scope.addContact.submitted to true     
				$scope.checkoutForm.submitted=true;  			
			};
		}
		
		function setSubmitData(){
			setSubmitVillages();
		}
		
		function setSubmitVillages(){
			$scope.user.village = {};
			$scope.user.villageBack
			$scope.user.village.villageCode = $scope.village.id;
			$scope.user.countyBackup = $scope.county;
			$scope.user.towershipBackup = $scope.towership;
			$scope.user.villageBackup = $scope.village;
			
			$scope.order.village = {};
			$scope.order.village.villageCode = $scope.receiverVillage.id;
			$scope.order.receiverCountyBackup = $scope.receiverCounty;
			$scope.order.receiverTowershipBackup = $scope.receiverTowership;
			$scope.order.receiverVillageBackup = $scope.receiverVillage;
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
			setVillageLoop($scope.county, $scope.towership, $scope.village,
					"receiverCounty", "receiverTowershipList", "receiverTowership",
					"receiverVillageList", "receiverVillage");
		}
		
		function setVillageLoop($scopeCounty, $scopeTowership, $scopeVillage,
				$scopeToCountyName, $scopeToTowershipListName, $scopeToTowershipName,
				$scopeToVillageListName, $scopeToVillageName){
			$scope[$scopeToCountyName] = $scopeCounty;
			commService
				.getTowerships($scopeCounty.id)
				.then(function(towerships){
					$scope[$scopeToTowershipListName] = towerships;
					$scope[$scopeToTowershipName] = $scopeTowership;
					return $scopeTowership;
				})
				.then(function(towership){
					commService
						.getVillages(towership.id)
						.then(function(villages){
							$scope[$scopeToVillageListName] = villages;
							$scope[$scopeToVillageName] = $scopeVillage;
						});
				});
		}
		
		function itemClick(programId){
			for (var i = 0; i < $scope.orderPrograms.length; i++) {
				if($scope.orderPrograms[i].programId==programId)
					$scope.order.orderProgram = $scope.orderPrograms[i];
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