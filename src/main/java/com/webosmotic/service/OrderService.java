package com.webosmotic.service;

import com.webosmotic.entity.Address;
import com.webosmotic.entity.Order;
import com.webosmotic.pojo.MyUserDetail;

public interface OrderService {

	void UpdateShippingAddress(Long addressId, Long orderId);

	void createAndSaveAddress(MyUserDetail user, Address address, Long orderId);
	
	Order applyCoupon(String CouponName, Long orderId);

	Order removeCoupon(Long orderId);

}
