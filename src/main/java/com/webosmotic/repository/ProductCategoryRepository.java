package com.webosmotic.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webosmotic.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long>{

	ProductCategory findByParentCategory(String category);

	ProductCategory findByParentCategoryAndSubCategory(String parentCategory, String subCategory);

	//ProductCategory findByName(String category);
	
	

}
