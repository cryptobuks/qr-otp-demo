package com.qrotp.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.qrotp.entities.UserLoginCredentials;

/*
@Repository
public class UserLoginCredentialsDAO extends	GenericDaoImpl<UserLoginCredentials, String>
implements	GenericDao<UserLoginCredentials, String>{
	public UserLoginCredentialsDAO() {
		super(UserLoginCredentials.class);
	}
}
*/


@Repository
public interface UserLoginCredentialsRepository extends	CrudRepository<UserLoginCredentials, String>{
	
}
