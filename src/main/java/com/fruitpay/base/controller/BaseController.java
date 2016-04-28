package com.fruitpay.base.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BaseController {
	
	@RequestMapping({"/"})
	public String showHomePage() {
	    return "index.html";   
	}
	
	@RequestMapping({"/app/**"})
	public String showHomePageByOthers() {
	    return "/index.html";   
	}
	
	@RequestMapping({"/admin/"})
	public String showAdminPage() {
	    return "/admin/index.html";   
	}
	
}

