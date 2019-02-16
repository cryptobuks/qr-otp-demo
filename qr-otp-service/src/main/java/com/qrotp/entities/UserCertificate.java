package com.qrotp.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="USERCERTIFICATE")
public class UserCertificate {

	@Id
	@Column(name="EMAIL")
	private	String	email;
	
	@Column(name="PUBLIC_KEY")
	private	String	publicKey;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
}
