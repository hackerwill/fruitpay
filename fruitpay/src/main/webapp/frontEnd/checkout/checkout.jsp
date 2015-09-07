<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" ng-app='myApp'>
<head>
  <title>果物配|您專屬的水果營養管家</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="../css/main.css">
  <link rel="stylesheet" href="css/checkout.css">
  <script src="../vendor/angularjs/1.4.4/angular.min.js"></script>
  <script src="../vendor/angular-strap/2.3.1/angular-strap.min.js"></script>
  <script src="../vendor/angular-strap/2.3.1/angular-strap.tpl.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="../js/main.js"></script>
  <script src="js/checkout.js"></script>
</head>
<body data-spy="scroll" data-target="#scrollNavbar" data-offset="50">
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation" bs-navbar>
		<div class="navbar-header">
			<a class="pull-left" href="#" ><img class="img-responsive" src="../images/logo211.jpg"/></a>
		</div>
		<ul class="nav navbar-nav" id="scrollNavbar">
			 <li data-target="#section1" bs-scrollspy><a href="#section1"><img class="img-responsive" src="../images/plan.png"/></a>選擇方案</li>
			 <li data-target="#section2" bs-scrollspy><a href="#section2"><img class="img-responsive" src="../images/orderItem.png"/></a>訂單資訊</li>
			 <li data-target="#section3" bs-scrollspy><a href="#section3"><img class="img-responsive" src="../images/checkout.png"/>結帳方式</a></li>
		</ul>
		<ul class="nav navbar-nav navbar-right">
			<li data-match-route="/$" style="display:none;"><a href="#"></a></li>
			<li data-match-route="/$"><a href="#">首頁</a></li>
			<li data-match-route="/page-one"><a href="#page-one">果物箱</a></li>
			<li data-match-route="/page-two"><a href="#page-two">常見問題</a></li>
			<li data-match-route="/page-third"><a href="#page-third">部落格</a></li>
			<li data-match-route="/page-forth"><a href="#page-forth">聯絡我們</a></li>
			<li data-match-route="/page-five"><a href="#page-five" ><span class="glyphicon glyphicon-log-in"></span> 會員中心</a></li>
		</ul>
		<hr>
	</nav>
