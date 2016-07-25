package com.fruitpay.base.comm;

public class CommConst {
	
	public enum VALID_FLAG{
		VALID(1), 
		INVALID(0);
		
		private int flag;
		
		private VALID_FLAG(int flag){
			this.flag = flag;
		}
		
		public int value(){
			return this.flag;
		}
	}
	
	public enum CREDIT_CARD_PERIOD {
		PERIOD(28);
		
		private int value;
		
		private CREDIT_CARD_PERIOD(int value){
			this.value = value;
		}
		
		public int value(){
			return this.value;
		}
	}
	
}

