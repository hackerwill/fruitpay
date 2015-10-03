package com.fruitpay.base.comm.returndata;

public class ReturnMessage implements ReturnData {

	private static final long serialVersionUID = 1L;
	
	protected String errorCode;	//錯誤代碼，0為無錯誤
	protected String message;	//錯誤信息
	
	public ReturnMessage(String errorCode){
		super();
		this.errorCode = errorCode;
		this.message = "";
	}
	
	public ReturnMessage(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	@Override
	public String getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public Object getObject() {
		return null;
	}
	
	
	
}
