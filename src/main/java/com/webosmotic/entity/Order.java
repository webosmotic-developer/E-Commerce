package com.webosmotic.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
@AttributeOverride(name = "Id", column = @Column(name = "order_id"))
public class Order extends UserAudit implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<OrderDetails> orderDetailsList = new HashSet<>();
	
	@Column(name = "Order_Number")
	private String orderNumber;
	
	@OneToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address shippingAddress;
	
	@Column(name = "Tracking_Number")
	private String trackingNumber;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
    private OrderStatus status; 
	
	private String statusComment;
	
	private float orderTotal;
	
	private float shippingCharge;
	
	private float payableAmount;
	
	private LocalDate deliveryDate;

	public Set<OrderDetails> getOrderDetailsList() {
		return orderDetailsList;
	}

	public void setOrderDetailsList(Set<OrderDetails> orderDetailsList) {
		this.orderDetailsList = orderDetailsList;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getStatusComment() {
		return statusComment;
	}

	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
	}

	public float getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}

	public float getShippingCharge() {
		return shippingCharge;
	}

	public void setShippingCharge(float shippingCharge) {
		this.shippingCharge = shippingCharge;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public float getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(float payableAmount) {
		this.payableAmount = payableAmount;
	}
}
