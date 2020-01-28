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

	@Column(name = "long_desc")
	@Type(type = "text")
	@NotBlank
	private String longDesc;

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

	public String getLongDesc() {
		return longDesc;
	}

	public void setLongDesc(String longDesc) {
		this.longDesc = longDesc;
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

	@Override
	public String toString() {
		return "Product [productCategory=" + productCategory + ", name=" + name + ", price=" + price + ", SKU=" + SKU
				+ ", stock=" + stock + ", shippingPrice=" + shippingPrice + ", taxPercent=" + taxPercent + ", cartDesc="
				+ ", longDesc=" + longDesc + ", image=" + image + ", sellCount=" + sellCount + ", creationDate="
				+ creationDate + ", lastModifiedDate=" + lastModifiedDate + "]";
	}
}
