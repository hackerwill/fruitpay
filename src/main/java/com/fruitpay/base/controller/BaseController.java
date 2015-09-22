package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fruitpay.base.comm.PageConst;
import com.fruitpay.base.dao.impl.CustomerDAOImpl;
import com.fruitpay.base.model.Customer;

@Controller
public class BaseController {
	
	private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
	private static int counter = 0;

//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String welcome(ModelMap model) {
//
//		model.addAttribute("message", "Welcome");
//		model.addAttribute("counter", ++counter);
//		logger.debug("[welcome] counter : {}", counter);
//
//		// Spring uses InternalResourceViewResolver and return back index.jsp
//		return PageConst.MAIN_PAGE.toString();
//
//	}
	
	@Inject
	private CustomerDAOImpl customerDAOImpl;

	@RequestMapping({"/testPage"})
	public String testPage() {
		
		//List<Customer> customer = customerDAOImpl.listAll();
		
	    return "index.html";   
	}
	
	@RequestMapping({"/"})
	public String showHomePage() {
	    return "index.html";   
	 }

}

