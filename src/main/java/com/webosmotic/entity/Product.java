package com.webosmotic.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "product")
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class Product extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	@JsonIgnoreProperties("productList")
	private ProductCategory productCategory;

	@Column(name = "name")
	@NotBlank
	private String name;

	@Column(name = "unit_price")
	private Float unitPrice;

	@Column(name = "description")
	@Type(type = "text")
	@NotBlank
	private String description;

	@Column(name = "sku" , unique = true)
	@NaturalId
	@NotBlank
	private String SKU;

	@Column(name = "stock")
	@NotNull
	private Integer stock;

	@Column(name = "cargo_price")
	private Float shippingPrice;

	@Column(name = "tax_percent")
	private Float taxPercent;

	@Column(name = "image")
	private String image;

	@Column(name = "sell_count")
	private Integer sellCount;
	
	@Column(columnDefinition = "BOOLEAN")
	private boolean showTag;
	
	private String brand;
	
	private float discount;
	
	private String seller;

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



	public String getSKU() {
		return SKU;
	}

	public void setSKU(String sKU) {
		SKU = sKU;
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

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	@Override
	public String toString() {
		return "Product [productCategory=" + productCategory + ", name=" + name + ", price=" + unitPrice + ", longDesc="
				+ description + ", SKU=" + SKU + ", stock=" + stock + ", shippingPrice=" + shippingPrice + ", taxPercent="
				+ taxPercent + ", image=" + image + ", sellCount=" + sellCount + ", showTag=" + showTag + ", brand="
				+ brand + ", discount=" + discount + "]";
	}

	public Float getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Float unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSellCount() {
		return sellCount;
	}

	public void setSellCount(Integer sellCount) {
		this.sellCount = sellCount;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	
}
