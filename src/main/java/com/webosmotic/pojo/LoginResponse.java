package com.webosmotic.pojo;

import com.webosmotic.Enum.RoleType;

/**
 * class to describe the response for the user login.
 *
 */
public class LoginResponse {

	private String token;
	private String type = "Bearer";
	private String email;
	private String name;
	private RoleType role;

	public LoginResponse(String token, String email, String name, RoleType role) {
		this.email = email;
		this.name = name;
		this.token = token;
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
}
