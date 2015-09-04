package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Test database table.
 * 
 */
@Entity
@NamedQuery(name="Test.findAll", query="SELECT t FROM Test t")
public class Test implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer test;

	private String test1;

	private String test2;

	public Test() {
	}

	public int getTest() {
		return this.test;
	}

	public void setTest(int test) {
		this.test = test;
	}

	public String getTest1() {
		return this.test1;
	}

	public void setTest1(String test1) {
		this.test1 = test1;
	}

	public String getTest2() {
		return this.test2;
	}

	public void setTest2(String test2) {
		this.test2 = test2;
	}

}