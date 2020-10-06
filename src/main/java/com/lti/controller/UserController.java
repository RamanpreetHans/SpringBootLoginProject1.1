package com.lti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.UserLogin;
import com.lti.otp.OTPService;
import com.lti.service.UserService;

@RestController
@RequestMapping(path = "UserLoginApp")
@CrossOrigin
public class UserController {

	@Autowired
	private UserService service ;
	
	@Autowired
	private OTPService otpService;
	
	@Autowired
	List<UserLogin> usersList ;
	
	@GetMapping("/")
	public void welcome()
	{
		System.out.println("Welcome to UserLogin App");
	}
	@PostMapping("/add")
	public String add(@RequestBody UserLogin user)
	{
		String added = "User Added Successfully";
		String notAdded = "User could Not be Added";
		boolean result=service.addUser(user);
		return (result)?added:notAdded;
	}
	
	
	@PostMapping("/login")
	public void login(@RequestBody UserLogin user)
	{
		boolean result = service.loginUser(user);
		if(result)
		{
			System.out.println("Login Successful");
		}
		
		else System.out.println("Login Unsuccessful");
		
	}
	
	@GetMapping("/getUsers")
	public List<UserLogin> getUsers()
	{
		usersList = service.findAllUsers();
		
		for(UserLogin u :usersList)
		{
			System.out.println("UserName: "+u.getUsername()+" Password: "+u.getPassword()+" Email: "+u.getUserEmail()+" ContactNO. : "+u.getContactNo());
		}
		return usersList;
	}
	
	
	@PutMapping("/forgotPassword")
	public String resetPassword(@RequestBody UserLogin user)
	{	
		String userEmail=user.getUserEmail();
		
		boolean result = service.restorePassword(userEmail);
		if(result)
		{
			//System.out.println("Password Reset Successfully");
			return "OTP Sent On Registered Contact No.";
		}
		
		else
		{
			//System.out.println("Password could not be reset");
			return "Password Could Not be Reset";
		}
			
	}
	
	@PostMapping(path = "/forgotPassword/{enteredOtp}")
	public String recieveUserOtp(@PathVariable("enteredOtp") int enteredOtp)
	{
		boolean result = service.recievedOtp(enteredOtp);
		
		if(result)
		{
			System.out.println("OTP Matched");
			return "OTP Matched";
		}
		
		else 
		{
			System.out.println("OTP Does'nt Matches");
			return "OTP did'nt Matched";
		}
	}
	
	@PostMapping(path = "/changePassword")
	public String changePassword(@RequestBody UserLogin user)
	{
		boolean result = service.modifyPassword(user);
		if(result)
			return "Passwrod Changed Successfully";
		return "Password Could not be Changed";
	}
	
	
	
	
}
