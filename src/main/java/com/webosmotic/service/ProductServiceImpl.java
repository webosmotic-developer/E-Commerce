package com.webosmotic.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.webosmotic.entity.Product;
import com.webosmotic.exception.AppException;
import com.webosmotic.repository.ProductRepository;

public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public Product findById(Long id) {
		Optional<Product> productOpt = productRepository.findById(id);
		if (productOpt.isPresent()) {
			return productOpt.get();
		} else {
			throw new AppException("No product found for the given Product_ID: " + id);
		}
	}

}
