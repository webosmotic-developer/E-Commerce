package com.webosmotic.pojo;

import java.time.LocalDate;

import javax.persistence.Embeddable;

import com.webosmotic.entity.ProductCategory;

@Embeddable
public class ProductInfo {

	private String name;
	private Float price;
	private String icon;
	private ProductCategory category;
	private float amount;
	private float discount;
	private float discountedAmount;
	private float shippingPrice;
	private String sellerName;
	private int quantiy;
	private LocalDate deliveredBy;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public float getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public float getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public String getSellerName() {
		return sellerName;
	}
	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}
	public int getQuantiy() {
		return quantiy;
	}
	public void setQuantiy(int quantiy) {
		this.quantiy = quantiy;
	}
	public LocalDate getDeliveredBy() {
		return deliveredBy;
	}
	public void setDeliveredBy(LocalDate deliveredBy) {
		this.deliveredBy = deliveredBy;
	}
	
	
}
