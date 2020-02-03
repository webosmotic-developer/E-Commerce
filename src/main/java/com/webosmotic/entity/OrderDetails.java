package com.webosmotic.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webosmotic.entity.BaseEntity;
import com.webosmotic.entity.Order;
import com.webosmotic.entity.ProductCategory;

@Entity
@AttributeOverride(name = "Id", column = @Column(name = "orderDetail_id"))
public class OrderDetails extends BaseEntity {

	private Long Id;
	private String name;
	private Float price;
	private String icon;
	private ProductCategory category;
	private Float shippingPrice;
	private Float discount;
	private String sellerName;
	private int quantiy;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnoreProperties("orderDetailsList")
	@JsonIgnore
	private Order order;
	   
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

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

	
	public Float getShippingPrice() {
		return shippingPrice;
	}

	public void setShippingPrice(Float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
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

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
