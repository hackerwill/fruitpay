package com.fruitpay.base.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the OrderPreference database table.
 * 
 */
@Entity
@NamedQuery(name="OrderPreference.findAll", query="SELECT o FROM OrderPreference o")
public class OrderPreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrderPreferencePK id;

	@Column(name="like_degree")
	private byte likeDegree;

	public OrderPreference() {
	}

	public OrderPreferencePK getId() {
		return this.id;
	}

	public void setId(OrderPreferencePK id) {
		this.id = id;
	}

	public byte getLikeDegree() {
		return this.likeDegree;
	}

	public void setLikeDegree(byte likeDegree) {
		this.likeDegree = likeDegree;
	}

}