package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the Product database table.
 * 
 */
@Entity
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="product_id")
	private int productId;

	@Lob
	@Column(name="product_desc")
	private String productDesc;

	@Column(name="product_name")
	private String productName;
	
	//bi-directional many-to-one association to Unit
	@ManyToOne
	@JoinColumn(name="unit_id")
	private Unit unit;

	//bi-directional many-to-one association to ShipmentDetail
	@OneToMany(mappedBy="product")
	private List<ShipmentDetail> shipmentDetails;

	public Product() {
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
		return this.unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public List<ShipmentDetail> getShipmentDetails() {
		return this.shipmentDetails;
	}

	public void setShipmentDetails(List<ShipmentDetail> shipmentDetails) {
		this.shipmentDetails = shipmentDetails;
	}

	public ShipmentDetail addShipmentDetail(ShipmentDetail shipmentDetail) {
		getShipmentDetails().add(shipmentDetail);
		shipmentDetail.setProduct(this);

		return shipmentDetail;
	}

	public ShipmentDetail removeShipmentDetail(ShipmentDetail shipmentDetail) {
		getShipmentDetails().remove(shipmentDetail);
		shipmentDetail.setProduct(null);

		return shipmentDetail;
	}

}