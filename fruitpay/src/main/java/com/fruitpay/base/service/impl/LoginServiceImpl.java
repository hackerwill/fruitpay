package com.fruitpay.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fruitpay.base.dao.MemberDao;
import com.fruitpay.base.model.LoginBean;
import com.fruitpay.base.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	@Qualifier("FakeMemberDaoImpl")
	MemberDao memberDao;
	
	@Override
	public boolean isEmailExisted(LoginBean loginBean) {
		return memberDao.isMemberFound(loginBean);
	}

}
