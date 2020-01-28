package com.webosmotic.pojo;

public class ProductDisplay {
	
	private Long Id;
	private String name;
	private Float price;
	private String images;
	private String category;
	private Integer sellCount;
	private Float stock;
	private Float shippingPrice;
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getSellCount() {
		return sellCount;
	}
	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}
	public Float getStock() {
		return stock;
	}
	public void setStock(Float stock) {
		this.stock = stock;
	}
	public Float getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(Float shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
}
