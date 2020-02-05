package com.webosmotic.security.oauth2;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.webosmotic.Enum.RoleType;
import com.webosmotic.entity.Role;
import com.webosmotic.entity.User;
import com.webosmotic.exception.OAuth2AuthenticationProcessingException;
import com.webosmotic.pojo.OAuthUserImpl;
import com.webosmotic.repository.UserRepository;
import com.webosmotic.security.oauth2.user.OAuth2UserInfo;
import com.webosmotic.security.oauth2.user.OAuth2UserInfoFactory;
import com.webosmotic.service.UserService;
import com.webosmotic.social.modal.AuthProvider;

/**
 * The CustomOAuth2UserService extends Spring Security’s
 * DefaultOAuth2UserService and implements its loadUser() method. This method is
 * called after an access token is obtained from the OAuth2 provider. In this
 * method, we first fetch the user’s details from the OAuth2 provider. If a user
 * with the same email already exists in our database then we update his
 * details, otherwise, we register a new user.
 */

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	private static final Logger _log = LoggerFactory.getLogger(CustomOAuth2UserService.class);

	@Autowired
	UserRepository userRepository;
	@Autowired
	private UserService userService;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		_log.info("0): {}", oAuth2UserRequest);
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
		_log.info("1): {}", oAuth2User);
		try {
			return processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			_log.warn("2): ", ex);
			// Throwing an instance of AuthenticationException will trigger the
			// OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(
				oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
		_log.info("3): {}", oAuth2UserInfo);

		if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
		}

		User user = userService.getUserByEmail(oAuth2UserInfo.getEmail());
		if (user != null) {
			_log.info("4): {}", user);
			if (!user.getProvider()
					.equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {
				throw new OAuth2AuthenticationProcessingException(
						"Looks like you're signed up with " + user.getProvider() + " account. Please use your "
								+ user.getProvider() + " account to login.");
			}
			user = updateExistingUser(user, oAuth2UserInfo);
		} else {
			_log.info("5): User not found by email {}", oAuth2UserInfo.getEmail());
			user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}
		return OAuthUserImpl.create(user, oAuth2User.getAttributes());
	}

	/*
	 * Method to create the new user and save into the database.
	 * @Param OAuth2UserRequest , OAuth2UserInfo
	 * @Retuen User
	 */
	private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		User user = new User();
		user.setName(oAuth2UserInfo.getName());
		user.setEmail(oAuth2UserInfo.getEmail());
		user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
		user.setProviderId(oAuth2UserInfo.getId());
		Role role = new Role(RoleType.Role_Buyer, "Buyer");
		user.getRoles().add(role);
		user.setEnable(true);
		user.setVerifiedTime(LocalDateTime.now());
		return userService.saveUser(user);

	}

	/*
	 * Method to update the existing user. 
	 * @Param User , OAuth2UserInfo
	 * @Retuen User
	 */
	private User updateExistingUser(User existingUser, OAuth2UserInfo oAuth2UserInfo) {
		existingUser.setName(oAuth2UserInfo.getName());
		return userRepository.save(existingUser);
	}
}