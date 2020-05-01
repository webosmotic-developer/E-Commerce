package com.webosmotic.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.webosmotic.Enum.TokenType;
import com.webosmotic.controller.AuthController;
import com.webosmotic.entity.User;
import com.webosmotic.entity.VerificationToken;
import com.webosmotic.exception.AppException;
import com.webosmotic.repository.VerificationtokenRepository;
import com.webosmotic.util.AppUtil;

/**
 * Service class for VerificationToken
 */
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Autowired
	VerificationtokenRepository verificationRepository;
	@Autowired
	EmailService emailService;
	@Autowired
	Environment env;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	/*
	 * Method to create the new VerificationToken object and save into the database.
	 * 
	 * @Param User user, TokenType type and String appurl
	 * 
	 * @Return saved VerificationToken obj.
	 */
	@Override
	public VerificationToken createVerificationToken(User user, TokenType type, String appUrl) {
		try {
			final VerificationToken token = new VerificationToken();
			token.setToken(UUID.randomUUID().toString());
			logger.info("token" + " " + token.getToken());
			token.setUser(user);
			token.setExpiryDate(AppUtil.calculateExpiryDate(24));
			logger.info("expiryDate" + " " + token.getExpiryDate());
			token.setTokenType(TokenType.Register);
			if (type.equals(TokenType.Register)) {
				sendEmailForRegistration(token.getToken(), user, appUrl);
			} else {
				sendEmailForForgotPassword(token.getToken(), user, appUrl);
			}
			return verificationRepository.save(token);
		} catch (final Exception e) {
			throw e;
		}
	}

	/*
	 * Method to fetch the verification token based on token and token type
	 * 
	 * @Param String token, TokenType type
	 * 
	 * @Return VerificationToken object.
	 */
	@Override
	public VerificationToken getVerificationToken(String token, TokenType type) {
		return verificationRepository.getByToken(token, type);

	}

	/*
	 * Method to send the email to the user email for email verification during the
	 * user signUp process
	 * 
	 * @Param String token, User user, String appurl.
	 */
	private void sendEmailForRegistration(String token, User user, String appUrl) {
		final String recipientAddress = user.getEmail();
		final String subject = "Registration Confirmation";
		final String confirmationUrl = "/auth/verify-email?token=" + token;
		final String message = "Congratulation !! Your account has been craeted please click on the link below to verify your email";
		final String text = message + "\n" + env.getProperty("app.baseurl") + confirmationUrl;
		emailService.sendSimpleMessageForRegisteration(recipientAddress, subject, text);
	}

	/*
	 * Method to send the email to the user email regarding the forgot password
	 * during the change password process
	 * 
	 * @Param String token, User user, String appurl.
	 */
	private void sendEmailForForgotPassword(String token, User user, String appUrl) {
		final String recipientAddress = user.getEmail();
		final String subject = "Reset Password";
		final String url = appUrl + "/auth/changePassword?id=" + user.getId() + "&token=" + token;
		final String message = "Congratulation !! Your aqccount has been craeted please click on the link below to verify your email";
		final String text = message + " rn " + env.getProperty("app.baseurl") + url;
		emailService.sendSimpleMessageForForgotPassword(recipientAddress, subject, text);
	}

	/*
	 * Method to Delete the Verification token
	 * 
	 * @Param String token
	 */
	@Override
	public void deleteverificationToken(VerificationToken token) {
		final Optional<VerificationToken> existingToken = verificationRepository.findById(token.getId());
		if (existingToken.isPresent()) {
			verificationRepository.delete(existingToken.get());
		} else {
			throw new AppException("No Verification token found for the given token");
		}

	}

}
