package com.webosmotic.pojo;

/**
 * @author ADMIN
 *
 */
public class ProductSearchCriteria {

	private String name;
	private String category;
	private String subCategory;
	private String brand;
	private String description;
	private int discount;
	private int unitPrice;
	private int minPrice;
	private int maxPrice;
	private int minDiscount;
	private int maxDiscount;
	private String all;
	
	public ProductSearchCriteria() {
		super();
	}
	
	public ProductSearchCriteria(String category) {
		this.subCategory="";
		this.category=category;
		this.name="";
		this.brand="";
		this.description="";
		this.discount=0;
		this.unitPrice=0;
		this.minDiscount=0;
		this.maxDiscount=0;
		this.all="";
		this.minPrice=0;
		this.maxPrice=0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAll() {
		return all;
	}

	public void setAll(String all) {
		this.all = all;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getMinDiscount() {
		return minDiscount;
	}

	public void setMinDiscount(int minDiscount) {
		this.minDiscount = minDiscount;
	}

	public int getMaxDiscount() {
		return maxDiscount;
	}

	public void setMaxDiscount(int maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

}
