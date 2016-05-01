package com.fruitpay.comm.utils;
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

  public void setApplicationContext(final ApplicationContext context)
              throws BeansException {
    CONTEXT = context;
  }

  public static <T> T getBean(Class<T> clazz) { return CONTEXT.getBean(clazz); }
}