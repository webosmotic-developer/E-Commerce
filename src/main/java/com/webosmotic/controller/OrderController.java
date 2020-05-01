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

import com.webosmotic.entity.Address;
import com.webosmotic.exception.AppException;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.service.OrderService;
import com.webosmotic.util.SecurityUtil;

@RestController
@RequestMapping("/order")
@Secured({ "ROLE_BUYER", "ROLE_ADMIN" })
public class OrderController {

	@Autowired
	OrderService orderService;

	/*
	 * API to add the shipping address to the order
	 * 
	 * @PathVariable Long OrderId , Long addrresId
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "{orderId}/address/{addressId}/add", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> UpdateAddressToOrder(@PathVariable("addressId") Long addressId,
			@PathVariable("orderId") Long orderId) {
		final ApiResponse response = new ApiResponse<>();
		try {
			orderService.UpdateShippingAddress(addressId, orderId);
			response.setSuccess(true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to create new shipping address for user and add to the order
	 * 
	 * @PathVariable Long OrderId , Long addrresId
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "{orderId}/address/save", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> CreateAndSaveAddressToOrder(@RequestBody Address address,
			@PathVariable("orderId") Long orderId) {
		final ApiResponse response = new ApiResponse<>();
		try {
			final MyUserDetail user = SecurityUtil.getUser();
			orderService.createAndSaveAddress(user, address, orderId);
			response.setSuccess(true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to apply coupon on the Order after validating
	 * 
	 * @PathVariable Long OrderId , String couponName
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "{orderId}/coupon/{couponName}", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> ApplyCouponToOrder(@PathVariable("orderId") Long orderId,
			@PathVariable("couponName") String couponName) {
		final ApiResponse response = new ApiResponse<>();
		try {
			response.setData(orderService.applyCoupon(couponName, orderId));
			response.setSuccess(true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to remove applied coupon on the Order
	 * 
	 * @PathVariable Long OrderId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "{orderId}/removecoupon", method = RequestMethod.PUT)
	public ResponseEntity<ApiResponse> ApplyCouponToOrder(@PathVariable("orderId") Long orderId) {
		final ApiResponse response = new ApiResponse<>();
		try {
			response.setData(orderService.removeCoupon(orderId));
			response.setSuccess(true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}

}
