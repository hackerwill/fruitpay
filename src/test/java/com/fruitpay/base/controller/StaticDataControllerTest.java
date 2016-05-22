package com.fruitpay.base.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.controller.StaticDataController;
import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;
import com.fruitpay.base.model.PostalCode;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@WebAppConfiguration
public class StaticDataControllerTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	@Inject
	StaticDataService staticDataService;
	@Inject
	DataUtil dataUtil;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
		MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
 
    }
	
	@Test
	public void findAllTowerships() throws Exception {
		List<PostalCode> postCodes = staticDataService.getAllPostalCodes();
		Assert.assertEquals(371, postCodes.size());
	}
	
	@Test
	public void getAdminConstantShouldReturnConstantList() throws Exception{
		this.mockMvc.perform(get("/staticDataCtrl/adminConstant")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(greaterThan(0))));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void postAdminConstantShouldAddNewConst() throws Exception{
		//test const add
		this.mockMvc.perform(post("/staticDataCtrl/adminConstant")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(dataUtil.getConstant())))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.constName", is(dataUtil.getConstant().getConstName())));
		
		//get record
		Constant newConstant = staticDataService.getAllConstants().stream()
				.filter(constant -> constant.getConstName().equals(dataUtil.getConstant().getConstName()))
				.collect(Collectors.toList())
				.get(0);
		
		//test const update
		String updateConstName = "update";
		newConstant.setConstName(updateConstName);
		this.mockMvc.perform(put("/staticDataCtrl/adminConstant")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(newConstant)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.constName", is(updateConstName)));
		
		//test constOption add
		this.mockMvc.perform(post("/staticDataCtrl/adminConstant/constOption")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytesByGson(dataUtil.getConstantOption(newConstant))))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.optionName", is(dataUtil.getConstantOption(newConstant).getOptionName())));
		
		//get record
		ConstantOption newConstantOption = staticDataService.getConstantOptionByName(dataUtil.getConstantOption(newConstant).getOptionName());
		
		//test constOption update
		String updateConstOptionName = "update";
		newConstantOption.setOptionName(updateConstOptionName);
		newConstant.setConstName(updateConstName);
		this.mockMvc.perform(put("/staticDataCtrl/adminConstant/constOption")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytesByGson(newConstantOption)))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.optionName", is(updateConstOptionName)));
		
	}
	
}
