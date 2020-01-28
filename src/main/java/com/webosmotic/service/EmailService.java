package com.webosmotic.service;

public interface EmailService {
	
	void sendSimpleMessageForRegisteration(String to, String subject, String text);
	
	void sendSimpleMessageForForgotPassword(String to, String subject, String text);
}
