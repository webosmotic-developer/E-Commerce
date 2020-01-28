package com.webosmotic.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<Long>{

	@Override
	public Optional<Long> getCurrentAuditor() {
		/*
		 * Long id = null; User user = (User)
		 * SecurityContextHolder.getContext().getAuthentication().getPrincipal(); if
		 * (user != null && user.getUser_id() != null) { id = user.getUser_id(); } if
		 * (id != null) { Optional<Long> opt = Optional.of(id); return opt; } return
		 * Optional.empty();
		 */
		return Optional.empty();
	}

}
