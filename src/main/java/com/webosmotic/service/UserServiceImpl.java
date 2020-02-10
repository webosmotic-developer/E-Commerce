package com.webosmotic.service;

import java.util.Collections;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.webosmotic.Enum.AuthProvider;
import com.webosmotic.Enum.RoleType;
import com.webosmotic.controller.AuthController;
import com.webosmotic.entity.Role;
import com.webosmotic.entity.User;
import com.webosmotic.pojo.SignupRequest;
import com.webosmotic.repository.RoleRepository;
import com.webosmotic.repository.UserRepository;

/**
 * Service class for User
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

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
	public User createUser(SignupRequest signupRequest) {
		try {
			Role existingRole = roleRepository.findByName(RoleType.Role_Buyer);
			if(existingRole == null) {
				existingRole = new Role(RoleType.Role_Buyer, "Buyer");
			}
			User newUser = new User();
			newUser.setName(signupRequest.getName());
			newUser.setUsername(signupRequest.getUsername());
			newUser.setEmail(signupRequest.getEmail());
			newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
			newUser.getRoles().add(existingRole);
			newUser.setProvider(AuthProvider.Local);
			newUser.setEnable(false);
			logger.info("user" + " " + newUser);
			return saveUser(newUser);
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
			Role existingRole = roleRepository.findByName(RoleType.Role_Buyer);
			if(existingRole == null) {
				existingRole = new Role(RoleType.Role_Buyer, "Buyer");
			}
			user.setProvider(provider);
			Role role = new Role(RoleType.Role_Buyer, "Buyer");
			user.getRoles().add(role);
			user.getRoles().add(existingRole);
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
