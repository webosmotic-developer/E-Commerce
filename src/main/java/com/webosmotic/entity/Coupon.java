package com.webosmotic.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.webosmotic.Enum.CouponType;

@Entity
@AttributeOverride(name = "id", column = @Column(name = "coupon_id"))
@Table (name = "coupons")
public class Coupon extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "coupon_name",unique = true)
	@NotEmpty
	private String couponName;
	
	@NotNull
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private CouponType type;
	
	@Column(name = "flat_discount")
	private int flatDiscount;
	
	
	private float discount;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	
	@NotNull
	@Column(name = "expiry_date")
	private LocalDate expiryDate;
	
	
	@Column(name = "min_order_value")
	@NotNull
	private int minOrderValue;
	
	@Column(name = "max_discount")
	private int maxDiscount;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "coupon")
	private List<Order> order= new ArrayList<>();
	
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public CouponType getType() {
		return type;
	}

	public void setType(CouponType type) {
		this.type = type;
	}

	public int getFlatDiscount() {
		return flatDiscount;
	}

	public void setFlatDiscount(int flatDiscount) {
		this.flatDiscount = flatDiscount;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public float getMinOrderValue() {
		return minOrderValue;
	}

	public void setMinOrderValue(int minOrderValue) {
		this.minOrderValue = minOrderValue;
	}

	public float getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(int maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

}
