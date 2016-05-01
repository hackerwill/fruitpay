package com.fruitpay.util;

import java.io.Serializable;


import org.springframework.mock.web.MockHttpSession;

public class AuthenticationInfo implements Serializable {
	
	private MockHttpSession session;
	
	private String uId;
	
	private String authentication;

	public AuthenticationInfo(MockHttpSession session, String uId,
			String authentication) {
		super();
		this.session = session;
		this.uId = uId;
		this.authentication = authentication;
	}

	public MockHttpSession getSession() {
		return session;
	}

	public void setSession(MockHttpSession session) {
		this.session = session;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

}
