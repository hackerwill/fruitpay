<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" ng-app='myApp'>
<head>
  <title>果物配|您專屬的水果營養管家</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
  <link rel="stylesheet" href="css/main.css">
  <script src="vendor/angularjs/1.4.4/angular.min.js"></script>
  <script src="vendor/angular-strap/2.3.1/angular-strap.min.js"></script>
  <script src="vendor/angular-strap/2.3.1/angular-strap.tpl.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="js/main.js"></script>
  <script src="js/login.js"></script>
</head>
<body>
	<div class="container" ng-controller="loginController">    
		 <pre>user = {{user | json}}</pre>
        <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">                    
            <div class="panel panel-info" >
            	<div class="panel-heading">
            		<div class="panel-title">登入</div>
                    <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">忘記密碼</a></div>
                </div>     
                <div style="padding-top:30px" class="panel-body" >
                    <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>
                    <form name="loginform" class="form-horizontal" role="form" ng-submit="onSubmit()" novalidate >
                        <span style="color:red" ng-show="loginform.email.$dirty && loginform.email.$invalid">
                        	<span ng-show="loginform.email.$error.required">信箱為空</span>
  							<span ng-show="loginform.email.$error.email">信箱格式錯誤</span>           
                        </span>           
                        <div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        	<input type="email" class="form-control" name="email" ng-model="user.email" value="" placeholder="請輸入信箱" required />                
                     	</div>
                     	<span style="color:red" ng-show="loginform.password.$dirty && loginform.password.$invalid">
                        	<span ng-show="loginform.password.$error.required">密碼為空</span>   
                        	<span ng-show="loginform.password.$error.minlength">密碼至少為六碼</span>         
                        </span>    
                    	<div style="margin-bottom: 25px" class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                            <input id="login-password" type="password" class="form-control" name="password" ng-model="user.password" placeholder="請輸入密碼" required ng-minlength="6"/>
                        </div>
<!--                         <div class="input-group"> -->
<!--                         	<div class="checkbox"> -->
<!--                             	<label> -->
<!--                             		<input id="login-remember" type="checkbox" ng-model="user.remember" value="1"> 記住我 -->
<!--                            		</label> -->
<!--                         	</div> -->
<!--                         </div> -->
                        <div style="margin-top:10px" class="form-group">
                        	<!-- Button -->
                            <div class="col-sm-12 controls">
                            	<Button type="submit" class="btn btn-success"/>登入</Button>
<!--                             	<Button type="submit" class="btn btn-primary"/>Facebook登入</Button> -->
							</div>
                     	</div>
                        <div class="form-group">
                        	<div class="col-md-12 control">
                            	<div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                	沒有帳號 
                                	<a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                		註冊
                                	</a>
                                </div>
                            </div>
                        </div>    
                    </form>     
				</div>                     
        	</div>  
        </div>
        
        <div id="signupbox" style="display:none; margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        	<div class="panel panel-info">
            	<div class="panel-heading">
                	<div class="panel-title">註冊</div>
                	<div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">登入</a></div>
                </div>  
                <div class="panel-body" >
             		<form id="signupform" class="form-horizontal" role="form">
                    	<div id="signupalert" style="display:none" class="alert alert-danger">
                        	<p>錯誤:</p>
                            <span></span>
                        </div>
                        <div class="row">
                    		<div class="col-sm-12">
                    			<div style="margin-bottom: 25px" class="input-group">
	                				<span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                     				<input type="text" class="form-control" name="email" placeholder="請輸入信箱" />
                     			</div>
                    		</div>
                    	</div>
                    	<div class="row">
	                		<div class="col-sm-6">
	                			<div style="margin-bottom: 25px" class="input-group">
	                				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	                				<input type="text" class="form-control" name="firstname" placeholder="請輸入姓氏" />
	                		    </div>
	                		</div>
                    		<div class="col-sm-6">
                    			<div style="margin-bottom: 25px" class="input-group">
	                				<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
	                				<input type="text" class="form-control" name="lastname" placeholder="請輸入名字" />
                    			</div>
                    		</div>
                    	</div>
                    	<div class="row">
                        	<div class="col-sm-12">
                        		<div style="margin-bottom: 25px" class="input-group">
	                				<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                      				<input type="password" class="form-control" name="passwd" placeholder="請輸入密碼" />
                      			</div>
                        	</div>
                  		</div>
						<div>
                    		<!-- Button -->                                        
                    		<div class="col-md-offset-3 col-md-9">
                        		<button id="btn-signup" type="button" class="btn btn-info"><i class="icon-hand-right"></i> &nbsp 註冊</button>
<!--                             	<span style="margin-left:8px;">或</span>   -->
                         	</div>
                     	</div>
<!--                      	<div style="border-top: 1px solid #999; padding-top:20px"  class="form-group"> -->
<!-- 	                 		<div class="col-md-offset-3 col-md-9"> -->
<!--                        			<button id="btn-fbsignup" type="button" class="btn btn-primary"><i class="icon-facebook"></i>   Facebook註冊</button> -->
<!--                         	</div>                                            -->
<!--                    	 	</div> -->
	             	</form>
        		</div>
        	</div>
		</div> 
    </div>
</body>