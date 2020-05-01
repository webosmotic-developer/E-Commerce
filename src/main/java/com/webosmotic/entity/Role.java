package com.webosmotic.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.webosmotic.Enum.RoleType;

/**
 * Main Entity Class which describes the role of the user
 */

@Entity
@AttributeOverride(name = "id", column = @Column(name = "role_id"))
public class Role extends BaseEntity implements Serializable {

	public Role() {
	}

	private static final long serialVersionUID = 1L;

	@Column(name = "name", updatable = false)
	@Enumerated(EnumType.STRING)
	private RoleType name;

	private String description;

	public RoleType getName() {
		return name;
	}

	public void setName(RoleType name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Role(RoleType name, String description) {
		this.name = name;
		this.description = description;
	}
}
