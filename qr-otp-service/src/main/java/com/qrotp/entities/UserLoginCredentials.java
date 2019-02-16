package com.qrotp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERLOGINCREDENTIALS")
public class UserLoginCredentials {

	@Id
	@Column(name="EMAIL")
	private	String	email;
	
	@Column(name="PASSWORD")
	private	String	password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
