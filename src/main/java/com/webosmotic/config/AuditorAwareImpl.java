package com.webosmotic.config;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.webosmotic.controller.AuthController;
import com.webosmotic.pojo.MyUserDetail;

public class AuditorAwareImpl implements AuditorAware<Long> {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		/*
		 * if (authentication == null || !authentication.isAuthenticated() ||
		 * authentication instanceof AnonymousAuthenticationToken) { return
		 * Optional.empty(); }
		 */
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			MyUserDetail userPrincipal = (MyUserDetail) authentication.getPrincipal();
			logger.info("userPrincipal" + " " + userPrincipal.getId());

			if (userPrincipal != null && userPrincipal.getId() != null) {
				return Optional.of(userPrincipal.getId());
			} else {
				return Optional.empty();
			}
		} else {
			return Optional.empty();
		}
	}

}
