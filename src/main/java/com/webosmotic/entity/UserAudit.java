package com.webosmotic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

@MappedSuperclass
public abstract class UserAudit extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "created_by")
    @CreatedBy
    private int createdBy;
 
    @Column(name = "modified_by")
    @LastModifiedBy
    private int modifiedBy;

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}
