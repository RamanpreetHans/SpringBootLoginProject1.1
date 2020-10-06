package com.lti.otp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lti.dao.UserDao;

@Service("service_otp")
@Scope(scopeName="singleton")
public class OTPServiceImpl implements OTPService{

	private static int sentOtp;
	private static int recievedOtp;
	
	
	Random random = new Random(); ;
	
	@Override
	public int generateOtp() {
	
		int randomNo = random.nextInt(100000);
	    return randomNo;
		
	}

	@Override
	public int sendOtp(String contactNo) {
		try
		{
		String apiKey ="H4dITBJMAxED1XKcZWG2NzbmvCgOqRStV03k86j9U75hiLwyolfLOhUIsCY5l0pkZVJbzuNTiEoyKmH4";
		String senderId = "FSTSMS";
		sentOtp= this.generateOtp();
		String message = Integer.toString(sentOtp);
		message = URLEncoder.encode(message, "UTF-8");
		String language ="english";
		String route = "qt";
		String myUrl = "https://www.fast2sms.com/dev/bulk?authorization="+apiKey+"&sender_id="+senderId+"&message=37202&variables={AA}&variables_values="+message+"&language="+language+"&route="+route+"&numbers="+contactNo;
		

		// Request Sending Part
		URL url = new URL(myUrl);
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("User-Agent", "Mozilla/5.0");
		connection.setRequestProperty("cache-control","no-cache");
		
		
		System.out.println("Sending OTP...");
		
		int code = connection.getResponseCode();
		System.out.println("Response Code is :"+code);
		int responseCode = code;
		StringBuffer response = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		while(true)
		{
			String line = br.readLine();
			if(line==null)
			{
				break;
			}
			
			response.append(line);	
		}
		System.out.println(response);
		
		return responseCode;
		
		}
		catch (Exception e) {
			System.out.println("Exception "+e+" occured");
		}
		
		return 0;
		
	}

	@Override
	public int fetchOtp() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setOtp(int enteredOtp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OTPService passedOtpObject(OTPService otpService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int verifyOtp(int enteredOtp) {
		return (sentOtp==enteredOtp)?1:0;
	}


	
	
	
}
