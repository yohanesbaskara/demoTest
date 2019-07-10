package com.example.demoHomeCredit.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;

public interface CustomGenericDao<T> {

	 static final String id = "id";
	    
	    boolean create(T object);
	    List<T> read();
	    boolean update(T object);
	    boolean delete(T object);
	    
	    T findById(Object id);
	    List<T> findByCustom(String key, Object value);
		public T findByCriteria(Criterion[] criterion) throws Exception;
}