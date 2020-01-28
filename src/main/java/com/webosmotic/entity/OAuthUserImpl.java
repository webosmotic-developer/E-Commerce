package com.webosmotic.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * Custom Class implementing the OAuth2User of the spring security to return the
 * OAuth user details during the oAuth2 Social authentication.
 */

public class OAuthUserImpl implements OAuth2User {

	private List<GrantedAuthority> authorities;
	private Map<String, Object> attributes;
	private String name;

	public OAuthUserImpl(User user, Map<String, Object> attributes) {
		this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		this.attributes = attributes;
		this.name = user.getName();
	}

	public static OAuth2User create(User user, Map<String, Object> attributes) {
		return new OAuthUserImpl(user, attributes);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getName() {
		return name;
	}
}
