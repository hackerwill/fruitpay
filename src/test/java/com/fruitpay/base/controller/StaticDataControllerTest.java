package com.fruitpay.base.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fruitpay.base.model.Towership;
import com.fruitpay.base.model.Village;
import com.fruitpay.base.service.StaticDataService;
import com.fruitpay.comm.model.SelectOption;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.TestUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class StaticDataControllerTest extends AbstractSpringJnitTest{
	
	@InjectMocks
	StaticDataController staticDataController;
	@Mock
	StaticDataService staticDataServiceMock;
	@Inject
	StaticDataService staticDataService;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.standaloneSetup(staticDataController).build();
 
    }

	@Test
	public void findAllCounties() throws Exception {
		SelectOption first = new SelectOption(1, "123");
		SelectOption second = new SelectOption(2, "345");
		
		
		when(staticDataServiceMock.getAllCounties()).thenReturn(Arrays.asList(first, second));
	
		this.mockMvc.perform(get("/staticDataCtrl/getAllCounties"))
	   		.andExpect(status().isOk())
	   		.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
	   		.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id", is(first.getId())))
			.andExpect(jsonPath("$[0].name", is(first.getName())))
			.andExpect(jsonPath("$[1].id", is(second.getId())))
			.andExpect(jsonPath("$[1].name", is(second.getName())));
		
		verify(staticDataServiceMock, times(1)).getAllCounties();
	    verifyNoMoreInteractions(staticDataServiceMock);
	}
	
	@Test
	public void verifyVillageFields() throws Exception {
		String villageCode = "1000201-001";
		Village village = staticDataService.getVillage(villageCode);
		Assert.assertEquals(villageCode, village.getVillageCode());
		
	}
	
	@Test
	public void verifyTowershipFields() throws Exception {
		String towershipCode = "1000201";
		Towership towership = staticDataService.getTowership(towershipCode);
		Assert.assertEquals(towershipCode, towership.getTowershipCode());
		
	}
	
	@Test
	public void findAllTowerships() throws Exception {
		List<Towership> towerships = staticDataService.getAllTowerships();
		Assert.assertEquals(368, towerships.size());
	}
}
