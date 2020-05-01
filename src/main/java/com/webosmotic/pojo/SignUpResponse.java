package com.webosmotic.pojo;

/**
 * class to describe the response for the user signUp.
 *
 */
public class SignUpResponse {

	private String message;
	private String username;
	private String email;
	private boolean success;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "SignUpResponse [username=" + username + ", email=" + email + ", success=" + success + "]";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
