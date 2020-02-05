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

    @OneToMany(mappedBy = "cart", cascade = CascadeType.PERSIST,  fetch = FetchType.EAGER)
    private Set<ProductSummary> products = new HashSet<>();

    @Column(name = "total_price")
    @NotNull
    private Float totalPrice;

    @Column(name = "total_Shipping_price")
    @NotNull
    private Float totalCargoPrice;

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
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

	public void addProduct(ProductSummary summary) {
		if(summary != null) {
			products.add(summary);
		}
		summary.setCart(this);
	}

}
