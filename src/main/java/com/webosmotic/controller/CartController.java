package com.webosmotic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webosmotic.entity.Cart;
import com.webosmotic.exception.AppException;
import com.webosmotic.exception.NotFoundException;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.pojo.CartCheckOutResponse;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.pojo.ProductSummaryUpdateRequest;
import com.webosmotic.service.CartService;
import com.webosmotic.util.SecurityUtil;

@RestController
@RequestMapping("/cart")
@Secured({"ROLE_BUYER","ROLE_ADMIN"})
public class CartController {

	@Autowired
	CartService cartService;

	/*
	 * API to fetch the cart for the given user
	 * 
	 * @return Cart object
	 */
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> fetchCartForUser() {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {
			Cart cart = cartService.fecthUserCart(SecurityUtil.getUser());
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to add the product to the cart for the given user
	 * @PathVariable Long product id
	 * @return Cart object
	 */
	@RequestMapping(value = "/product/{product_id}/add", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> addNewProductToCart(@PathVariable("product_id") Long productId) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {
			MyUserDetail user = SecurityUtil.getUser();
			if(user == null) {
				throw new NotFoundException("No LoggedIn user found");
			}
			Cart cart = cartService.addToCart(productId, user);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to update the product in the cart
	 * @PathVariable ProductSummaryUpdateRequest object
	 * @return Cart object
	 */
	@RequestMapping(value = "product/update", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<Cart>> updateCartItem(
			@RequestBody ProductSummaryUpdateRequest request) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {
			Cart cart = cartService.updateCartItemInCart(request);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	 
	/*
	 * API to remove the product in the cart
	 * @PathVariable ProductSummaryUpdateRequest object
	 * @return Cart object
	 */
	@RequestMapping(value = "cartitem/remove/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Cart>> removeProductFromCart(
			@PathVariable("id") Long id) {
		ApiResponse<Cart> response = new ApiResponse<>();
		try {
			Cart cart = cartService.removeProductFromCart(id);
			response.setSuccess(true);
			response.setData(cart);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to checkout the product from the cart and create an order
	 * @PathVariable Long Cart_Id
	 * @return OrderID 
	 */
	@RequestMapping(value = "/checkout/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<CartCheckOutResponse>> cartCheckOut(@PathVariable("id") Long cartId) {
		ApiResponse<CartCheckOutResponse> response = new ApiResponse<>();
		try {
			CartCheckOutResponse checkout = cartService.createOrderForCart(SecurityUtil.getUser(), cartId);
			response.setSuccess(true);
			response.setData(checkout);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
}
