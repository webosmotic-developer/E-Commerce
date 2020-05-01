package com.webosmotic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.webosmotic.entity.Product;
import com.webosmotic.entity.ProductCategory;
import com.webosmotic.specification.ProductSpecification;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<Product>{

	List<Product> findByShowTagOrderByNameAsc(boolean b);

	List<Product> findByProductCategoryAndIdNotOrderBySellCountDesc(ProductCategory category, Long id);

	List<Product> findTop8ByOrderByCreatedAtDesc();

	List<Product> findTop8ByOrderBySellCountDesc();
	
	List<Product> findByProductCategoryOrderByNameDesc(ProductCategory productCategory, Pageable pageRequest);
	
}
