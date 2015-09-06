package com.fruitpay.base.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fruitpay.base.dao.MemberDao;
import com.fruitpay.base.model.LoginBean;

@Service("FakeMemberDaoImpl")
public class FakeMemberDaoImpl implements MemberDao{

	private static List<LoginBean> loginBeans; 
	
	static{
		loginBeans = new ArrayList<LoginBean>();
		loginBeans.add(new LoginBean("aaa@hotmail.com", "12345"));
	}
	
	@Override
	public boolean isMemberFound(LoginBean loginBean) {

		String email = loginBean.getEmail();
		String password = loginBean.getPassword();
		
		for (Iterator<LoginBean> iterator = loginBeans.iterator(); iterator.hasNext();) {
			LoginBean userLoginBean = iterator.next();
			if(email.equals(userLoginBean.getEmail())&& password.equals(userLoginBean.getPassword())){
				return true;
			}
		}
		
		return false;
	}
	
	

}
