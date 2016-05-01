package com.fruitpay.base.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fruitpay.base.model.Role;

public interface RoleDAO extends JpaRepository<Role, Integer> {

}
