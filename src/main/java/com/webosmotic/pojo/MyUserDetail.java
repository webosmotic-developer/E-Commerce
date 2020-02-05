package com.webosmotic.pojo;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.webosmotic.entity.User;

/**
 * Custom Class implementing the userDetails of the spring security to return
 * the UserDetails during authentication of the user.
 */

public class MyUserDetail implements UserDetails, OAuth2User, Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String userName;
	private String password;
	private String email;
	private String name;
	private boolean enable;
	private List<GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public MyUserDetail(User user) {
		this.id = user.getId();
		this.userName = user.getUsername();
		this.password = user.getPassword();
		this.enable = user.getEnable();
		this.setEmail(user.getEmail());
		this.name = user.getName();
		this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
	}

	public static MyUserDetail create(User user) {
		return new MyUserDetail(user);
	}

	public static MyUserDetail create(User user, Map<String, Object> attributes) {
		MyUserDetail userPrincipal = MyUserDetail.create(user);
		userPrincipal.setAttributes(attributes);
		return userPrincipal;
	}

	private void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enable;
	}

	@Override
	public java.util.Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
