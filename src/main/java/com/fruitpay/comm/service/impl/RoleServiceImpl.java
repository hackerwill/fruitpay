package com.fruitpay.comm.service.impl;

import org.springframework.stereotype.Service;

import com.fruitpay.comm.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	private final String MANAGER_ID = "FruitpayAdmin";

	@Override
	public boolean isAdmin(String userId) {
		if(userId.equals(MANAGER_ID))
			return true;
		else
			return false;
	}

}
