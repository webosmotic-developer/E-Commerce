package com.webosmotic.service;

import com.webosmotic.entity.TokenType;
import com.webosmotic.entity.User;
import com.webosmotic.entity.VerificationToken;

public interface VerificationTokenService {
	
	VerificationToken createVerificationToken(User user, TokenType type, String appUrl);
	
	void deleteverificationToken(VerificationToken token);
	
	VerificationToken getVerificationToken(String token, TokenType type);
}
