package com.fruitpay.base.comm.returndata;

public enum LoginReturnMessage{
	
	Success(new ReturnMessage("0")),
	EmailNotFound(new ReturnMessage("1", "Email is not Found.")),
	RequiredFieldsIsEmpty(new ReturnMessage("2", "Required fields are empty.")),
	EmailPasswordNotMatch(new ReturnMessage("3", "Email and password not match.")),
	EmailAlreadyExisted(new ReturnMessage("4", "Email is already signed up.")),
	UnexpectedError(new ReturnMessage("-1", "Unexpected Error Happened."));
	
	private ReturnMessage rm;
	
	LoginReturnMessage(ReturnMessage rm){
		this.rm = rm;
	}
	
	public ReturnMessage getReturnMessage(){
		return rm;
	}

}
