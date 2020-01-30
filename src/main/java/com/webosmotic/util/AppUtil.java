package com.webosmotic.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.webosmotic.entity.Product;
import com.webosmotic.pojo.ApiResponse;
import com.webosmotic.pojo.ProductDisplay;

/**
 * Util class for Application
 */
@Component
public class AppUtil {

	/*
	 * method to check the given token is expired or not
	 * @Param String token 
	 * @Return boolean
	 */
	public static boolean checkForTokenExpiration(LocalDateTime passToken) {
		if (passToken.isAfter(LocalDateTime.now(ZoneOffset.UTC))) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * method to calculate the Date with the given hours ahead for the token
	 * expiration
	 * @Param int hour 
	 * @Return LocalDateTime
	 */
	public static LocalDateTime calculateExpiryDate(int hours) {
		LocalDateTime currentDateTime = LocalDateTime.now(ZoneOffset.UTC);
		LocalDateTime nextTime = currentDateTime.plusHours(hours);
		return nextTime;
	}
	
	public static List<ProductDisplay> createProductDisplay(List<Product> products) {
		List<ProductDisplay> productDisplays = new ArrayList<>();
		products.forEach(p -> {
			ProductDisplay display = new ProductDisplay();
			display.setCategory(p.getProductCategory());
			display.setImages(p.getImage());
			display.setName(p.getName());
			display.setPrice(p.getPrice());
			display.setSellCount(p.getSellCount());
			display.setShippingPrice(p.getShippingPrice());
			productDisplays.add(display);
		});
		return productDisplays;
	}
}
