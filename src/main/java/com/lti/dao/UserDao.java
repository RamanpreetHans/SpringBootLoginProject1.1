package com.lti.dao;

import java.util.List;

import com.lti.model.UserLogin;

public interface UserDao {

	public int createUser(UserLogin user);
	public int userLogin(UserLogin user);
	public List<UserLogin> readAllUsers();
	public String resetPassword(String userEmail);
	public int changePassword(UserLogin user);
}
