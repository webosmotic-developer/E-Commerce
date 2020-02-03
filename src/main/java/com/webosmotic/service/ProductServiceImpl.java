package com.webosmotic.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.webosmotic.entity.Product;
import com.webosmotic.entity.ProductCategory;
import com.webosmotic.exception.AppException;
import com.webosmotic.pojo.ProductDisplay;
import com.webosmotic.pojo.ProductSearchCriteria;
import com.webosmotic.repository.ProductCategoryRepository;
import com.webosmotic.repository.ProductRepository;
import com.webosmotic.specification.ProductSpecification;
import com.webosmotic.util.AppUtil;
@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;

	@Override
	public Product findById(Long id) {
		Optional<Product> productOpt = productRepository.findById(id);
		if (productOpt.isPresent()) {
			return productOpt.get();
		} else {
			throw new AppException("No product found for the given Product_ID: " + id);
		}
	}

	@Override
	public List<ProductDisplay> getRelatedProducts(ProductCategory productCategory, Long id) {
		List<Product> products = productRepository.findByProductCategoryAndIdNotOrderBySellCountDesc(productCategory,
				id);
		return AppUtil.createProductDisplay(products);
	}

	@Override
	public List<ProductDisplay> findTop8ByOrderByDateCreatedDesc() {
		List<Product> products = productRepository.findTop8ByOrderByDateCreatedDesc();
		return AppUtil.createProductDisplay(products);
	}

	@Override
	public List<ProductDisplay> findTop8ByOrderBySellCountDesc() {
		List<Product> products = productRepository.findTop8ByOrderBySellCountDesc();
		return AppUtil.createProductDisplay(products);
	}

	@Override
	public List<ProductDisplay> findCategoryProducts(int offset, int size, String sort, String category) {
		try {
			ProductCategory productCategory = productCategoryRepository.findByName(category);
			if (productCategory != null) {
				Pageable pageRequest = PageRequest.of(Math.floorDiv(offset, size), size, Sort.by(Direction.DESC, sort));
				List<Product> products = productRepository.findByProductCategoryOrderByNameDesc(productCategory,
						pageRequest);
				return AppUtil.createProductDisplay(products);
			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public List<ProductDisplay> fetchShowProducts() {
		try {
			List<Product> products = productRepository.findProductsByShowTagOrderByNameAsc(true);
			return AppUtil.createProductDisplay(products);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<ProductDisplay> SearchProducts(int offset, int size, String sort,
			ProductSearchCriteria searchCriteria) {
		try {
			Pageable pageRequest = PageRequest.of(Math.floorDiv(offset, size), size, Sort.by(Direction.DESC, sort));
			Specification specification = ProductSpecification.findBySearchCriteria(searchCriteria);
			List<Product> products = (List<Product>) productRepository.findAll(specification, pageRequest);
			if (!products.isEmpty() && products.size() > 0) {
				return AppUtil.createProductDisplay(products);
			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			throw new AppException(e.getMessage());

		}
	}
}
