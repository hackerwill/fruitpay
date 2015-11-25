package com.fruitpay.comm.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
	
	public static String getDomainURL(HttpServletRequest request){
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();  // includes leading forward slash
		String resultPath = scheme + "://" + serverName + ":" + serverPort + contextPath;
		return resultPath;
	}

}
