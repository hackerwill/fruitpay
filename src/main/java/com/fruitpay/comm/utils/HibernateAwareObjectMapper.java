package com.fruitpay.comm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
 
public class HibernateAwareObjectMapper extends ObjectMapper {
 
    public HibernateAwareObjectMapper() {
    	Hibernate5Module hm = new Hibernate5Module();;
    	hm.configure(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION, false);
        registerModule(hm); 
    }
}
