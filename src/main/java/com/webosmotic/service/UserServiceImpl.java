package com.webosmotic.service;

import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webosmotic.entity.Role;
import com.webosmotic.entity.RoleType;
import com.webosmotic.entity.User;
import com.webosmotic.repository.RoleRepository;
import com.webosmotic.repository.UserRepository;
import com.webosmotic.social.modal.AuthProvider;

/**
 * Service class for User
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	RoleRepository roleRepository;
	
	/*
	 * Method to check the if the email is already exists
	 * @Param String email
	 * @Return boolean
	 */
	@Override
	public boolean checkForEmail(String email) {
		User userEmail = userRepository.findByEmail(email);
		if (userEmail != null && userEmail.getEmail() != null && userEmail.getEmail().equalsIgnoreCase(email)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Method to check the if the userName is already exists
	 * @Param String userName
	 * @Return boolean
	 */
	@Override
	public boolean checkForUserName(String userName) {
		User username = userRepository.findByUsername(userName);
		if (username != null && username.getUsername() != null && username.getUsername().equalsIgnoreCase(userName)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Method to find the user object for the given userName
	 * @Param String userName
	 * @Return User object
	 */
	@Override
	public User getUserByUserName(String username) {
		return userRepository.findByUsername(username);
	}

	/*
	 * Method to find the user object for the given email
	 * @Param String email
	 * @Return User object
	 */
	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/*
	 * Method to create the new user object and saved into database
	 * @Param User signupRequest
	 * @Return User savedUser
	 */
	@Override
	@Transactional
	public User createUser(User signupRequest) {
		try {
			Role role = new Role(RoleType.Role_Buyer, "Buyer");
			signupRequest.getRoles().add(role);
			signupRequest.setProvider(AuthProvider.local);
			signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			signupRequest.setEnable(false);
			return saveUser(signupRequest);
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Method to create the new user object for the social OAuth2 login.
	 * @Param User user , AuthProvider provider
	 * @Return User savedUser
	 */
	@Override
	@Transactional
	public User createSocialUser(User user, AuthProvider provider) {
		try {
			user.setProvider(provider);
			Role role = new Role(RoleType.Role_Buyer, "Buyer");
			user.getRoles().add(role);
			user.setRoles(Collections.singleton(role));
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setEnable(true);
			return saveUser(user);
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Method to save the user into database
	 * @Param User user
	 * @Return User savedUser
	 */
	@Override
	public User saveUser(User user) {
		try {
			return userRepository.save(user);
		} catch (Exception e) {
			throw e;
		}
	}
}
