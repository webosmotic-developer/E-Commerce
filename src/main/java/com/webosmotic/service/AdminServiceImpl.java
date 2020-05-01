package com.webosmotic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webosmotic.Enum.CouponType;
import com.webosmotic.entity.Coupon;
import com.webosmotic.exception.NotFoundException;
import com.webosmotic.repository.CouponRepository;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	CouponRepository couponRepository;

	@Override
	public void addCoupons(List<Coupon> coupons) {
		for (Coupon coupon : coupons) {
			if (coupon.getType().equals(CouponType.Fixed)) {
				if (coupon.getFlatDiscount() == 0) {
					throw new NotFoundException("FlatDiscount cant be zero for type Fixed");
				}
			} else if (coupon.getType().equals(CouponType.Discount)) {
				if (coupon.getDiscount() == 0 || coupon.getMaxDiscount() == 0) {
					throw new NotFoundException("discount & maxDiscount cant be zero for type Discount");
				}
			}
		}
		couponRepository.saveAll(coupons);
	}
}
