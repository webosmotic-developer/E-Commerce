package com.webosmotic.pojo;

/**
 * class to describe the request for the user SignUp.
 * 
 */
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupRequest {
	
	@NotEmpty(message = "The username cannot be blank")
	private String username;
	@NotEmpty
	@Email
	private String email;
	@NotEmpty
	@Size(min = 3, max = 100, message = "The size of the password should be grater than 3 character")
	private String password;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "SignupRequest [username=" + username + ", email=" + email + ", password=" + password + "]";
	}

}
