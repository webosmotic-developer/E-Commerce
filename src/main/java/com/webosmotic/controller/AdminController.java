package com.webosmotic.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webosmotic.entity.Coupon;
import com.webosmotic.exception.AppException;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.service.AdminService;

@RestController
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController {

	@Autowired
	AdminService adminService;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "coupon/add", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse> AddCoupons(@RequestBody List<Coupon> coupons) {
		final ApiResponse response = new ApiResponse<>();
		try {
			adminService.addCoupons(coupons);
			response.setSuccess(true);
			response.setData("Coupons Added Successfully");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}
}
