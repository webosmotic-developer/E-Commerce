package com.webosmotic.util;

import java.util.Base64;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.SerializationUtils;

/**
 * Util class for Cookie
 */
public class CookieUtils {

	/*
	 * method to get the cookie value from the given request
	 * 
	 * @Param Http
	 * 
	 * @Return LocalDateTime
	 */
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
		final Cookie[] cookies = request.getCookies();

		if (cookies != null && cookies.length > 0) {
			for (final Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return Optional.of(cookie);
				}
			}
		}

		return Optional.empty();
	}

	/*
	 * method to add the new cookie value from the given request
	 * 
	 * @Param response, String name, int age
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		final Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/*
	 * method to delete the cookie value from the given request
	 * 
	 * @Param request, response, String name
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		final Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (final Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public static String serialize(Object object) {
		return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(object));
	}

	public static <T> T deserialize(Cookie cookie, Class<T> cls) {
		return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
	}
}