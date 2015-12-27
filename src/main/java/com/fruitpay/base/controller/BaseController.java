package com.fruitpay.base.controller;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	@RequestMapping({"/testPage"})
	public String testPage() {
		
		//List<Customer> customer = customerDAOImpl.listAll();
		
	    return "index.html";   
	}
	
	@RequestMapping({"/"})
	public String showHomePage() {
	    return "index.html";   
	}
	
	@RequestMapping({"/app/**"})
	public String showHomePageByOthers() {
	    return "/index.html";   
	}
	
}

