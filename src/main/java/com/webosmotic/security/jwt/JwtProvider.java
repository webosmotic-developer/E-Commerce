package com.webosmotic.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT Provider Class to generate the new JWT token and validate the generated
 * token and validate the JWT token with every request.
 */
@Component
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${app.jwtSecret}")
	private String jwtSecret;
	@Value("${app.jwtExpiration}")
	private int jwtExpiration;

	/*
	 * method to generate the JWT token.
	 * 
	 * @Param Authentication Object from the spring security
	 * 
	 * @Return JWT token
	 */
	public String generate(Authentication authentication) {
		String username = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			username = userDetails.getUsername();
		} else {
			username = authentication.getName();
		}

		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpiration * 1000))
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	/*
	 * method to validate the JWT
	 * 
	 * @Param JWT token
	 * 
	 * @Return boolean
	 */
	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (final Exception e) {
			logger.error("JWT Authentication Failed");
		}
		return false;
	}

	/*
	 * method to extract the userName detail from the JWT token
	 * 
	 * @Param JWT token
	 * 
	 * @Return boolean
	 */
	public String getUserName(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

}
