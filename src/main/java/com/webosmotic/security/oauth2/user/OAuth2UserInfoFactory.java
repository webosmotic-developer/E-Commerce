package com.webosmotic.security.oauth2.user;

import java.util.Map;

import com.webosmotic.Enum.AuthProvider;
import com.webosmotic.exception.OAuth2AuthenticationProcessingException;

/**
 * Factory class returning the type of OAuth2UserInfo based on the type of the
 * Social login type.
 */
public class OAuth2UserInfoFactory {

	private OAuth2UserInfoFactory() {
		throw new IllegalStateException("Utility class is not meant to be instantiated");
	}

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProvider.Google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(AuthProvider.Facebook.toString())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(
					"Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}