package com.lti.service;
import java.util.List;

import com.lti.model.UserLogin;

public interface UserService {
	
	public boolean addUser(UserLogin user);
	public boolean loginUser(UserLogin user);
	public List<UserLogin> findAllUsers();
	public boolean restorePassword(String userEmail);
	public boolean recievedOtp(int enteredOtp);
	public boolean modifyPassword(UserLogin user);
	
	 
	
	

}
