package com.qrotp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.qrotp.entities.UserCertificate;

/*
@Repository
public class UserCertificateDAO extends	GenericDaoImpl<UserCertificate, String> implements	GenericDao<UserCertificate, String>{
	
}
*/

@Repository
public	interface	UserCertificateRepository	extends	JpaRepository<UserCertificate, String>{}