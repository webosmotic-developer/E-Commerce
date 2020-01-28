package com.webosmotic.service;

import com.webosmotic.entity.User;
import com.webosmotic.social.modal.AuthProvider;

public interface UserService {
	
	User createUser(User user);
	
	User createSocialUser(User user, AuthProvider provider);
	
	User saveUser(User user);
	
	boolean checkForEmail(String email);
	
	boolean checkForUserName(String userName);
	
	User getUserByUserName(String username);
	
	User getUserByEmail(String email);
}
