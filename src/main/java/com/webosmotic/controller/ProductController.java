package com.webosmotic.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webosmotic.entity.Product;
import com.webosmotic.entity.ProductCategory;
import com.webosmotic.entity.ProductReview;
import com.webosmotic.exception.AppException;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.pojo.ErrorResponse;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.pojo.ProductDisplay;
import com.webosmotic.pojo.ProductSearchCriteria;
import com.webosmotic.service.ProductService;
import com.webosmotic.util.SecurityUtil;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;
    
    /* 
     * API to fetch the random products
     * @return Product list
     */	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<List<ProductDisplay>>> getShowProducts() {
		ApiResponse<List<ProductDisplay>> response = new ApiResponse<>();
		try {
			List<ProductDisplay> products = productService.fetchShowProducts();
			response.setSuccess(true);
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	
	@Secured({"ROLE_SELLER","ROLE_ADMIN"})
	@RequestMapping(value = "/category/save", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<List<ProductCategory>>> saveProductCategory(
			@RequestBody List<ProductCategory> category) {
		ApiResponse<List<ProductCategory>> response = new ApiResponse<>();
		try {
			List<ProductCategory> savedProducts = productService.saveProductCategory(category);
			response.setSuccess(true);
			response.setData(savedProducts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

    /* 
     * API to fetch the products for the given product category
     * @QueryParam Integer offset , Integer size, String sort, String category
     * @return Product list
     */	
	@RequestMapping(value = "/category/{category}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<List<ProductDisplay>>> getProductsByCategory(
			@RequestParam(name = "offset", defaultValue = "0") int offset,
			@RequestParam(name = "size", defaultValue = "25") int size,
			@RequestParam(name = "sort", defaultValue = "name") String sort,
			@PathVariable("category") String category) {
		ApiResponse<List<ProductDisplay>> response = new ApiResponse<>();
		try {
			List<ProductDisplay> products = productService.findCategoryProducts(offset, size, sort, category);
			response.setSuccess(true);
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
	/* 
     * API to fetch the products based on the search criteria
     * @QueryParam Integer offset , Integer size, String sort, ProductSearch searchCriteria
     * @return Product list
     */	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<List<ProductDisplay>>> getProductsBykeyword(
			@RequestParam(name = "offset", defaultValue = "0") int offset,
			@RequestParam(name = "size", defaultValue = "25") int size,
			@RequestParam(name = "sort", defaultValue = "name") String sort,
			@RequestBody ProductSearchCriteria searchCriteria) {
		ApiResponse<List<ProductDisplay>> response = new ApiResponse<>();
		try {
			List<ProductDisplay> products = productService.SearchProducts(offset, size,sort,searchCriteria);
			response.setSuccess(true);
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

    /* 
     * API to fetch the full product detail based on productID
     * @Param Long Id
     * @return Product
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<Product>> getFullProductDetailsById(@PathVariable("id") Long id) {
		ApiResponse<Product> response = new ApiResponse<>();
		try {
			Product product = productService.findById(id);
			response.setSuccess(true);
			response.setData(product);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setSuccess(false);
			response.setError(new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null,
					e.getMessage()));
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*
	 * API to fetch the related products detail based on productID
	 * @Param Long Id
	 * @return List<Products>
	 */
	@RequestMapping(value = "/related", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<List<ProductDisplay>>> getByRelatedProducts(@RequestParam("id") Long id) {
		ApiResponse<List<ProductDisplay>> response = new ApiResponse<>();
		try {
			List<ProductDisplay> products = productService.getRelatedProducts(id);
			response.setSuccess(true);
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to fetch the recently added products
	 * @return List<Products>
	 */
	@RequestMapping(value = "/recent", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<List<ProductDisplay>>> getByNewlyAdded() {
		ApiResponse<List<ProductDisplay>> response = new ApiResponse<>();
		try {
			List<ProductDisplay> products = productService.findRecentlyAddedProduct();
			response.setSuccess(true);
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to fetch the most selling products
	 * @return List<Products>
	 */
	@RequestMapping(value = "/mostselling", method = RequestMethod.GET)
	public ResponseEntity<ApiResponse<List<ProductDisplay>>> getByMostSelling() {
		ApiResponse<List<ProductDisplay>> response = new ApiResponse<>();
		try {
			List<ProductDisplay> products = productService.findMostSellingProducts();
			response.setSuccess(true);
			response.setData(products);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	@Secured({"ROLE_SELLER","ROLE_ADMIN"})
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<List<Product>>> saveNewProduct(
			@RequestBody List<Product> products) {
		ApiResponse<List<Product>> response = new ApiResponse<>();
		try {
			List<Product> savedProducts = productService.saveNewProduct(products);
			response.setSuccess(true);
			response.setData(savedProducts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
	
		@Secured({"ROLE_BUYER","ROLE_ADMIN"})
		@RequestMapping(value = "/review/{pid}/save", method = RequestMethod.POST)
	public ResponseEntity<ApiResponse<ProductReview>> saveProductReview(
			@RequestBody ProductReview review,
			@PathVariable ("pid") Long pid) {
		ApiResponse<ProductReview> response = new ApiResponse<>();
		try {
			MyUserDetail user = SecurityUtil.getUser();
			ProductReview savedProducts = productService.saveProductReview(review, pid, user);
			response.setSuccess(true);
			response.setData(savedProducts);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			throw new AppException(e.getMessage());
		}
	}
		
	
}
