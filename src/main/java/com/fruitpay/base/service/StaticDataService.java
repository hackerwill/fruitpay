package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.Product;
import com.fruitpay.base.model.Village;
import com.fruitpay.comm.model.SelectOption;

public interface StaticDataService {
	
	public List<Village> getAllVillages();
	
	public List<SelectOption> getAllCounties();
	
	public List<SelectOption> getTowerships(String countyCode);
	
	public List<SelectOption>  getVillages(String towershipCode);
	
	public List<Product> getAllProducts();

}
