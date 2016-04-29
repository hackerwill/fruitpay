package com.fruitpay.comm.session.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FPSessionInfo implements Serializable{
	private static final long serialVersionUID = -5220247136592562655L;

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	private String FPToken;
	
	private String userId;
	
	private String userName;
	
	private String logonTime;
	
	private String lastAccessTime;
	
	private String sessionId;
	
	private String redirectUrl;

	private Locale userLocale;
	
	private String serSession;
	
	private String logonAddress;
	
	private String userCuid;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLogonTime() {
		return logonTime;
	}

	public void setLogonTime(Date date) {		
		this.logonTime = sdf.format(date);
	}

	public String getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date date) {
		this.lastAccessTime = sdf.format(date);
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getFPToken() {
		return FPToken;
	}

	public void setFPToken(String FPToken) {
		this.FPToken = FPToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Locale getUserLocale() {
		return userLocale;
	}

	public void setUserLocale(Locale userLocale) {
		this.userLocale = userLocale;
	}

	public String getSerSession() {
		return serSession;
	}

	public void setSerSession(String serSession) {
		this.serSession = serSession;
	}

	public String getLogonAddress() {
		return logonAddress;
	}

	public void setLogonAddress(String logonAddress) {
		this.logonAddress = logonAddress;
	}

	public String getUserCuid() {
		return userCuid;
	}

	public void setUserCuid(String userCuid) {
		this.userCuid = userCuid;
	}

	public void setLogonTime(String logonTime) {
		this.logonTime = logonTime;
	}

	public void setLastAccessTime(String lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
	
	
}
