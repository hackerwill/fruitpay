package com.fruitpay.comm.utils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/*
 * In some situation, we need to use dependencied injection but may not make the bean controlled by spring,
 * such as EntityListner. In this kind of situation, use this hack to get all cached server.
 * 
 */
@Component
public class SpringApplicationContext implements ApplicationContextAware {
  private static ApplicationContext CONTEXT;
  private static final Logger logger = Logger.getLogger(SpringApplicationContext.class);

  public void setApplicationContext(final ApplicationContext context)
              throws BeansException {
    CONTEXT = context;
  }

  public static <T> T getBean(Class<T> clazz) { 
	  try{
		  return CONTEXT.getBean(clazz); 
	  }catch(Exception e){
		  logger.error("SpringApplicationContext getBean failed.");
	  }
	  return null;
  }
}