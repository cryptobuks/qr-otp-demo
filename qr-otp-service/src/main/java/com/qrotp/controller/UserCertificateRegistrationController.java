package com.qrotp.controller;

import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qrotp.dto.UploadCertificateAcknowledgement;
import com.qrotp.service.OTPService;
import com.qrotp.service.UserCertificateService;

@Controller
public class UserCertificateRegistrationController {

	@Autowired
	private	UserCertificateService userCertificateService;
	
	@Autowired
	private	OTPService	otpService;
	
	@RequestMapping(value="/uploadcertificate",method=RequestMethod.POST)
	@ResponseBody
	public	UploadCertificateAcknowledgement uploadCertificate(@RequestParam(name="email") String emailId,@RequestParam(name="publickey") byte[] publicKeyBytes){
		emailId=emailId.trim().toLowerCase();
		
		Encoder encoder=	Base64.getEncoder();
		String publicKey=encoder.encodeToString(publicKeyBytes);
		
		UploadCertificateAcknowledgement ack=new	UploadCertificateAcknowledgement();
		try {
			userCertificateService.saveUserCertificate(emailId, publicKey);
			ack.setErrorCode(0);
			ack.setErrorDescription("OK");
		}
		catch(Exception e) {
			ack.setErrorCode(1);
			ack.setErrorDescription(e.getMessage());
		}
		return	ack;
	}
	
	@RequestMapping(value="/generatephotootp/{email}/{encrypt}",method=RequestMethod.GET, produces=MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public	byte[]	generateOTP(@PathVariable("email") String emailId,@PathVariable("encrypt") String encrypt) {
		
		Boolean bEncrypt=false;
		
		try {
			bEncrypt=Boolean.parseBoolean(encrypt);
		}
		catch(Exception e) {}
		
		String otp=otpService.generateOTP(emailId);
		byte[] imageData=null;
		try {
			imageData=userCertificateService.generateQRCodeImage(emailId, bEncrypt, otp);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return	imageData;
	}

}
