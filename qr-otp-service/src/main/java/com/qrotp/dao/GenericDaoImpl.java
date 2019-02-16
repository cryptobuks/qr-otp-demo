package com.qrotp.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class GenericDaoImpl <T, PK extends Serializable> implements GenericDao<T, PK>{
	private Class<T> type;
	
	@PersistenceContext
	private	EntityManager em;
	
	public GenericDaoImpl(Class<T> type) {
	    this.type = type;
	}
	
	public void create(T o) {
		em.persist(o);
	}
	
	public T read(PK id) {
		return	em.find(type, id);
	}
	
	public void update(T o) {
		em.merge(o);
	}
	
	public void delete(T o) {
		em.remove(o);
	}

}