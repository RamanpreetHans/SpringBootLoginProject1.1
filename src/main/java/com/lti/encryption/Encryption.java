package com.lti.encryption;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope (scopeName = "singleton")
public class Encryption {

	AES256TextEncryptor enc =new AES256TextEncryptor();

	public String encryptPass(String pass)
	{
		AES256TextEncryptor enc = new AES256TextEncryptor();
		String secretKey = "2@4$56&";
		enc.setPassword(secretKey);
		return enc.encrypt(pass);
		
	}
	
	public String decryptPass(String encPass)
	{
		AES256TextEncryptor dec = new AES256TextEncryptor();
		String secretKey ="2@4$56&";
		dec.setPassword(secretKey);
		return dec.decrypt(encPass);
	}
	
	public static void main( String args[])
	{
		Encryption obj = new Encryption();
		String encPass= obj.encryptPass("Han");
		System.out.println(encPass);
		System.out.println(obj.encryptPass("Han").length());
		System.out.println("Original Password: "+obj.decryptPass(encPass));
	
	}
		

}
