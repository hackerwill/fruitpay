package com.fruitpay.base.comm;

public enum ProgramId {
	SINGLE(1), 
	FAMILY(2);
	
	private final int programId;
	
	ProgramId(final int programId){
		this.programId = programId;
	}
	
	public int value() {
		return this.programId;
	}

}
