package com.webosmotic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webosmotic.entity.Cart;
import com.webosmotic.entity.CartItem;
import com.webosmotic.entity.MyUserDetail;
import com.webosmotic.exception.AppException;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.service.CartService;

import javassist.NotFoundException;

@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@RequestMapping(value = "/product/{product_id}/add", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> addNewProductToCart(@PathVariable("product_id") Long productId) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {

			Cart cart = cartService.addToCart(productId);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "cartitem/remove/{id}/", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> removeProductFromCart(@PathVariable("id") Long id) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {

			Cart cart = cartService.removeFromCart(id);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "cartitem/update", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<Cart>> updateCartItem(
			@RequestBody CartItem item) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {

			Cart cart = cartService.updateCartItemInCart(item);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> fetchCartForUser() {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {
			MyUserDetail user = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user == null) {
				throw new NotFoundException("No user found ");
			}
			Cart cart = cartService.fecthUserCart(user);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/checkout/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> cartCheckOut(
			@PathVariable("id") Long cartId) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {
			MyUserDetail user = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user == null) {
				throw new NotFoundException("No user found ");
			}
			Cart cart = cartService.createOrderForCart(user, cartId);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

}
