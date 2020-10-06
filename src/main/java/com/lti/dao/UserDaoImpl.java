package com.lti.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lti.encryption.Encryption;
import com.lti.model.UserLogin;
import com.lti.otp.OTPService;


@Repository("dao")
public class UserDaoImpl implements UserDao {
	
	//Local Variables
	private boolean validUser=false;
	
	
	
	@Autowired
	OTPService otpService;
	
	
	@Autowired
	private Encryption encDecObj;
	
	@Autowired
	private List<UserLogin> usersList;  // = new ArrayList<UserLogin>();
	
	@PersistenceContext
	private EntityManager entityManager;

	public UserDaoImpl()
	{
		
	}
	
	//Add User Function
	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public int createUser(UserLogin user) {
		System.out.println("Creating User");
		String encryptedPassword=encDecObj.encryptPass(user.getPassword());
		user.setPassword(encryptedPassword);
		entityManager.persist(user);
		return 1;
	}
	


	//Login Function
	
	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public int userLogin(UserLogin user) {
		System.out.println("Logging You In");
		String jpql = "Select u from UserLogin u";
		TypedQuery<UserLogin> tquery = entityManager.createQuery(jpql,UserLogin.class);
		usersList = tquery.getResultList();
		for(UserLogin u:usersList)
		{	
			
			if(u.getUserEmail().equals(user.getUserEmail()) && encDecObj.decryptPass(u.getPassword()).equals(user.getPassword()))
			{	
				System.out.println("LoggedIn as: "+u.getUsername());
				validUser = true;
			}
		}
		return (validUser)? 1:0;
	}
	
	//Read Users

	@Override
	public List<UserLogin> readAllUsers() {
		String jpql = "Select u from UserLogin u";
		TypedQuery<UserLogin> tquery =entityManager.createQuery(jpql,UserLogin.class);
		return tquery.getResultList();
	
	}

	@Override
	public String resetPassword(String userEmail) {
		UserLogin user = entityManager.find(UserLogin.class, userEmail);
		if(user!=null)
		{	
			String contactNo=user.getContactNo();
//			int generatedOtp = otpService.generateOtp();
//			otpService.sendOtp(generatedOtp, contactNo);
//			sentOtp=generatedOtp;
			return contactNo;
		}
		// Case When No User Exists with Entered Email  | Email is Incorrect
		else 
		{
			System.out.println("Entered Email is Not Valid");
			System.out.println("OR User Does'nt Exists");
			return null;
		}
		
	}

	//Change Password
	@Override
	@Transactional(propagation = Propagation.MANDATORY)
	public int changePassword(UserLogin user) {
		String userEmail = user.getUserEmail();
		String jpql = "SELECT u FROM UserLogin u WHERE u.userEmail = :userEmail";
		TypedQuery<UserLogin> tquery =entityManager.createQuery(jpql,UserLogin.class);
		tquery.setParameter("userEmail", userEmail);
		UserLogin userDb = tquery.getSingleResult();
		String newPassword = encDecObj.encryptPass(user.getPassword());
		
		userDb.setPassword(newPassword);
		UserLogin updatedUser = entityManager.merge(userDb);
		
		return (updatedUser!=null)?1:0;
				
		
	}
	
	
	
	

}

