package com.webosmotic.service;

import java.util.List;

import com.webosmotic.entity.Product;
import com.webosmotic.pojo.ProductDisplay;
import com.webosmotic.pojo.ProductSearchCriteria;

public interface ProductService {

	Product findById(Long id);

	List<ProductDisplay> getRelatedProducts(Long id);

	List<ProductDisplay> findCategoryProducts(int offset, int size, String sort, String category);

	List<ProductDisplay> fetchShowProducts();

	List<ProductDisplay> SearchProducts(int offset, int size, String sort, ProductSearchCriteria searchCriteria);

	List<ProductDisplay> findMostSellingProducts();

	List<ProductDisplay> findRecentlyAddedProduct();


}
