package com.webosmotic.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webosmotic.pojo.ProductInfo;

@Entity
@Table(name = "cartItem")
public class CartItem extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Embedded
    ProductInfo productInfo;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "cart_id")
    @JsonIgnoreProperties("cartItemList") //Prevents infinite recursion
    @JsonIgnore
    private Cart cart;

	public ProductInfo getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(ProductInfo productInfo) {
		this.productInfo = productInfo;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
}

