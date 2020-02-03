package com.webosmotic.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "cart")
public class Cart extends UserAudit implements Serializable {

	private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @NotNull
    private List<CartItem> cartItemList = new ArrayList<>();

    @Column(name = "total_price")
    @NotNull
    private Float totalPrice;

    @Column(name = "total_Shipping_price")
    @NotNull
    private Float totalCargoPrice;

	public List<CartItem> getCartItemList() {
		return cartItemList;
	}

	public void setCartItemList(List<CartItem> cartItemList) {
		this.cartItemList = cartItemList;
	}

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
}
