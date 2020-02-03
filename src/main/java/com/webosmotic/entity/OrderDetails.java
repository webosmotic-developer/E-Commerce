package com.webosmotic.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webosmotic.pojo.ProductInfo;

@Entity
@AttributeOverride(name = "Id", column = @Column(name = "orderDetail_id"))
public class OrderDetails extends BaseEntity {

	@Embedded
    ProductInfo productInfo;
	
	@ManyToOne
	@JoinColumn(name = "order_id")
	@JsonIgnoreProperties("orderDetailsList")
	@JsonIgnore
	private Order order;

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	   
	
}
