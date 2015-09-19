package com.fruitpay.base.dao;

import com.fruitpay.base.model.LoginBean;

public interface MemberDao {
	
	public boolean isMemberFound(LoginBean loginBean);
}
