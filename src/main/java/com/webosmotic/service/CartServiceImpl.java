package com.webosmotic.service;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webosmotic.Enum.OrderStatus;
import com.webosmotic.entity.Cart;
import com.webosmotic.entity.Order;
import com.webosmotic.entity.Product;
import com.webosmotic.entity.ProductSummary;
import com.webosmotic.entity.User;
import com.webosmotic.exception.AppException;
import com.webosmotic.exception.NotFoundException;
import com.webosmotic.pojo.CartCheckOutResponse;
import com.webosmotic.pojo.MyUserDetail;
import com.webosmotic.pojo.ProductSummaryUpdateRequest;
import com.webosmotic.repository.CartRepository;
import com.webosmotic.repository.OrderRepository;
import com.webosmotic.repository.ProductRepository;
import com.webosmotic.repository.ProductSummaryRepository;
import com.webosmotic.repository.UserRepository;
import com.webosmotic.util.AppUtil;
import com.webosmotic.util.SecurityUtil;

import static com.websmotic.constant.AppConstant.ORDER_CREATED_COMMENT;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductSummaryRepository productSummaryRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	UserRepository userRepository;
	@Override
	public Cart fecthUserCart(MyUserDetail user) {
		try {
			Cart cart = cartRepository.findByCreatedBy(user.getId());
			if (cart == null) {
				throw new NotFoundException("No cart found for the given user");
			}
			return cart;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Cart addToCart(Long productId, MyUserDetail user) {
		try {
			ProductSummary summary = null;
			boolean existingItem = false;
			Cart cart = cartRepository.findByCreatedBy(user.getId());
			if (cart == null) {
				cartRepository.save(new Cart());
				cart = cartRepository.findByCreatedBy(user.getId());
			} else {
				Set<ProductSummary> products = cart.getProducts();
				for (ProductSummary p : products) {
					if (p.getProductId().equals(productId)) {
						p.setQuantity(p.getQuantity() + 1);
						summary = p;
						existingItem = true;
					}
				}
			}
			if (!existingItem) {
				Optional<Product> productOpt = productRepository.findById(productId);
				if (!productOpt.isPresent()) {
					throw new NotFoundException("No product found for the given product_id : " + productId);
				}
				summary = AppUtil.createProductSummmary(productOpt.get());
				summary.setCart(cart);
			}
			summary = calculateProductAmount(summary);
			cart.getProducts().add(summary);
			cart = calculateCartTotal(cart);
			cartRepository.save(cart);
			return cart;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Cart updateCartItemInCart(ProductSummaryUpdateRequest request) {
		if (request.getId() == null) {
			throw new AppException("item id cannot be null");
		}
		Optional<ProductSummary> summaryOpt = productSummaryRepository.findById(request.getId());
		if (!summaryOpt.isPresent()) {
			throw new NotFoundException("No item found for the given Id: " + request.getId());
		}
		ProductSummary summary = summaryOpt.get();
		summary.setQuantity(request.getQuantity());
		calculateProductAmount(summary);
		productSummaryRepository.save(summary);
		Optional<Cart> cartOpt = cartRepository.findById(summary.getCart().getId());
		if (cartOpt.isPresent()) {
			return cartOpt.get();
		} else {
			throw new NotFoundException("No cart found for the given cart item");
		}
	}

	@Override
	public Cart removeProductFromCart(Long id) {
		try {
			ProductSummary summary=null;
			Cart cart = cartRepository.findByCreatedBy(SecurityUtil.getUser().getId());
			if (cart ==null) {
				throw new NotFoundException("No cart found for the given cart item");
			}
			Set<ProductSummary> products = cart.getProducts();
			for (ProductSummary p : products) {
				if (p.getId().equals(id)) {
					summary=p;
					break;
				}
			}
			products.remove(summary);
			cart.setProducts(products);
			cart = calculateCartTotal(cart);
			cartRepository.save(cart);
			return cart;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public CartCheckOutResponse createOrderForCart(MyUserDetail user, Long cartId) {
		try {
			Cart cart = cartRepository.findByCreatedByAndId(user.getId(), cartId);
			if (cart == null) {
				throw new NotFoundException("No Cart found for the given cartId: " + cartId);
			}
			Order newOrder = new Order();
			newOrder.setOrderNumber(AppUtil.randomAlphaNumeric(10));
			newOrder.setOrderTotal(cart.getTotalDiscountedPrice());
			newOrder.setShippingCharge(cart.getTotalCargoPrice());
			newOrder.setPayableAmount(cart.getTotalDiscountedPrice() + cart.getTotalCargoPrice());
			newOrder.setStatus(OrderStatus.Created);
			newOrder.setStatusComment(ORDER_CREATED_COMMENT);
			newOrder.setCoupon(null);
			Order savedOrder = orderRepository.save(newOrder);
			Optional<User> loggedInUserOpt = userRepository.findById(user.getId());
			if (!loggedInUserOpt.isPresent()) {
				throw new NotFoundException("No user found");
			}
			CartCheckOutResponse response = new CartCheckOutResponse();
			response.setName(loggedInUserOpt.get().getName());
			response.setEmail(loggedInUserOpt.get().getEmail());
			response.setAddress(loggedInUserOpt.get().getAdresses());
			response.setOrderId(savedOrder.getId());
			response.setOrderNumber(savedOrder.getOrderNumber());
			return response;
		} catch (Exception e) {
			throw e;
		}
	}

	private ProductSummary calculateProductAmount(ProductSummary p) {
		if (p.getPrice() != null) {
			float totalAmount = (p.getPrice() * p.getQuantity());
			p.setAmount(totalAmount);
			if (p.getDiscount() > 0.00) {
				float s = (100 - p.getDiscount());
				float discountedAmount = (totalAmount * s) / 100;
				p.setDiscountedAmount(discountedAmount);
			} else {
				p.setDiscountedAmount(totalAmount);
			}
		}
		return p;
	}

	private Cart calculateCartTotal(Cart cart) {
		Float totalactualPrice = 0F;
		Float totaldiscountedPrice = 0F;
		Float totalShippingPrice = 0F;

		for (ProductSummary ps : cart.getProducts()) {

			totalactualPrice = totalactualPrice + ps.getAmount();
			totaldiscountedPrice = totaldiscountedPrice + ps.getDiscountedPrice();
			totalShippingPrice = totalShippingPrice + ps.getShippingPrice();
		}
		cart.setTotalActualPrice(roundTwoDecimals(totalactualPrice));
		cart.setTotalDiscountedPrice(roundTwoDecimals(totaldiscountedPrice));
		cart.setTotalCargoPrice(roundTwoDecimals(totalShippingPrice));
		return cart;
	}

	private float roundTwoDecimals(float d) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		return Float.valueOf(twoDForm.format(d));
	}

}
