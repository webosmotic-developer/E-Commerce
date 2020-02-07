package com.webosmotic.service;

import com.webosmotic.entity.Cart;
import com.webosmotic.pojo.CartCheckOutResponse;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.pojo.ProductSummaryUpdateRequest;

public interface CartService {

	Cart fecthUserCart(MyUserDetail user);
	
	Cart addToCart(Long productId, MyUserDetail userDetails);

	Cart updateCartItemInCart(ProductSummaryUpdateRequest request);

	Cart removeProductFromCart(Long id);

	CartCheckOutResponse createOrderForCart(MyUserDetail user, Long cartId);
	

//	Cart removeFromCart(Long id);
//
//	
//
//	
//
//	Cart createOrderForCart(MyUserDetail user, Long cartId);

}
