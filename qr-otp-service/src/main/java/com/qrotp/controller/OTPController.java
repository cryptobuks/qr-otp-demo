package com.qrotp.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.qrotp.service.OTPService;


@RestController
public class OTPController {

	@Autowired
	private	OTPService otpService;
	
	@RequestMapping(value="/validateotp",method=RequestMethod.POST)
	@ResponseBody
	public	ResponseEntity<Map<String,Object>>	validateOTP(@RequestParam("email") String emailId,@RequestParam("otp") String otp){
		emailId=emailId.toLowerCase().trim();
		Map<String,Object> response=new	HashMap<String,Object>();
		HttpStatus	httpStatus=null;
		if(otpService.validateOTP(emailId, otp)) {
			response.put("status", "VALID");
			httpStatus=HttpStatus.OK;
		}
		else {
			response.put("status", "INVALID");
			httpStatus=HttpStatus.BAD_REQUEST;
		}
		ResponseEntity<Map<String,Object>> respEntity=new	ResponseEntity<Map<String,Object>>(response, httpStatus);
		return	respEntity;
	}
	
}
