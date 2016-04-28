package com.fruitpay.comm.utils;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class HttpUtil {
	
	private String TEST_DOMAIN_URL = "http://beta.fruitpay.com.tw/fruitpayTest/";
	private String DOMAIN_URL = "http://fruitpay.com.tw/fruitpay/";
	
	@Inject
	private ConfigMap configMap;
	
	public String getDomainURL(HttpServletRequest request){
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();  // includes leading forward slash
		String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;
		return resultPath;
	}
	
	public String getDomainURL() throws Exception{
		String debugMode = configMap.get(ConfigMap.Key.DEBUG_MODE);
		return "true".equals(debugMode) ? TEST_DOMAIN_URL : DOMAIN_URL;
	}

}
