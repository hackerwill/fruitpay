package com.fruitpay.base.comm.returndata;

import org.springframework.http.HttpStatus;

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
		RequiredFieldsIsEmpty(new ReturnMessage(Status.Failed.getStatus(), "必要欄位還沒有填寫", HttpStatus.METHOD_NOT_ALLOWED)),
		AuthenticationFailed(new ReturnMessage(Status.Failed.getStatus(), "認證錯誤", HttpStatus.FORBIDDEN)),
		UnexpectedError(new ReturnMessage(Status.Failed.getStatus(), "未知錯誤發生", HttpStatus.INTERNAL_SERVER_ERROR));
		
		private ReturnMessage rm;
		Common(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Login{
		AccountNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個帳號", HttpStatus.NOT_FOUND)),
		EmailNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個信箱", HttpStatus.NOT_FOUND)),
		EmailPasswordNotMatch(new ReturnMessage(Status.Failed.getStatus(), "信箱與密碼不符", HttpStatus.FORBIDDEN)),
		EmailAlreadyExisted(new ReturnMessage(Status.Failed.getStatus(), "信箱已被註冊", HttpStatus.FORBIDDEN)),
		PasswordNotMatched(new ReturnMessage(Status.Failed.getStatus(), "密碼不符合", HttpStatus.FORBIDDEN));
		
		private ReturnMessage rm;
		Login(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Order{
		OrderNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個訂單號碼", HttpStatus.NOT_FOUND)),
		AddCustomerFailed(new ReturnMessage(Status.Failed.getStatus(), "新增顧客失敗", HttpStatus.METHOD_NOT_ALLOWED));
		
		private ReturnMessage rm;
		Order(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum Coupon{
		CouponNotFound(new ReturnMessage(Status.Failed.getStatus(), "找不到這個優惠券號碼", HttpStatus.NOT_FOUND));
	
		private ReturnMessage rm;
		Coupon(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
}

