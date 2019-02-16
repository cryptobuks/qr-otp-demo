package com.qrotp.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USEROTP")
public class UserOTP {

	@Id
	@Column(name="EMAIL")
	private	String	email;
	
	@Column(name="OTP")
	private	String	otp;
	
	@Column(name="VALIDITY")
	private	Timestamp	validity;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Timestamp getValidity() {
		return validity;
	}

	public void setValidity(Timestamp validity) {
		this.validity = validity;
	}
}
