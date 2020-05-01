package com.webosmotic.entity;

import java.io.Serializable;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart")
@AttributeOverride(name = "id", column = @Column(name = "cart_id"))
public class Cart extends UserAudit implements Serializable {

	private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL,  fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<ProductSummary> products = new HashSet<>();

    @Column(name = "total_price")
    @NotNull
    private Float totalActualPrice;  //totalActualPrice= will be the value customer pay without any discount
    
    @Column(name = "total_discounted_price")
    @NotNull
    private Float totalDiscountedPrice; //totalDiscountedPrice= will be the value customer pay for product

    @Column(name = "total_Shipping_price")
    @NotNull
    private Float totalCargoPrice;
    
    

	public Cart() {
		super();
		this.totalActualPrice = 0f;
		this.totalDiscountedPrice = 0f;
		this.totalCargoPrice =0f;
	}

	public Float getTotalCargoPrice() {
		return totalCargoPrice;
	}

	public void setTotalCargoPrice(Float totalCargoPrice) {
		this.totalCargoPrice = totalCargoPrice;
	}

	public Set<ProductSummary> getProducts() {
		return products;
	}

	public void setProducts(Set<ProductSummary> products) {
		this.products = products;
	}

	public Float getTotalActualPrice() {
		return totalActualPrice;
	}

	public void setTotalActualPrice(Float totalActualPrice) {
		this.totalActualPrice = totalActualPrice;
	}

	public Float getTotalDiscountedPrice() {
		return totalDiscountedPrice;
	}

	public void setTotalDiscountedPrice(Float totalDiscountedPrice) {
		this.totalDiscountedPrice = totalDiscountedPrice;
	}

	
}
