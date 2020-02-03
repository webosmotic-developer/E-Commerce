package com.webosmotic.service;

import com.webosmotic.entity.Cart;
import com.webosmotic.entity.CartItem;
import com.webosmotic.entity.MyUserDetail;

public interface CartService {

	Cart addToCart(Long productId);

	Cart removeFromCart(Long id);

	Cart fecthUserCart(MyUserDetail user);

	Cart updateCartItemInCart(CartItem item);

}
