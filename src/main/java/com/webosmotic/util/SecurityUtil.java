package com.webosmotic.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.webosmotic.exception.UserNotFoundException;
import com.webosmotic.pojo.MyUserDetail;

public class SecurityUtil {
	
	public static MyUserDetail getUser() {
		MyUserDetail userDetails = (MyUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (userDetails == null) {
			throw new UserNotFoundException("No User found");
		}
		return userDetails;
	}

}
