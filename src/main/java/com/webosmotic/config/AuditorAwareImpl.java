package com.webosmotic.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.webosmotic.pojo.MyUserDetail;

public class AuditorAwareImpl implements AuditorAware<Long>{

	  @Override
	    public Optional<Long> getCurrentAuditor() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	        if (authentication == null ||
	                !authentication.isAuthenticated() ||
	                authentication instanceof AnonymousAuthenticationToken) {
	            return Optional.empty();
	        }

	        MyUserDetail userPrincipal = (MyUserDetail) authentication.getPrincipal();

	        return Optional.ofNullable(userPrincipal.getId());
	    }
	
	

}
