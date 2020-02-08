package com.webosmotic.service;

import com.webosmotic.Enum.AuthProvider;
import com.webosmotic.entity.User;
import com.webosmotic.pojo.SignupRequest;

public interface UserService {
	
	User createUser(SignupRequest signupRequest);
	
	User createSocialUser(User user, AuthProvider provider);
	
	User saveUser(User user);
	
	boolean checkForEmail(String email);
	
	boolean checkForUserName(String userName);
	
	User getUserByUserName(String username);
	
	User getUserByEmail(String email);
}
