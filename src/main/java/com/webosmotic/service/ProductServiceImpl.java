package com.webosmotic.service;

import java.util.ArrayList;
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
import com.webosmotic.entity.ProductReview;
import com.webosmotic.exception.AppException;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.pojo.ProductDisplay;
import com.webosmotic.pojo.ProductSearchCriteria;
import com.webosmotic.repository.ProductCategoryRepository;
import com.webosmotic.repository.ProductRepository;
import com.webosmotic.repository.ProductReviewRepository;
import com.webosmotic.specification.ProductSpecification;
import com.webosmotic.util.AppUtil;

import javassist.NotFoundException;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductCategoryRepository productCategoryRepository;
	@Autowired
	ProductReviewRepository productReviewRepository;

	@Override
	public List<ProductDisplay> fetchShowProducts() {
		try {
			List<Product> products = productRepository.findByShowTagOrderByNameAsc(true);
			return AppUtil.createProductDisplay(products);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public List<ProductDisplay> findCategoryProducts(int offset, int size, String sort, String category) {
		try {
			ProductCategory productCategory = productCategoryRepository.findByParentCategory(category);
			if (productCategory != null) {
				Pageable pageRequest = PageRequest.of(Math.floorDiv(offset, size), size, Sort.by(Direction.DESC, sort));
				List<Product> products = productRepository.findByProductCategoryOrderByNameDesc(productCategory,
						pageRequest);
				return AppUtil.createProductDisplay(products);
			} else {
				return Collections.emptyList();
			}
		} catch (Exception e) {
			throw e;
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
	public List<ProductDisplay> getRelatedProducts(Long id) {
		try {
			Optional<Product> productOpt = productRepository.findById(id);
			if (!productOpt.isPresent()) {
				throw new NotFoundException("No product found for the given productId: " + id);
			}
			ProductCategory category = productOpt.get().getProductCategory();
			if (category == null) {
				throw new NotFoundException("No product category found for the given productId: " + id);
			}
			List<Product> products = productRepository.findByProductCategoryAndIdNotOrderBySellCountDesc(category, id);
			return AppUtil.createProductDisplay(products);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	public List<ProductDisplay> findRecentlyAddedProduct() {
		try {
			List<Product> products = productRepository.findTop8ByOrderByCreatedAtDesc();
			return AppUtil.createProductDisplay(products);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ProductDisplay> findMostSellingProducts() {
		try {
			List<Product> products = productRepository.findTop8ByOrderBySellCountDesc();
			return AppUtil.createProductDisplay(products);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Product> saveNewProduct(List<Product> products) {
		try {
			
			
			List<Product> savedProducts = new ArrayList<Product>();
			products.forEach(p -> {

				ProductCategory pcategory = p.getProductCategory();
				
				ProductCategory existingCategory = productCategoryRepository.findByParentCategoryAndSubCategory(pcategory.getParentCategory(), pcategory.getSubCategory());
				if(existingCategory != null) {
					p.setProductCategory(existingCategory);
				}
				savedProducts.add(productRepository.save(p));
			});
			return savedProducts;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<ProductCategory> saveProductCategory(List<ProductCategory> category) {
		try {
			List<ProductCategory> savedProducts = new ArrayList<>();
			category.forEach(p -> {
				savedProducts.add(productCategoryRepository.save(p));
			});
			return savedProducts;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ProductReview saveProductReview(ProductReview review, Long pid, MyUserDetail user) {
		try {
			Optional<Product> productOpt = productRepository.findById(pid);
			if (!productOpt.isPresent()) {
				throw new NotFoundException("No product foud for the given productId: " + pid);
			}

			review.setProduct(productOpt.get());
			review.setReviewerName(user.getUsername());
			return productReviewRepository.save(review);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}

	}
}
