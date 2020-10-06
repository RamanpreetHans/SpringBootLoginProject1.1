package com.lti.otp;

public interface OTPService {

	public int generateOtp();
	public int sendOtp(String contactNo);
	public int fetchOtp();
	public void setOtp(int enteredOtp);
	public OTPService passedOtpObject(OTPService otpService);
	public int verifyOtp(int enteredOtp);
}
