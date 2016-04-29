package com.fruitpay.comm.utils;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import javassist.NotFoundException;

@Component
public class ConfigMap {
	
	public enum Key{
		DEBUG_MODE;
	}

	@Resource(name="configXmlMap")
	Map<String, String> configXmlMap;
	
	public String get(ConfigMap.Key key) throws NotFoundException{
		if(AssertUtils.isEmpty(configXmlMap.get(key.name())))
			throw new NotFoundException("The value in Config Map is not found, key :" + key.name());
		
		return configXmlMap.get(key.name());
	}
	
	

}
