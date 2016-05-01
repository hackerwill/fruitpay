package com.fruitpay.base.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fruitpay.base.dao.RoleDAO;
import com.fruitpay.base.dao.UserRoleDAO;
import com.fruitpay.base.model.Customer;
import com.fruitpay.base.model.Role;
import com.fruitpay.base.model.UserRole;
import com.fruitpay.base.service.UserRoleService;

@Service
public class UserRoleServiceImpl implements UserRoleService {
	
	@Inject
	private UserRoleDAO userRoleDAO;
	
	@Inject
	private RoleDAO roleDAO;

	@Override
	public List<UserRole> findByUserId(int userId) {
		Customer customer = new Customer();
		customer.setCustomerId(userId);
		List<UserRole> userRoles = userRoleDAO.findByCustomer(customer);
		
		return userRoles;
	}

	@Override
	public UserRole addUserRole(UserRole userRole) {
		userRole = userRoleDAO.save(userRole);
		return userRole;
	}

	@Override
	public Role addRole(Role role) {
		role = roleDAO.save(role);
		return role;
	}

}
