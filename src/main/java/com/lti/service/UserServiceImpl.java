package com.lti.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lti.dao.UserDao;
import com.lti.model.UserLogin;
import com.lti.otp.OTPService;

@Service("service")
@Scope(scopeName="singleton")
public class UserServiceImpl implements UserService  {
	
	private static int sentOtp;
	private static int recievedOtp;
	
	@Autowired
	private UserDao dao ;
	
	@Autowired
	private OTPService otpService;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean addUser(UserLogin user) {
		int result = dao.createUser(user);
		return (result>0)?true:false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean loginUser(UserLogin user) {
		int result = dao.userLogin(user);
		return (result>0)?true:false;
	}

	
	@Override
	public List<UserLogin> findAllUsers() {
	return dao.readAllUsers();
		
	}

	@Override
	public boolean restorePassword(String userEmail) {
		String contactNo = dao.resetPassword(userEmail);
		
		if(contactNo!=null)
	{
		int responseCode =	otpService.sendOtp(contactNo);

		if(responseCode==200)
		{
			return true;
		}
	else return false;
		
	}
		
		else return false;
		
	}

	@Override
	public boolean recievedOtp(int enteredOtp) {
		
		int result = otpService.verifyOtp(enteredOtp);
		return (result>0)?true:false;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean modifyPassword(UserLogin user) {
		
		int result = dao.changePassword(user);
		return (result>0)?true:false;
		
	}

	
	
	

}
