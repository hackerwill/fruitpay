package com.fruitpay.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.fruitpay.base.model.Customer;
import com.fruitpay.base.service.LoginService;
import com.fruitpay.base.service.UserRoleService;
import com.fruitpay.base.model.Role;
import com.fruitpay.base.model.UserRole;
import com.fruitpay.util.AbstractSpringJnitTest;
import com.fruitpay.util.DataUtil;

import java.util.List;

import javax.inject.Inject;

@WebAppConfiguration
public class UserRoleServiceTest extends AbstractSpringJnitTest{
	
	@Inject
	private UserRoleService userRoleService;
	@Inject
	private LoginService loginService;
	
	@Inject
	private DataUtil dataUtil;
	
	@Test
	@Rollback(true)
	@Transactional
	public void addUserAndGrantUserRole() throws Exception {
		
		Customer customer = loginService.signup(dataUtil.getSignupCustomer());
		Role role = userRoleService.addRole(dataUtil.getRole());
		
		UserRole userRole = dataUtil.getUserRole(customer, role);
		
		userRole = userRoleService.addUserRole(userRole);
		
		List<UserRole> returnUserRoles = userRoleService.findByUserId(customer.getCustomerId());
		
		Assert.assertEquals(returnUserRoles.size(), 1);
		Assert.assertEquals(userRole.getUserRoleId(), returnUserRoles.get(0).getUserRoleId());
		
	}
	
}
