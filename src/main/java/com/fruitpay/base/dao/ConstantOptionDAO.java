package com.fruitpay.base.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fruitpay.base.model.Constant;
import com.fruitpay.base.model.ConstantOption;

public interface ConstantOptionDAO extends JpaRepository<ConstantOption, Integer> {

	public ConstantOption findByOptionName(String optionName);
	
	Page<ConstantOption> findByConstant(Constant constant, Pageable pageable);
}
