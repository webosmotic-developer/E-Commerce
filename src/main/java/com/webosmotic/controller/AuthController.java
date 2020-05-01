package com.webosmotic.controller;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webosmotic.Enum.TokenType;
import com.webosmotic.entity.User;
import com.webosmotic.entity.VerificationToken;
import com.webosmotic.exception.AppException;
import com.webosmotic.exception.BadRequestException;
import com.webosmotic.exception.UserNotFoundException;
import com.webosmotic.pojo.ForgotPasswordRequest;
import com.webosmotic.pojo.LoginRequest;
import com.webosmotic.pojo.LoginResponse;
import com.webosmotic.pojo.SignUpResponse;
import com.webosmotic.pojo.SignupRequest;
import com.webosmotic.repository.UserRepository;
import com.webosmotic.security.jwt.JwtProvider;
import com.webosmotic.service.UserService;
import com.webosmotic.service.VerificationTokenService;
import com.webosmotic.util.AppUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	/* Controller to handle the Authentication and Authorization request */
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtProvider jwtProvider;
	@Autowired
	UserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	VerificationTokenService verificationTokenService;
	@Autowired
	UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	/*
	 * API for Login into the system
	 * 
	 * @RequestBody LoginRequest containing userName and password
	 * 
	 * @Return Login Response with JWT token
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> save(@RequestBody LoginRequest request) {
		try {
			User existingUser = userService.getUserByUserName(request.getUsername());
			if (existingUser == null) {
				existingUser = userService.getUserByEmail(request.getUsername());
			}
			if (existingUser != null && !existingUser.getEnable()) {
				throw new UserNotFoundException("The given user email is not verified");
			} else {
				final String username = existingUser.getUsername();
				final String password = request.getPassword();
				final Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(username, password));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				final String jwt = jwtProvider.generate(authentication);
				return ResponseEntity.ok(new LoginResponse(jwt, existingUser.getEmail(), existingUser.getName(),
						existingUser.getRoles().get(0).getName()));
			}
		} catch (final Exception e) {
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to add the new user to the system
	 * 
	 * @RequestBody User object containing user details
	 * 
	 * @Return SignUp Response on adding the user to database and sending the
	 * verification link to to the registered email with generated token
	 */
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<SignUpResponse> save(@RequestBody SignupRequest signupRequest, HttpServletRequest request) {
		try {
			logger.info("user" + " " + signupRequest);
			// check for Existing user
			if (userService.checkForEmail(signupRequest.getEmail())) {
				throw new BadRequestException("The given email " + signupRequest.getEmail() + " is already in use.");
			}
			// creating the new user
			final User savedUser = userService.createUser(signupRequest);
			logger.info("savedUser" + " " + savedUser);
			// sending the verification email
			final String appUrl = request.getContextPath();
			logger.info("appUrl" + " " + appUrl);
			verificationTokenService.createVerificationToken(savedUser, TokenType.Register, appUrl);
			// creating an signUp response
			final SignUpResponse response = new SignUpResponse();
			response.setMessage("Success!! An verification email is send to your register email.");
			response.setUsername(savedUser.getUsername());
			response.setEmail(savedUser.getEmail());
			response.setSuccess(true);
			logger.info("response" + " " + response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (final Exception e) {
			logger.info("user_error" + " " + e.getMessage());
			throw new AppException(e.getMessage());
		}
	}

	/*
	 * API to verify the newly created user email
	 * 
	 * @RequestParam token
	 * 
	 * @Return the 200 OK response
	 */
	@RequestMapping(value = "/verify-email", method = RequestMethod.GET)
	public ResponseEntity<String> confirmRegistration(HttpServletRequest request, Model model,
			@RequestParam("token") String token) {
		final VerificationToken verificationToken = verificationTokenService.getVerificationToken(token,
				TokenType.Register);
		if (verificationToken == null) {
			throw new UserNotFoundException("No token found for the given token");
		}
		final boolean isTokenExpired = AppUtil.checkForTokenExpiration(verificationToken.getExpiryDate());
		if (!isTokenExpired) {
			final User user = verificationToken.getUser();
			user.setEnable(true);
			user.setVerifiedTime(LocalDateTime.now(ZoneOffset.UTC));
			userService.saveUser(user);
			verificationTokenService.deleteverificationToken(verificationToken);
			return new ResponseEntity<>("The user is verified", HttpStatus.OK);
		} else {
			throw new AppException("The given token is expired");
		}
	}

	/*
	 * API to handle the request for forgot password request
	 * 
	 * @RequestParam User email
	 * 
	 * @Return 200 OK response and send an email with reset-password link
	 */
	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ResponseEntity<?> resetPassword(HttpServletRequest request, @RequestParam("email") String userEmail) {
		final User user = userService.getUserByEmail(userEmail);
		if (user == null) {
			throw new UserNotFoundException("No user found for the given user email: " + userEmail);
		}
		final String appUrl = request.getContextPath();
		verificationTokenService.createVerificationToken(user, TokenType.ResetPassword, appUrl);
		return new ResponseEntity<>("Your password reset request has been sent to your regitered email", HttpStatus.OK);
	}

	/*
	 * API to handle the request for password change
	 * 
	 * @RequestParam token
	 * 
	 * @Requestbody passwordDto with the new password
	 * 
	 * @Return the 200 OK response with new password save to the database
	 */
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public ResponseEntity<?> showChangePasswordPage(@RequestParam("token") String token,
			@RequestBody ForgotPasswordRequest passwordDto) {

		final VerificationToken verificationToken = verificationTokenService.getVerificationToken(token,
				TokenType.ResetPassword);
		if (verificationToken == null) {
			throw new UserNotFoundException("No token found for the given token");
		}
		final User user = verificationToken.getUser();
		final boolean isTokenExpired = AppUtil.checkForTokenExpiration(verificationToken.getExpiryDate());
		if (!isTokenExpired) {
			user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));
			userRepository.save(user);
		} else {
			throw new AppException("The given token is expired");
		}
		return new ResponseEntity<>("The user is verified", HttpStatus.OK);
	}
}
