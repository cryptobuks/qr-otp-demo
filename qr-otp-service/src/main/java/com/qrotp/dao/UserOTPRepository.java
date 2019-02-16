package com.qrotp.dao;

import org.springframework.data.repository.CrudRepository;

import org.springframework.stereotype.Repository;

import com.qrotp.entities.UserOTP;

/*
@Repository
public class UserOTPDAO extends	GenericDaoImpl<UserOTP, String>{
	public UserOTPDAO() {
		super(UserOTP.class);
	}
}
*/

@Repository
public interface UserOTPRepository extends	CrudRepository<UserOTP, String>{
}