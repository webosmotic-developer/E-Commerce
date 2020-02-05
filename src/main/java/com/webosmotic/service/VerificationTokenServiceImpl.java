package com.webosmotic.service;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	/*
	 * Method to create the new VerificationToken object and save into the
	 * database.
	 * @Param User user, TokenType type and String appurl
	 * @Return saved VerificationToken obj.
	 */
	@Override
	public VerificationToken createVerificationToken(User user, TokenType type, String appUrl) {
		try {
			VerificationToken token = new VerificationToken();
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
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * Method to fetch the verification token based on token and token type 
	 * @Param String token, TokenType type
	 * @Return VerificationToken object.
	 */
	@Override
	public VerificationToken getVerificationToken(String token, TokenType type) {
		return verificationRepository.getByToken(token, type);

	}

	/*
	 * Method to send the email to the user email for email verification during the
	 * user signUp process
	 * @Param String token, User user, String appurl.
	 */
	private void sendEmailForRegistration(String token, User user, String appUrl) {
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = appUrl + "/verify-email?id=" + user.getId() + "&token=" + token;
		String message = "Congratulation !! Your aqccount has been craeted please click on the link below to verify your email";
		String text = message + " rn" + "http://localhost:8080" + confirmationUrl;
		emailService.sendSimpleMessageForRegisteration(recipientAddress, subject, text);
	}

	/*
	 * Method to send the email to the user email regarding the forgot password
	 * during the change password process
	 * @Param String token, User user, String appurl.
	 */
	private void sendEmailForForgotPassword(String token, User user, String appUrl) {
		String recipientAddress = user.getEmail();
		String subject = "Reset Password";
		String url = appUrl + "/changePassword?id=" + user.getId() + "&token=" + token;
		String message = "Congratulation !! Your aqccount has been craeted please click on the link below to verify your email";
		String text = message + " rn" + "http://localhost:8080" + url;
		emailService.sendSimpleMessageForForgotPassword(recipientAddress, subject, text);
	}

	/*
	 * Method to Delete the Verification token
	 * @Param String token
	 */
	@Override
	public void deleteverificationToken(VerificationToken token) {
		Optional<VerificationToken> existingToken = verificationRepository.findById(token.getId());
		if (existingToken.isPresent()) {
			verificationRepository.delete(token);
		} else {
			throw new AppException("No Verification token found for the given token");
		}

	}

}
