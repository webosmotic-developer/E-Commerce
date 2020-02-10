package com.webosmotic.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.webosmotic.Enum.AuthProvider;
import com.webosmotic.Enum.Gender;

/**
 * Main Entity Class describing the User details, address and roles
 * 
 */

@Entity
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String username;

	@NotEmpty
	@Email
	@NaturalId
	@Size(max = 80)
	@Column(unique = true)
	private String email;

	private String password;

	@Column(name = "active", columnDefinition = "BOOLEAN")
	private Boolean enable;

	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private String providerId;

	private LocalDateTime verifiedTime;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Address> adresses = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // LAZY - no session ?? TODO troubleshoot
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "role_id"))
	private List<Role> roles = new ArrayList<>();

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Address> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Address> adresses) {
		this.adresses = adresses;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", enable=" + enable + ", provider=" + provider + ", gender=" + gender + ", providerId=" + providerId
				+ ", verifiedTime=" + verifiedTime + ", adresses=" + adresses + ", roles=" + roles + "]";
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	
	public LocalDateTime getVerifiedTime() {
		return verifiedTime;
	}

	public void setVerifiedTime(LocalDateTime verifiedTime) {
		this.verifiedTime = verifiedTime;
	}
	
	public void addAddress(Address address) {
		if(address != null) {
			adresses.add(address);
		}
		address.setUser(this);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
