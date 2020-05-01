package com.webosmotic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/** Service Class for sending the email */
@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	public JavaMailSender emailSender;

	/*
	 * Method to send an email to the new signUp user for the verification email.
	 * 
	 * @Param String to - userEmail
	 * 
	 * @Param String subject - Email subject
	 * 
	 * @Param String text - email body
	 */
	@Override
	public void sendSimpleMessageForRegisteration(String to, String subject, String text) {
		sendEmail(to, "test@example.com", subject, text);
	}

	/*
	 * Method to send an email to the new signUp user for the verification email.
	 * 
	 * @Param String to - userEmail
	 * 
	 * @Param String subject - Email subject
	 * 
	 * @Param String text - email body
	 */
	@Override
	public void sendSimpleMessageForForgotPassword(String to, String subject, String text) {
		sendEmail(to, "test@example.com", subject, text);

	}

	private void sendEmail(String to, String from, String subject, String text) {
		final SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setFrom(from);
		message.setSubject(subject);
		message.setText(text);
		emailSender.send(message);
	}
}
