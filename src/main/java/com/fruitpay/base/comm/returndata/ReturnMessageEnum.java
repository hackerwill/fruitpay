package com.fruitpay.base.comm.returndata;

import org.springframework.http.HttpStatus;

import com.fruitpay.comm.model.ReturnMessage;

public class ReturnMessageEnum{
	
	public enum Status{
		Success("0"),
		Failed("-1"),
		REDIRECT_TO_LOGIN("-2");
		
		
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
		AuthenticationFailed(new ReturnMessage(Status.REDIRECT_TO_LOGIN.getStatus(), "認證錯誤，請您重新登入", HttpStatus.FORBIDDEN)),
		UnexpectedError(new ReturnMessage(Status.Failed.getStatus(), "未知錯誤發生", HttpStatus.INTERNAL_SERVER_ERROR)),
		NotFound(new ReturnMessage(Status.REDIRECT_TO_LOGIN.getStatus(), "找不到資料，請確認資料是否正確", HttpStatus.NOT_FOUND));
		
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
		PasswordNotMatched(new ReturnMessage(Status.Failed.getStatus(), "密碼不符合", HttpStatus.FORBIDDEN)),
		RequiredLogin(new ReturnMessage(Status.REDIRECT_TO_LOGIN.getStatus(), "需要重新登入", HttpStatus.FORBIDDEN));
		
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
	
	public enum Shipment{
		HasMoreThanOneRecordInSameDay(new ReturnMessage(Status.Failed.getStatus(), "同一天有超過兩筆出貨記錄", HttpStatus.FORBIDDEN));
	
		private ReturnMessage rm;
		Shipment(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}
	
	public enum ShipmentPrerence{
		ColumnSumNotEnoughForRowSum(new ReturnMessage(Status.Failed.getStatus(), "水果總數比訂單需要數量少", HttpStatus.FORBIDDEN)),
		UnableToGetResult(new ReturnMessage(Status.Failed.getStatus(), "計算不出結果, 請嘗試調整數量", HttpStatus.FORBIDDEN)),
		RequiredFruitAmountIsNotEnoughForUser(new ReturnMessage(Status.Failed.getStatus(), "水果品種不足, 顧客出貨單的數量無法滿足", HttpStatus.FORBIDDEN)),
		ColumnNumberNotEnough(new ReturnMessage(Status.Failed.getStatus(), "水果品種不足, 至少要比每張出貨單需要數量多", HttpStatus.FORBIDDEN)),
		CanNotFindMatchedRowData(new ReturnMessage(Status.Failed.getStatus(), "找不到需出貨訂單, 請確認日期是否正確", HttpStatus.FORBIDDEN));

		private ReturnMessage rm;
		ShipmentPrerence(ReturnMessage rm){
			this.rm = rm;
		}
		public ReturnMessage getReturnMessage(){
			return rm;
		}
	}	

}

