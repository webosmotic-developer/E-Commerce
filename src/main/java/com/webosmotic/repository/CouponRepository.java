package com.webosmotic.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

	Optional<Coupon> findByCouponName(String couponName);
}
