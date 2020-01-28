package com.webosmotic.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.backend.model.ProductDisplay;
import com.commerce.backend.service.ProductCategoryService;
import com.commerce.backend.service.ProductDisplayService;
import com.nimbusds.oauth2.sdk.Response;
import com.webosmotic.entity.Product;
import com.webosmotic.entity.ProductCategory;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.pojo.ErrorResponse;
import com.webosmotic.service.ProductService;

@RestController("/product")
public class ProductController {

    @Autowired
    ProductService productService;

	/*
	 * @RequestMapping(value = "/product/category", method = RequestMethod.GET)
	 * public ResponseEntity getProductsByCategory (@RequestParam("page") Integer
	 * page,
	 * 
	 * @RequestParam("size") Integer size,
	 * 
	 * @RequestParam(value = "sort", required = false) String sort,
	 * 
	 * @RequestParam(value = "category", required = false) String category) {
	 * 
	 * if (page == null || size == null) { throw new
	 * IllegalArgumentException("Page and size parameters are required"); }
	 * PageRequest pageRequest; if (sort != null && !isBlank(sort)) { Sort
	 * sortRequest = getSort(sort); if (sortRequest == null) { throw new
	 * IllegalArgumentException("Invalid sort parameter"); } pageRequest =
	 * PageRequest.of(page, size, sortRequest); } else { pageRequest =
	 * PageRequest.of(page, size); }
	 * 
	 * if (category != null && !isBlank(category)) { ProductCategory productCategory
	 * = productCategoryService.findByName(category); if (productCategory == null) {
	 * throw new IllegalArgumentException("Invalid category parameter"); } List
	 * returnList = productDisplayService.findAllByProductCategory(pageRequest,
	 * productCategory); return new ResponseEntity<List>(returnList, HttpStatus.OK);
	 * }
	 * 
	 * List returnList = productDisplayService.findAll(pageRequest); return new
	 * ResponseEntity<>(returnList, HttpStatus.OK); }
	 */

    /* API to fetch the product detail based on productID
     * @Param Long Id
     * @return Product
     * */
	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET, params = "id")
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

    @RequestMapping(value = "/product/related", method = RequestMethod.GET, params = "id")
    public ResponseEntity getByRelated(@RequestParam("id") Long id) {
        ProductDisplay productDisplay = productDisplayService.findById(id);
        if (productDisplay == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List>(productDisplayService.getRelatedProducts(productDisplay.getProductCategory(), id), HttpStatus.OK);
    }

    @RequestMapping(value = "/product/recent", method = RequestMethod.GET)
    public ResponseEntity getByNewlyAdded() {
        List returnList = productDisplayService.findTop8ByOrderByDateCreatedDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/mostselling", method = RequestMethod.GET)
    public ResponseEntity getByMostSelling() {
        List returnList = productDisplayService.findTop8ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }


    //TODO rebuild the logic
    @RequestMapping(value = "/product/interested", method = RequestMethod.GET)
    public ResponseEntity getByInterested() {
        List returnList = productDisplayService.findTop8ByOrderBySellCountDesc();
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }


    @RequestMapping(value = "/product/search", method = RequestMethod.GET, params = {"page", "size", "keyword"})
    public ResponseEntity searchProduct(@RequestParam("page") Integer page,
                                        @RequestParam("size") Integer size,
                                        @RequestParam("keyword") String keyword) {
        List returnList = productDisplayService.searchProducts(keyword, page, size);
        return new ResponseEntity<List>(returnList, HttpStatus.OK);
    }

    private boolean isBlank(String param) {
        return param.isEmpty() || param.trim().equals("");
    }


    //A better way to do this is storing sorting options in the database
    //and sending those options to the client. Later then the client
    //sends the parameter based upon that.
    private Sort getSort(String sort) {
        switch (sort) {
            case "lowest":
                return Sort.by(Sort.Direction.ASC, "price");
            case "highest":
                return Sort.by(Sort.Direction.DESC, "price");
            case "name":
                return Sort.by(Sort.Direction.ASC, "name");
            default:
                return null;
        }
    }


}
