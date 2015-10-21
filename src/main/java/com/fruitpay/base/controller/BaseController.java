package com.fruitpay.base.controller;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
	
	private final Logger logger = Logger.getLogger(this.getClass());
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

