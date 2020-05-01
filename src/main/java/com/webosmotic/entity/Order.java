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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.webosmotic.Enum.OrderStatus;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "order_id"))
@Table(name = "orders")
public class Order extends UserAudit implements Serializable {

	private static final long serialVersionUID = 1L;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<ProductSummary> products = new HashSet<>();

	@NaturalId
	@Column(name = "Order_Number", unique = true)
	private String orderNumber;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
			CascadeType.DETACH })
	@JoinColumn(name = "shippingAddress_Id")
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

	@ManyToOne
	private Coupon coupon;

	private float couponDiscount;

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

	public Set<ProductSummary> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductSummary> products) {
		this.products = products;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

	public float getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(float couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

}
