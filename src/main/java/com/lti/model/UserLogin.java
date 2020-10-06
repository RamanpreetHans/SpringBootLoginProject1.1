package com.lti.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(scopeName = "prototype")
@Entity
@Table(name="user_login")
public class UserLogin {
	
	@Id
	@Column(name="user_email")
	private String userEmail;
	
	
	@Column(name="user_name")
	private String username;
	
	@Column(name="pass")
	private String password;
	
	@Column(name="contact_no")
	private String contactNo;

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
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	//Constructors
	public UserLogin() {
		
	}
	@Override
	public String toString() {
		return "UserLogin [userEmail=" + userEmail + ", username=" + username + ", password=" + password
				+ ", contactNo=" + contactNo + "]";
	}
	public UserLogin(String userEmail, String username, String password, String contactNo) {
		super();
		this.userEmail = userEmail;
		this.username = username;
		this.password = password;
		this.contactNo = contactNo;
	}

	
	
	
	
}
