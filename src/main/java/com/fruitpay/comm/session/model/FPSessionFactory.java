package com.fruitpay.comm.session.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FPSessionFactory {
	private static final Log log = LogFactory.getLog(FPSessionFactory.class);
	private static FPSessionMap instance = null;
    private FPSessionFactory(){}
    public static FPSessionMap getInstance() {
        if (instance == null){
            synchronized(FPSessionMap.class){
                if(instance == null) {
                	try{
                		 instance = initialSessionMap();
                	}catch(Exception e){
                		log.error("Initial FP Session Map fail", e);
                	}                    
                }
            }
        }
        return instance;
    }
    
    /**
     * Initial FP Session Map
     * @return
     */
    private final static FPSessionMap initialSessionMap() throws Exception{
    	FPSessionMap tFPSessionMapObj = new FPSessionMap();
    	Map<String,FPSessionInfo> FPSessionMap = new HashMap<String, FPSessionInfo>();
    	tFPSessionMapObj.setFPSessionMap(FPSessionMap);
    	Map<String,String> logonMap = new HashMap<String,String>();
    	tFPSessionMapObj.setLogonMap(logonMap);
    	return tFPSessionMapObj;
    }
}
