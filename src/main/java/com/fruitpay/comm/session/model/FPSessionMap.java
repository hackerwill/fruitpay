package com.fruitpay.comm.session.model;


import java.util.Map;

public final class FPSessionMap {
	protected FPSessionMap(){
		
	}
	
	private Map<String, FPSessionInfo> FPSessionMap;

	public Map<String, FPSessionInfo> getFPSessionMap() {		
		return FPSessionMap;
	}	
	protected void setFPSessionMap(Map<String, FPSessionInfo> FPSessionMap){
		this.FPSessionMap=FPSessionMap;
	}
	public void putFPToken(String FPTokenKey, FPSessionInfo FPSessionInfo){		
		this.FPSessionMap.put(FPTokenKey, FPSessionInfo);
	}
	private Map<String,String> logonMap;

	public Map<String, String> getLogonMap() {
		return logonMap;
	}
	protected void setLogonMap(Map<String, String> logonMap) {
		this.logonMap = logonMap;
	}
	
	public void putLogonMap(String biCuid, String logonIp) {
		this.logonMap.put(biCuid, logonIp);
	}
}
