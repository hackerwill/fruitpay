package com.fruitpay.base.comm.returndata;

import com.fruitpay.comm.model.ReturnMessage;

public class ReturnMessageEnum{
	
	public enum Status{
		Success("0"),
		Failed("-1");
		
		private String status = null;
		
		private Status(String status){
			this.status = status;
		}
		
		public String getStatus(){
			return this.status;
		}
	}
	
	public enum Common{
		Success(new ReturnMessage(Status.Success.getStatus())),
		RequiredFieldsIsEmpty(new ReturnMessage(Status.Failed.getStatus(), "必要欄位還沒有填寫")),
		AuthenticationFailed(new ReturnMessage(Status.Failed.getStatus(), "認證錯誤")),
		UnexpectedError(new ReturnMessage(Status.Failed.getStatus(), "未知錯誤發生"));
		
		private ReturnMessage rm;
		Common(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Login{
		AccountNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個帳號")),
		EmailNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個信箱")),
		EmailPasswordNotMatch(new ReturnMessage(Status.Failed.getStatus(), "信箱與密碼不符")),
		EmailAlreadyExisted(new ReturnMessage(Status.Failed.getStatus(), "信箱已被註冊")),
		PasswordNotMatched(new ReturnMessage(Status.Failed.getStatus(), "密碼不符合"));
		
		private ReturnMessage rm;
		Login(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Order{
		OrderNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個訂單號碼"));
		
		private ReturnMessage rm;
		Order(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
}

