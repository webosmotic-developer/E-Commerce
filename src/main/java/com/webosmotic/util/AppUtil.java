package com.webosmotic.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Component;

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
}