<div class="container">	
	<div id="section1" class="container-fluid sectionDiv">
		<h1>選擇方案</h1>
		<hr/>
		<div class="row planDiv">
			<div class="col-sm-6">
				<table class="table">
					<thead>
						<tr>
							<th><h1>單人活力水果箱</h1></th>
						</tr>
						<tr>
							<td><img class="img-responsive" src="../images/singlePlan.png"/></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>元氣果物箱(單人方案)</th>
						</tr>
						<tr>
							<td>4-5種新鮮水果</td>
						</tr>
						<tr>
							<td>客製化您的水果箱</td>
						</tr>
						<tr>
							<td>適用:小資女、單身、學生</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-sm-6">
				
				<table class="table">
					<thead>
						<tr>
							<th><h1>家庭元氣果物箱</h1></th>
						</tr>
						<tr>
							<td><img class="img-responsive" src="../images/familyPlan.png"/></td>
						</tr>
					</thead>
					<tbody>
						<tr>
							<th>元氣果物箱(家庭方案)</th>
						</tr>
						<tr>
							<td>6-8種新鮮水果</td>
						</tr>
						<tr>
							<td>客製化您的水果箱</td>
						</tr>
						<tr>
							<td>適用:家庭主婦、上班族</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div id="section2" class="container-fluid sectionDiv">
		<h1>訂單資訊</h1>
		<hr/>
		<form role="form">
			<div class="form-group">
					<label for="email">信箱:</label>
					<input type="email" class="form-control" id="email">
				</div>
				<div class="form-group">
					<label for="pwd">密碼:</label>
					<input type="password" class="form-control" id="pwd">
				</div>
				<div class="form-group">
					<label for="pwd">確認密碼:</label>
					<input type="password" class="form-control" id="pwd">
			</div>
			<div class="form-group form-inline">
				<label for="email">中文全名:</label>
				<input type="text" class="form-control" id="email">
				<input type="radio" class="form-control" name="sex"> 
				<label for="sex"> 先生</label>
				<input type="radio" class="form-control" name="sex"> 
				<label for="sex"> 小姐</label>
			</div>
			<div class="form-group form-inline">
				<label for="ageRange">年齡:</label>
				<select class="form-control" id="ageRange">
					<option>15~25</option>
					<option>25~35</option>
					<option>35~45</option>
					<option>45~55</option>
				</select>
				<div class="form-group" ng-class="{'has-error': datepickerForm.date2.$invalid}">
					<label class="control-label"><small><i class="fa fa-calendar"></i>生日: </small></label>
					<input type="text" class="form-control" ng-model="selectedDateAsNumber" data-date-format="yyyy-MM-dd" data-date-type="number" data-min-date="02/10/86" data-max-date="today" data-autoclose="1" name="date2" bs-datepicker>
				</div>
			</div>
			<div class="form-group form-inline">
				<label for="phoneNumber">手機號碼:</label>
				<input type="text" class="form-control" id="phoneNumber">
			</div>
			<div class="form-group form-inline">
				<label for="address">聯絡地址:</label>
				<input type="radio" class="form-control" name="homeOrCompany"> 
				<label for="homeOrCompany">住家</label>
				<input type="radio" class="form-control" name="homeOrCompany"> 
				<label for="homeOrCompany">公司</label>
				<input type="text" class="form-control" id="address">
			</div>
		</form>
		<form>
			<h1>收件人資訊</h1>
			<hr/>
			<div class="form-group form-inline">
				<label for="note">同上請打勾:</label>
				<input type="checkbox" class="form-control" id="note">
			</div>
			<div class="form-group form-inline">
				<label for="email">收件人姓名:</label>
				<input type="text" class="form-control" id="email">
				<input type="radio" class="form-control" name="sex"> 
				<label for="sex"> 先生</label>
				<input type="radio" class="form-control" name="sex"> 
				<label for="sex"> 小姐</label>
			</div>
			<div class="form-group form-inline">
				<label for="phoneNumber">手機號碼:</label>
				<input type="text" class="form-control" id="phoneNumber">
			</div>
			<div class="form-group form-inline">
				<label for="address">送貨地址:</label>
				<input type="radio" class="form-control" name="homeOrCompany"> 
				<label for="homeOrCompany">住家</label>
				<input type="radio" class="form-control" name="homeOrCompany"> 
				<label for="homeOrCompany">公司</label>
				<input type="text" class="form-control" id="address">
			</div>
			<div class="form-group form-inline">
				<label for="address">配送時段:</label>
				<input type="radio" class="form-control" name="expertTime"> 
				<label for="expertTime">上午</label>
				<input type="radio" class="form-control" name="expertTime"> 
				<label for="expertTime">下午(14~17)</label>
				<input type="radio" class="form-control" name="expertTime"> 
				<label for="expertTime">下午(17~20)</label>
			</div>
			<div class="form-group form-inline">
				<label for="note">備註:</label>
				<input type="text" class="form-control" id="note">
			</div>
			<div class="form-group form-inline">
				<label>您的第一次配送時間:8/19(三)(固定週三配達，禮拜一12點以前訂購可以當週三送達，之後便是隔周三送達。)</label>
			</div>
			<br>
		</form>
	</div>
	<div id="section3" class="container-fluid sectionDiv">
		<h1>結帳方式</h1>
		<hr/>
		<form>
			<div class="form-group form-inline">
				<input type="radio" class="form-control" name="checkoutMethod"> 
				<label for="checkoutMethod"> 信用卡</label>
				<br>
				<input type="radio" class="form-control" name="checkoutMethod"> 
				<label for="checkoutMethod"> 貨到付款 (酌收30元手續費)</label>
			</div>
		</form>
		<br>
		
		<button class="btn btn-default">確定</button>
		<br>
	</div>
</div>
	
	
	<div class="footer" id="footer">
		<p>Copyright 2015果物配| All Rights Reserved | <br>
		   info@fruitpay.com.tw| Tel: 02-2338-0958| 台北市松山區南京東路四段197號7F
		</p>
	</div>
</body>
</html>