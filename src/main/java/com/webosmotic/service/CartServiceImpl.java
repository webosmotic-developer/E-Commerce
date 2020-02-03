//package com.webosmotic.service;
//
//import java.text.DecimalFormat;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//import com.webosmotic.entity.Cart;
//import com.webosmotic.entity.CartItem;
//import com.webosmotic.entity.MyUserDetail;
//import com.webosmotic.entity.Order;
//import com.webosmotic.entity.OrderDetails;
//import com.webosmotic.entity.OrderStatus;
//import com.webosmotic.entity.Product;
//import com.webosmotic.exception.AppException;
//import com.webosmotic.exception.NotFoundException;
//import com.webosmotic.exception.UserNotFoundException;
//import com.webosmotic.repository.CartIemRepository;
//import com.webosmotic.repository.CartRepository;
//import com.webosmotic.repository.ProductRepository;
//import com.webosmotic.util.AppUtil;
//
//@Service
//public class CartServiceImpl implements CartService {
//	
//	@Autowired
//	CartRepository cartRepository;
//	@Autowired
//	ProductRepository productRepository;
//	@Autowired
//	CartIemRepository cartItemRepository;
//	
//	
//	@Override
//	public Cart fecthUserCart(MyUserDetail user) {
//		try {
//			Optional<Cart> cartOpt = cartRepository.findById(user.getId());
//			if (!cartOpt.isPresent()) {
//				throw new NotFoundException("No cart found for the given user");
//			}
//
//			return cartOpt.get();
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//
//	@Override
//	public Cart addToCart(Long productId) {
//
//		MyUserDetail userDetails = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		if (userDetails == null) {
//			throw new UserNotFoundException("No User found");
//		}
//		Cart existingCart = cartRepository.findbyCreatedBy(userDetails.getId());
//		if (existingCart == null) {
//			existingCart = new Cart();
//		}
//		boolean existingItem = false;
//		List<CartItem> items = existingCart.getCartItemList();
//		for (CartItem i : items) {
//			if (i.getProduct().getId().equals(productId)) {
//				existingItem = true;
//			}
//		}
//		if (!existingItem) {
//			CartItem item = new CartItem();
//			Optional<Product> productOpt = productRepository.findById(productId);
//			if (!productOpt.isPresent()) {
//				throw new NotFoundException("No product found for the givrn product_id : " + productId);
//			}
//			item.setProduct(productOpt.get());
//			item.setQuantity(1);
//			calculateCartTotal(existingCart);
//			item.setDeliveredBy(LocalDate.now().plusDays(2));
//			item.setSeller(item.getProduct().getSeller());
//			calculateCartItemAmount(item);
//			item.setCart(existingCart);
//		}
//		cartRepository.save(existingCart);
//		return existingCart;
//	}
//	
//	@Override
//	public Cart removeFromCart(Long id) {
//		try {
//			Optional<CartItem> itemOpt = cartItemRepository.findById(id);
//			if (!itemOpt.isPresent()) {
//				throw new NotFoundException("No item found in the cart to delete for the given id: " + id);
//			}
//			cartItemRepository.delete(itemOpt.get());
//			Optional<Cart> cartOpt = cartRepository.findById(itemOpt.get().getCart().getId());
//			if (!cartOpt.isPresent()) {
//				throw new NotFoundException("No cart found for the given cartItem: " + id);
//			} else {
//				if (cartOpt.get().getCartItemList().size() > 0) {
//					return cartOpt.get();
//				} else {
//					cartRepository.delete(cartOpt.get());
//					return null;
//				}
//			}
//		} catch (Exception e) {
//			throw e;
//		}
//	}
//	
//	@Override
//	public Cart updateCartItemInCart(CartItem item) {
//		if (item.getId() == null) {
//			throw new AppException("item id cannot be null");
//		}
//		Optional<CartItem> cartItemOpt = cartItemRepository.findById(item.getId());
//		if (!cartItemOpt.isPresent()) {
//			throw new NotFoundException("No item found for the given item Id: " + item.getId());
//		}
//		CartItem newItems = cartItemOpt.get();
//		newItems.setQuantity(item.getQuantity());
//		calculateCartItemAmount(newItems);
//		cartItemRepository.save(newItems);
//		Optional<Cart> cartOpt = cartRepository.findById(newItems.getCart().getId());
//		if (cartOpt.isPresent()) {
//			return cartOpt.get();
//		} else {
//			throw new NotFoundException("No cart found for the given cart item");
//		}
//	}
//
//	private void calculateCartItemAmount(CartItem item) {
//		if (item.getProduct().getUnitPrice() != null) {
//			float totalAmount = (item.getProduct().getUnitPrice() * item.getQuantity());
//			item.setAmount(totalAmount);
//			if (item.getProduct().getDiscount() > 0.00) {
//				float s = (100 - item.getProduct().getDiscount());
//				float discountedAmount = (totalAmount * s) / 100;
//				item.setDiscount(item.getProduct().getDiscount());
//				item.setDiscountedAmount(discountedAmount);
//			}else {
//				item.setDiscountedAmount(totalAmount);
//			}
//		}
//	}
//
//	private void calculateCartTotal(Cart cart) {
//		Float totalPrice = 0F;
//		Float totalShippingPrice = 0F;
//
//		for (CartItem i : cart.getCartItemList()) {
//			System.out.println("amount " + i.getAmount());
//			totalPrice = totalPrice + i.getAmount();
//			totalShippingPrice = totalShippingPrice + i.getProduct().getShippingPrice();
//		}
//
//		cart.setTotalPrice(roundTwoDecimals(totalPrice));
//		cart.setTotalCargoPrice(roundTwoDecimals(totalShippingPrice));
//	}
//
//	private float roundTwoDecimals(float d) {
//		DecimalFormat twoDForm = new DecimalFormat("#.##");
//		return Float.valueOf(twoDForm.format(d));
//	}
//
//	@Override
//	public Cart createOrderForCart(MyUserDetail user, Long cartId) {
//		Optional<Cart> cartOpt = cartRepository.findById(cartId);
//		if(!cartOpt.isPresent()) {
//			throw new NotFoundException("No Cart found for the given cartId: " + cartId);
//		}
//		Cart cart = cartOpt.get();
//		
//		Order newOrder = new Order();
//		newOrder.setOrderNumber(AppUtil.randomAlphaNumeric(10));
//		newOrder.setOrderTotal(cart.getTotalPrice());
//		newOrder.setShippingCharge(cart.getTotalCargoPrice());
//		newOrder.setPayableAmount(cart.getTotalPrice() + cart.getTotalCargoPrice());
//		newOrder.setStatus(OrderStatus.ordered);
//		newOrder.setStatusComment("Your Order has been placed");
//		
//		cart.getCartItemList().forEach(i -> {
//			newOrder.getOrderDetailsList().add(createOrderDetail(i));
//		});
//		return null;
//	}
//	
//	private OrderDetails createOrderDetail(CartItem item) {
//		
//		OrderDetails od = new OrderDetails();
//		
//		Product product = item.getProduct();
//		
//		od.setName(product.getName());
//		
//		return null;
//	}
//	
//
//}
