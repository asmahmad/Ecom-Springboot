package com.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="category_id")
	private long id;
	private String name;
	private boolean is_deleted;
	private boolean is_activated;
	
	public Category() {

	}
	public Category(String name) {
		this.name=name;
		this.is_activated=true;
		this.is_deleted=false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean is_deleted() {
		return is_deleted;
	}

	public void set_deleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public boolean is_activated() {
		return is_activated;
	}

	public void set_activated(boolean is_activated) {
		this.is_activated = is_activated;
	}
	
	

}
