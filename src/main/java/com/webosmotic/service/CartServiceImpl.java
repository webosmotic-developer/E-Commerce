package com.webosmotic.service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.webosmotic.entity.Cart;
import com.webosmotic.entity.CartItem;
import com.webosmotic.entity.MyUserDetail;
import com.webosmotic.entity.Product;
import com.webosmotic.exception.NotFoundException;
import com.webosmotic.exception.UserNotFoundException;
import com.webosmotic.repository.CartIemRepository;
import com.webosmotic.repository.CartRepository;
import com.webosmotic.repository.ProductRepository;

@Service
public class CartServiceImpl implements CartService {
	
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartIemRepository cartItemRepository;
	
	
	@Override
	public Cart fecthUserCart(MyUserDetail user) {
		try {
			Optional<Cart> cartOpt = cartRepository.findById(user.getId());
			if (!cartOpt.isPresent()) {
				throw new NotFoundException("No cart found for the given user");
			}

			return cartOpt.get();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Cart addToCart(Long productId) {

		MyUserDetail userDetails = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails == null) {
			throw new UserNotFoundException("No User found");
		}
		Cart existingCart = cartRepository.findbyCreatedBy(userDetails.getId());
		if (existingCart == null) {
			existingCart = new Cart();
		}
		boolean existingItem = false;
		List<CartItem> items = existingCart.getCartItemList();
		for (CartItem i : items) {
			if (i.getProduct().getId().equals(productId)) {
				existingItem = true;
			}
		}
		if (!existingItem) {
			CartItem item = new CartItem();
			Optional<Product> productOpt = productRepository.findById(productId);
			if (!productOpt.isPresent()) {
				throw new NotFoundException("No product found for the givrn product_id : " + productId);
			}
			item.setProduct(productOpt.get());
			item.setAmount(calculateCartItemAmount(item));
			item.setQuantity(1);
			calculateCartTotal(existingCart);
			existingCart.getCartItemList().add(item);
			item.setCart(existingCart);
		}
		cartRepository.save(existingCart);
		return existingCart;
	}
	
	@Override
	public Cart removeFromCart(Long id) {
		try {
			Optional<CartItem> itemOpt = cartItemRepository.findById(id);
			if (!itemOpt.isPresent()) {
				throw new NotFoundException("No item found in the cart to delete for the given id: " + id);
			}
			cartItemRepository.delete(itemOpt.get());
			Optional<Cart> cartOpt = cartRepository.findById(itemOpt.get().getCart().getId());
			if (!cartOpt.isPresent()) {
				throw new NotFoundException("No cart found for the given cartItem: " + id);
			} else {
				if (cartOpt.get().getCartItemList().size() > 0) {
					return cartOpt.get();
				} else {
					cartRepository.delete(cartOpt.get());
					return null;
				}

			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	
	
	
	private float calculateCartItemAmount(CartItem item) {
		float amount = (float) 0.00;
		if (item.getProduct().getUnitPrice() != null) {
			float totalAmount = item.getProduct().getUnitPrice() * item.getQuantity();
			if (item.getProduct().getDiscount() > 0.00) {
				float s = (100 - item.getProduct().getDiscount());
				amount = (totalAmount * s) / 100;
			}
		}
		return roundTwoDecimals(amount);
	}
	
	private void calculateCartTotal(Cart cart) {
		Float totalPrice = 0F;
		Float totalShippingPrice = 0F;

		for (CartItem i : cart.getCartItemList()) {
			System.out.println("amount " + i.getAmount());
			totalPrice = totalPrice + i.getAmount();
			totalShippingPrice = totalShippingPrice + i.getProduct().getShippingPrice();
		}

		cart.setTotalPrice(roundTwoDecimals(totalPrice));
		cart.setTotalCargoPrice(roundTwoDecimals(totalShippingPrice));
	}

	private float roundTwoDecimals(float d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Float.valueOf(twoDForm.format(d));
	}

	@Override
	public Cart updateCartItemInCart(CartItem item) {
	return null;
		
		
	}

	








	

}
