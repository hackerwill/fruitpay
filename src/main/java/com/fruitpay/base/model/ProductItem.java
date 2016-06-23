package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@NamedQuery(name="ProductItem.findAll", query="SELECT p FROM ProductItem p")
@Cacheable
public class ProductItem extends AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_item_id")
	private Integer productItemId;
	
	@Column(name="category_item_id", length=5, unique=true)
	@NotNull
	private String categoryItemId;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	@NotNull
	@JsonBackReference
	private Product product;
	
	@Column(name="name")
	@NotNull
	private String name;
	
	@ManyToOne
	@JoinColumn(name="unit_id")
	@NotNull
	@JsonProperty("unit")
	private ConstantOption unit;
	
	@Column(name="family_amount")
	@NotNull
	private int familyAmount;
	
	@Column(name="single_amount")
	@NotNull
	private int singleAmount;
	
	@Column(name="expiry_order")
	@NotNull
	private int expiryOrder;
	
	@Column(name="placement_order")
	@NotNull
	private int placementOrder;
	
	@Column(name="is_foreign")
	@NotNull
	private int isForeign;

	public Integer getProductItemId() {
		return productItemId;
	}

	public void setProductItemId(Integer productItemId) {
		this.productItemId = productItemId;
	}

	public String getCategoryItemId() {
		return categoryItemId;
	}

	public void setCategoryItemId(String categoryItemId) {
		this.categoryItemId = categoryItemId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ConstantOption getUnit() {
		return unit;
	}

	public void setUnit(ConstantOption unit) {
		this.unit = unit;
	}

	public int getFamilyAmount() {
		return familyAmount;
	}

	public void setFamilyAmount(int familyAmount) {
		this.familyAmount = familyAmount;
	}

	public int getSingleAmount() {
		return singleAmount;
	}

	public void setSingleAmount(int singleAmount) {
		this.singleAmount = singleAmount;
	}

	public int getExpiryOrder() {
		return expiryOrder;
	}

	public void setExpiryOrder(int expiryOrder) {
		this.expiryOrder = expiryOrder;
	}

	public int getPlacementOrder() {
		return placementOrder;
	}

	public void setPlacementOrder(int placementOrder) {
		this.placementOrder = placementOrder;
	}

	public int getIsForeign() {
		return isForeign;
	}

	public void setIsForeign(int isForeign) {
		this.isForeign = isForeign;
	}
	
}