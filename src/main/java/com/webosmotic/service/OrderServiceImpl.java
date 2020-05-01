package com.webosmotic.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webosmotic.Enum.CouponType;
import com.webosmotic.entity.Address;
import com.webosmotic.entity.Coupon;
import com.webosmotic.entity.Order;
import com.webosmotic.entity.User;
import com.webosmotic.exception.AppException;
import com.webosmotic.exception.NotFoundException;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.repository.AddressRepository;
import com.webosmotic.repository.CouponRepository;
import com.webosmotic.repository.OrderRepository;
import com.webosmotic.repository.UserRepository;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRespository;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CouponRepository couponRepository;

	@Override
	public void UpdateShippingAddress(Long addressId, Long orderId) {
		try {
			final Optional<Address> addressOpt = addressRepository.findById(addressId);
			if (!addressOpt.isPresent()) {
				throw new NotFoundException("No Address found for the given " + addressId);
			}
			saveAddressInOrder(addressOpt.get(), orderId);
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public void createAndSaveAddress(MyUserDetail myUser, Address address, Long orderId) {
		try {
			final User user = userRepository.findByUsernameAndEmail(myUser.getUsername(), myUser.getEmail());
			address.setUser(user);
			final Address savedAddress = addressRepository.save(address);
			saveAddressInOrder(savedAddress, orderId);
		} catch (final Exception e) {
			throw e;
		}
	}

	private Order saveAddressInOrder(Address Address, Long orderId) {
		final Optional<Order> orderOpt = orderRespository.findById(orderId);
		if (!orderOpt.isPresent()) {
			throw new NotFoundException("No Order found for the given orderId" + orderId);
		}
		final Order order = orderOpt.get();
		order.setShippingAddress(Address);
		return orderRespository.save(order);
	}

	@Override
	public Order applyCoupon(String couponName, Long orderId) {
		try {
			final Optional<Coupon> couponOpt = couponRepository.findByCouponName(couponName);
			if (!couponOpt.isPresent()) {
				throw new AppException("Invalid coupon");
			}
			final Coupon coupon = couponOpt.get();
			if (coupon.getExpiryDate().isBefore(LocalDate.now())) {
				throw new AppException("Expired coupon");
			}
			final Optional<Order> orderOpt = orderRespository.findById(orderId);
			if (!orderOpt.isPresent()) {
				throw new NotFoundException("No Order found for the given orderId" + orderId);
			}
			final Order order = orderOpt.get();
			if (order.getCoupon() != null) {
				throw new AppException("One coupon is already applied");
			}

			if (coupon.getMinOrderValue() > order.getOrderTotal()) {
				throw new AppException("Minimun Order value " + coupon.getMinOrderValue() + " not reached");
			}

			if (coupon.getType().equals(CouponType.Fixed)) {
				order.setCouponDiscount(coupon.getFlatDiscount());
			}
			if (coupon.getType().equals(CouponType.Discount)) {
				final float discount = (order.getPayableAmount() * coupon.getDiscount()) / 100;
				if (discount > coupon.getMaxDiscount()) {
					order.setCouponDiscount(coupon.getMaxDiscount());
				} else {
					order.setCouponDiscount(discount);
				}
			}
			order.setPayableAmount(order.getPayableAmount() - order.getCouponDiscount());
			order.setCoupon(coupon);
			orderRespository.save(order);
			return order;
		} catch (final Exception e) {
			throw e;
		}
	}

	@Override
	public Order removeCoupon(Long orderId) {
		final Optional<Order> orderOpt = orderRespository.findById(orderId);
		if (!orderOpt.isPresent()) {
			throw new NotFoundException("No Order found for the given orderId" + orderId);
		}
		final Order order = orderOpt.get();
		order.setPayableAmount(order.getPayableAmount() + order.getCouponDiscount());
		order.setCoupon(null);
		order.setCouponDiscount(0);
		orderRespository.save(order);
		return order;
	}
}
