package com.webosmotic.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@AttributeOverride(name = "Id", column = @Column(name = "product_id"))
public class Product extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties("productList")
	private ProductCategory productCategory;

	@Column(name = "name")
	@NotBlank
	private String name;

	@Column(name = "price")
	private Float price;

	@Column(name = "description")
	@Type(type = "text")
	@NotBlank
	private String description;

	@Column(name = "sku")
	@NotBlank
	private String SKU;

	@Column(name = "stock")
	@NotNull
	private Float stock;

	@Column(name = "cargo_price")
	private Float shippingPrice;

	@Column(name = "tax_percent")
	private Float taxPercent;

	@Column(name = "image")
	private String image;

	@Column(name = "sell_count")
	@JsonIgnore
	private Integer sellCount;
	
	@Column(columnDefinition = "BOOLEAN")
	private boolean showTag;
	
	private String brand;
	
	private int discount;

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
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

	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
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

	public Float getTaxPercent() {
		return taxPercent;
	}

	public void setTaxPercent(Float taxPercent) {
		this.taxPercent = taxPercent;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public boolean isShowTag() {
		return showTag;
	}

	public void setShowTag(boolean showTag) {
		this.showTag = showTag;
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

	@Override
	public String toString() {
		return "Product [productCategory=" + productCategory + ", name=" + name + ", price=" + price + ", longDesc="
				+ description + ", SKU=" + SKU + ", stock=" + stock + ", shippingPrice=" + shippingPrice + ", taxPercent="
				+ taxPercent + ", image=" + image + ", sellCount=" + sellCount + ", showTag=" + showTag + ", brand="
				+ brand + ", discount=" + discount + "]";
	}
	
	
}
