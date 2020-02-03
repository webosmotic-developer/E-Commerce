package com.webosmotic.pojo;

import com.webosmotic.entity.ProductCategory;

public class ProductDisplay {
	
	private Long Id;
	private String name;
	private Float price;
	private String images;
	private ProductCategory category;
	private Integer sellCount;
	private Float shippingPrice;
	private Float discount;
	
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
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public Float getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(Float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
}
