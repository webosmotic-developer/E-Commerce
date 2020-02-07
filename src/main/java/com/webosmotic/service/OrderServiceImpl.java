package com.webosmotic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webosmotic.entity.Address;
import com.webosmotic.entity.Order;
import com.webosmotic.entity.User;
import com.webosmotic.exception.AppException;
import com.webosmotic.exception.NotFoundException;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.repository.AddressRepository;
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

	@Override
	public void UpdateShippingAddress(Long addressId, Long orderId) {
		try {
			Optional<Address> addressOpt = addressRepository.findById(addressId);
			if (!addressOpt.isPresent()) {
				throw new NotFoundException("No Address found for the given exception" + addressId);
			}
			saveAddressInOrder(addressOpt.get(), orderId);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public void createAndSaveAddress(MyUserDetail myUser, Address address, Long orderId) {
		try {
			User user = userRepository.findByUsernameAndEmail(myUser.getUsername(), myUser.getEmail());
			address.setUser(user);
			Address savedAddress = addressRepository.save(address);
			saveAddressInOrder(savedAddress, orderId);
		} catch (Exception e) {
			throw e;
		}
	}

	
	private Order saveAddressInOrder(Address Address, Long orderId) {
		Optional<Order> orderOpt = orderRespository.findById(orderId);
		if (!orderOpt.isPresent()) {
			throw new NotFoundException("No Order found for the given orderId" + orderId);
		}
		Order order = orderOpt.get();
		order.setShippingAddress(Address);
		return orderRespository.save(order);
	}
	

}
