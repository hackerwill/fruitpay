package com.fruitpay.base.comm.returndata;

import com.fruitpay.comm.model.ReturnMessage;

public class ReturnMessageEnum{
	
	public enum Common{
		Success(new ReturnMessage("0")),
		RequiredFieldsIsEmpty(new ReturnMessage("-1", "必要欄位還沒有填寫")),
		AuthenticationFailed(new ReturnMessage("-1", "認證錯誤")),
		UnexpectedError(new ReturnMessage("-1", "未知錯誤發生"));
		
		private ReturnMessage rm;
		Common(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Login{
		AccountNotFound(new ReturnMessage("-1", "找不到這個帳號")),
		EmailNotFound(new ReturnMessage("-1", "找不到這個信箱")),
		EmailPasswordNotMatch(new ReturnMessage("-1", "信箱與密碼不符")),
		EmailAlreadyExisted(new ReturnMessage("-1", "信箱已被註冊"));
		
		private ReturnMessage rm;
		Login(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Order{
		OrderNotFound(new ReturnMessage("-1", "找不到這個訂單號碼"));
		
		private ReturnMessage rm;
		Order(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
}

