package com.webosmotic.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.webosmotic.entity.User;
import com.webosmotic.exception.UserNotFoundException;
import com.webosmotic.pojo.MyUserDetail;

/**
 * Custom class to override the default UserdetailsService to fetch the User
 * details from the underlying database using the JPA .
 */
@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	UserService userService;

	/*
	 * Method to get the UserDetails during the User login process.
	 * @Param String userName
	 * @Return Spring Security UserDetails class.
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = userService.getUserByUserName(username);
		if (user != null) {
			return MyUserDetail.create(user);
		} else {
			throw new UserNotFoundException("No user found for the given userName");
		}
	}
}
