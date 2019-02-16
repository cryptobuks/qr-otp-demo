package com.qrotp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qrotp.dao.UserOTPRepository;
import com.qrotp.entities.UserOTP;

@Service
@Transactional
public class OTPService {

	@Autowired
	private	UserOTPRepository userOTPRepository;

	public	String	generateOTP(String emailId) {
		int[] digits= {0,1,2,3,4,5,6,7,8,9};
		String otp="";
		for(int i=0;i<6;i++) {
			int num=	Math.abs((int)(Math.random() * digits.length));
			otp+=num;
		}
		
		UserOTP rec=	userOTPRepository.findById(emailId).orElse(null);
		
		if(rec==null) {
			rec=new	UserOTP();
			rec.setEmail(emailId);
			rec.setOtp(otp);
			userOTPRepository.save(rec);
		}
		else {
			rec.setOtp(otp);
			userOTPRepository.save(rec);
		}
		return	otp;
	}

	public	boolean	validateOTP(String email,String otp) {
		UserOTP rec=	userOTPRepository.findById(email).orElse(null);
		if(rec==null || otp==null) {
			return	false;
		}
		else if(otp.equals(rec.getOtp())){
			return	true;
		}
		else {
			return	false;
		}
	}

}
