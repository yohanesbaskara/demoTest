package com.example.demoHomeCredit.common.util;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;


public interface GenericDAO<T, ID extends Serializable> {
	
    //Class<T> getEntityClass();

    int countAll() throws Exception;
    int countByExample(final T exampleInstance) throws Exception;
    
	List<T> findAll(Order order) throws Exception;
	List<T> findByExample(T exampleInstance, Order order, String... excludeProperty) throws Exception;
	List<T> findByCriteria(Order order, Criterion... criterion) throws Exception;
    List<T> findByNamedQuery(final String queryName, Object... params) throws Exception;
    List<T> findByNamedQueryAndNamedParams(final String queryName, final Map<String, ?extends Object> params) throws Exception;
	
	PartialList<T> findByExample(int start, int count, T exampleInstance, Order order, String... excludeProperty) throws Exception;
	PartialList<T> findByCriteria(int start, int count, Order order, Criterion... criterion) throws Exception;
	
	T findById(long id) throws Exception;
	T findById(long id, Session session) throws Exception;
	T load(long id) throws Exception;
	T findById(long id, boolean lockMode) throws Exception;
	T findByCriteria(Criterion... criterion) throws Exception;
	T findByCriteria(Session session, Criterion... criterion) throws Exception;
	
	// make persistent
	T save(final T entity) throws Exception;
	T update(T entity) throws Exception;
	T save(T entity, Session session) throws Exception;
	T update(T entity, Session session) throws Exception;
	
	// make transient
	void delete(T entity) throws Exception;
	void delete(long id) throws Exception;
	int delete(String key, String sql) throws Exception;
	void delete(T entity, Session session) throws Exception;
	void delete(long id, Session session) throws Exception;
	int delete(String sql, Session session) throws Exception;
	
	// session
	Session getSession(String key);
	List<T> findByCriteria(Session session, Order order, Criterion... criterion) throws Exception;
	
}
