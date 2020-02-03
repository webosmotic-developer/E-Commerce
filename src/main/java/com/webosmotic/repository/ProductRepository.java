package com.webosmotic.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.webosmotic.entity.Product;
import com.webosmotic.entity.ProductCategory;
import com.webosmotic.specification.ProductSpecification;

public interface ProductRepository extends JpaRepository<Product, Long> , JpaSpecificationExecutor<ProductSpecification>{

//	List<Product> findByProductCategoryAndProduct_idNotOrderBySellCountDesc(ProductCategory productCategory, Long id);

//	List<Product> findTop8ByOrderByCreationDateDesc();
//
//	List<Product> findTop8ByOrderBySellCountDesc();
//
//	List<Product> findByProductCategoryOrderByNameDesc (ProductCategory productCategory, Pageable pageRequest);
//
//	List<Product> findProductsByShowTagOrderByNameAsc(boolean b);
}
