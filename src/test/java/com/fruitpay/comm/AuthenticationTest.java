package com.fruitpay.comm;


import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.fruitpay.base.comm.exception.HttpServiceException;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.CustomerService;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.comm.auth.LoginConst;
import com.fruitpay.comm.model.Role;
import com.fruitpay.comm.session.FPSessionUtil;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.AuthenticationInfo;
import com.fruitpay.util.DataUtil;
import com.fruitpay.util.TestUtil;

import org.hamcrest.core.IsEqual;
import org.junit.Assert;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

@WebAppConfiguration
public class AuthenticationTest extends AbstractSpringJnitTest{
	
	@Inject
    private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Inject
	private DataUtil dataUtil;
	@Inject
	private LoginService loginService;
	@Inject
	private CustomerService customerService;
	
	@Before
    public void setup() {
 
        // Process mock annotations
        MockitoAnnotations.initMocks(this);
        // Setup Spring test in standalone mode
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        		.build();
    
    }

	@Test
	@Transactional
	@Rollback(true)
	public void testLoginAndGetTokenWithoutUid() throws Exception {
		
		//先註冊一個帳號
        Customer loginCustomer = loginService.signup(dataUtil.getSignupCustomer());
        Assert.assertEquals(loginCustomer.getEmail(), dataUtil.getSignupCustomer().getEmail());
		
		this.mockMvc.perform(post("/loginCtrl/login")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytesByGson(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isInternalServerError());
		
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testLoginAndGetTokenWithUidAndValidate() throws Exception {

		MockHttpSession session = new MockHttpSession();
		
		//先註冊一個帳號
        Customer loginCustomer = loginService.signup(dataUtil.getSignupCustomer());
        Assert.assertEquals(loginCustomer.getEmail(), dataUtil.getSignupCustomer().getEmail());
        
        String uId = dataUtil.getUniqueUid(dataUtil.getSignupCustomer());
		
        HttpSession returnSession = this.mockMvc.perform(post("/loginCtrl/login")
				.session(session)
				.header(LoginConst.LOGIN_UID, uId)
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytesByGson(dataUtil.getSignupCustomer())))
	   		.andExpect(status().isOk())
	   		.andReturn()
	   		.getRequest()
	   		.getSession();

		String authentication = (String)returnSession.getAttribute(LoginConst.LOGIN_AUTHORIZATION);

		Assert.assertNotNull(returnSession);
		Assert.assertNotNull(authentication);
		
		String conetent = this.mockMvc.perform(post("/loginCtrl/validateToken")
				.session(session)
				.header(LoginConst.LOGIN_UID, uId)
				.header(LoginConst.LOGIN_AUTHORIZATION, authentication)
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(loginCustomer)))
	   		.andExpect(status().isOk())
	   		.andReturn()
	   		.getResponse()
	   		.getContentAsString();
		
		Assert.assertEquals(conetent, "true");
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetSessionWithDataUtil() throws Exception {
		
		AuthenticationInfo auth = dataUtil.getAuthInfo(mockMvc);
		
		Customer customer = customerService.findByEmail(dataUtil.getSignupCustomer().getEmail());
		
		String conetent = this.mockMvc.perform(post("/loginCtrl/validateToken")
				.session(auth.getSession())
				.header(LoginConst.LOGIN_UID, auth.getuId())
				.header(LoginConst.LOGIN_AUTHORIZATION, auth.getAuthentication())
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(customer)))
	   		.andExpect(status().isOk())
	   		.andReturn()
	   		.getResponse()
	   		.getContentAsString();
		
		Assert.assertEquals(conetent, "true");
		
	}
	
}
