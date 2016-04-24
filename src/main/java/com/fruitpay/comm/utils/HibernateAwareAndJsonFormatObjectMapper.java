package com.fruitpay.comm.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
 
public class HibernateAwareAndJsonFormatObjectMapper extends ObjectMapper {
 
    public HibernateAwareAndJsonFormatObjectMapper() {
    	Hibernate5Module hm = new Hibernate5Module();;
    	hm.configure(Hibernate5Module.Feature.USE_TRANSIENT_ANNOTATION, false);
        registerModule(hm);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.setDateFormat(df);
    }
}
