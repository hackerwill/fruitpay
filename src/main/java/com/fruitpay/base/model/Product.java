package com.fruitpay.base.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
@Cacheable
public class Product extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_id")
	private Integer productId;
	
	@Column(name="category_id", unique=true, length=5)
	private String categoryId;
	
	@Column(name="product_english_name")
	private String productEnglishName;

	@Lob
	@Column(name="product_desc")
	private String productDesc;

	@Column(name="product_name")
	private String productName;
	
	@Column(name="image_link")
	private String imageLink;

	@OneToMany(mappedBy="product", fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<ProductItem> productItems;

	public Product() {
	}

	public String getProductEnglishName() {
		return productEnglishName;
	}

	public void setProductEnglishName(String productEnglishName) {
		this.productEnglishName = productEnglishName;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public Integer getProductId() {
		return this.productId;
	}

	public void setProductId(Integer productId) {
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

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

}