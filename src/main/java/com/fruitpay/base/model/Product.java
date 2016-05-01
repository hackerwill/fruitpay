package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_id")
	private int productId;
	
	@Column(name="product_english_name")
	private String productEnglishName;

	@Lob
	@Column(name="product_desc")
	private String productDesc;

	@Column(name="product_name")
	private String productName;
	
	@Column(name="image_link")
	private String imageLink;

	@ManyToOne
	@JoinColumn(name="unit_id")
	private Unit unit;

	public Product() {
	}

	public String getProductEnglishName() {
		return productEnglishName;
	}

	public void setProductEnglishName(String productEnglishName) {
		this.productEnglishName = productEnglishName;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductDesc() {
		return this.productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}


}