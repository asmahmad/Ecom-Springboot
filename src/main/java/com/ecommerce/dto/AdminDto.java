package com.ecommerce.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;


@Data @NoArgsConstructor @AllArgsConstructor
public class AdminDto {
    @Size(min=3, max=10, message="Invalid first name!(3-10 characters")
    private String firstName;
    @Size(min=3, max=10, message="Invalid last name!(3-10 characters")
    private String LastName;
    private String username;
    @Size(min=5, max=15, message="Invalid password!(5-15 characters")
    private String password;
    private String repeatPassword;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepeatPassword() {
		return repeatPassword;
	}
	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}
    
    
}
