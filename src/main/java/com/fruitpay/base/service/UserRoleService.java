package com.fruitpay.base.service;

import java.util.List;

import com.fruitpay.base.model.Role;
import com.fruitpay.base.model.UserRole;

public interface UserRoleService {
	
	public List<UserRole> findByUserId(int userId);
	
	public UserRole addUserRole(UserRole userRole);
	
	public Role addRole(Role role);

}
