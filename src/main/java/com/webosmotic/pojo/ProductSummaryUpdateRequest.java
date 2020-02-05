package com.webosmotic.pojo;

import io.micrometer.core.lang.NonNull;

public class ProductSummaryUpdateRequest {
	
	@NonNull
	private Long id;
	private Long productId;
	private int quantity;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
