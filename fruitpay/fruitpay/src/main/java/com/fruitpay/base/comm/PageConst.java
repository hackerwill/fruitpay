package com.fruitpay.base.comm;

public enum PageConst {
	MAIN_PAGE("main"), 
	LOGIN_PAGE("login");
	
	private final String page;
	
	PageConst(final String page){
		this.page = page;
	}
	
	@Override
	public String toString() {
		return this.page;
	}

}
