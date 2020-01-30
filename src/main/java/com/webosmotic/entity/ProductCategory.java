package com.webosmotic.entity;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "product_category")
@AttributeOverride(name = "Id", column = @Column(name = "category_id"))
public class ProductCategory extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String parentCategory;
	@NotBlank
	private String subCategory;

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
}
