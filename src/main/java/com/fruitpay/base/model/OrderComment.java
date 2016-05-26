package com.fruitpay.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@NamedQuery(name="OrderComment.findAll", query="SELECT c FROM OrderComment c")
public class OrderComment extends AbstractEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="comment_id")
	private Integer commentId;
	
	@ManyToOne
	@JoinColumn(name="order_id")
	@JsonBackReference
	private CustomerOrder customerOrder;
	
	@Column(name="comment")
	private String comment;

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
