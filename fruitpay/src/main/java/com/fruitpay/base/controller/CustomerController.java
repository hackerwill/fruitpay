package com.fruitpay.base.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fruitpay.base.dao.CustomerDAO;
import com.fruitpay.base.model.Area;
import com.fruitpay.base.model.City;
import com.fruitpay.base.model.Customer;

@Controller
public class CustomerController {
	
	@Autowired
	CustomerDAO customerDAO;
	
	@RequestMapping(value = "/getAllCustomer", method = RequestMethod.POST)
	public @ResponseBody List<Customer> getAllCustomer(){
		
		List<Customer> customers = customerDAO.listAll();
		Area area = customers.get(0).getArea();
		ObjectMapper mapper = new ObjectMapper();
		
		File json = new File("D://Will//temp.json");
        try {
			mapper.writeValue(json, customers.get(0));
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		return customers;
	}

}
