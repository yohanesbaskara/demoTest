package com.example.demoHomeCredit.common.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

public abstract class GenericHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	Class<T> persistentClass;
	Session session;	
	
	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.persistentClass = (Class<T>)((ParameterizedType) getClass().getGenericSuperclass() )
			.getActualTypeArguments()[0];
	}
	
    public Session getSession(String key) {
       if( session == null)
          session = HibernateUtil.currentSession(key);
       return session;
    }
    
    protected Session getSessionAnnotated(String className) {
        if( session == null)
           session = HibernateUtil.currentSessionAnnotated(className);
        return session;
    }
    
    protected Session getSessionAnnotated() {
        if( session == null)
           session = HibernateUtil.currentSessionAnnotated(getPersistentClass().getName());
        return session;
    }
    
	
	public Class<T> getPersistentClass() {
		return persistentClass;
	}
	
	/*@Override
	public Class<T> getEntityClass() {
		return persistentClass;
	}*/

	@Override
	public void delete(T entity) throws Exception {
		Session session = getSessionAnnotated(entity.getClass().getName());
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.delete(entity);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delete(long id) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			T t = (T)session.get(getPersistentClass(), id);
			if (t!=null) session.delete(t);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}		
	}	
	

	@Override
	public int delete(String key, String sql) throws Exception {
		Session session = getSession(key);
		Transaction transaction = null;
		int i = 0;
		try {
			transaction = session.beginTransaction();
			Query query = session.createSQLQuery(sql);
			i = query.executeUpdate();
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return i;
	}
	
	@Override
	public void delete(T entity, Session session) throws Exception {
		try {
			session.delete(entity);
		} catch(Exception exception){
			throw exception;
		} finally {
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void delete(long id, Session session) throws Exception {
		try {
			T t = (T)session.get(getPersistentClass(), id);
			if (t!=null) session.delete(t);
		} catch(Exception exception){
			throw exception;
		} finally {
		}		
	}	
	

	@Override
	public int delete(String sql, Session session) throws Exception {
		int i = 0;
		try {
			Query query = session.createSQLQuery(sql);
			i = query.executeUpdate();
		} catch(Exception exception){
			throw exception;
		} finally {
		}
		return i;
	}
	
	@Override
	public List<T> findAll(Order order) throws Exception {
		return findByCriteria(order);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, Order order, String... excludeProperty) throws Exception {
		Session session = getSessionAnnotated(exampleInstance.getClass().getName());
		Transaction transaction = null;
		List<T> list = null;
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			Example example = Example.create(exampleInstance);
			//example.excludeZeroes();
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
			example.ignoreCase();
			example.enableLike(MatchMode.ANYWHERE);
			criteria.add(example);
			if (order!=null) criteria.addOrder(order); 
			list = criteria.list();
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public PartialList<T> findByExample(int start, int count, T exampleInstance, Order order, String... excludeProperty) throws Exception {
		Session session = getSessionAnnotated(exampleInstance.getClass().getName());
		Transaction transaction = null;
		PartialList<T> partialList = null;
		try {
			transaction = session.beginTransaction();
			// total data
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			Example example = Example.create(exampleInstance);
			//example.excludeZeroes();
			for (String exclude : excludeProperty) {
				example.excludeProperty(exclude);
			}
			example.ignoreCase();
			example.enableLike(MatchMode.ANYWHERE);
			criteria.add(example);
			criteria.setProjection(Projections.rowCount());
			int total = ((Integer)criteria.list().iterator().next()).intValue();
			// partial data
			criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			criteria.add(example);
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			partialList = new PartialList<T>(criteria.list(), total);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return partialList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T findById(long id) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		T entity = null;
		try {
			transaction = session.beginTransaction();
			//entity = (T)session.get(getPersistentClass(), id, LockMode.UPGRADE);
			entity = (T)session.get(getPersistentClass(), id);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(long id, Session session) throws Exception {
		T entity = null;
		try {
			//entity = (T)session.get(getPersistentClass(), id, LockMode.UPGRADE);
			entity = (T)session.get(getPersistentClass(), id);
		} catch(Exception exception){
			throw exception;
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T load(long id) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		T entity = null;
		try {
			transaction = session.beginTransaction();
			//entity = (T)session.get(getPersistentClass(), id, LockMode.UPGRADE);
			entity = (T)session.load(getPersistentClass(), id);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return entity;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T findById(long id, boolean lockMode) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		T entity = null;
		try {
			transaction = session.beginTransaction();
			entity = (T)session.get(getPersistentClass(), id, LockOptions.UPGRADE);
			//entity = (T)session.get(getPersistentClass(), id);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return entity;
	}

	@Override
	public T save(T entity) throws Exception {
		Session session = getSessionAnnotated(entity.getClass().getName());
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(entity);
			transaction.commit();
		} catch(Exception exception) {
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return entity;
	}
	
	@Override
	public T update(T entity) throws Exception {
		Session session = getSessionAnnotated(entity.getClass().getName());
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.update(entity);
			transaction.commit();
		} catch(Exception exception) {
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return entity;
	}
	
	@Override
	public T save(T entity, Session session) throws Exception {
		try {
			session.save(entity);
		} catch(Exception exception) {
			throw exception;
		} finally {
		}
		return entity;
	}
	
	@Override
	public T update(T entity, Session session) throws Exception {
		try {
			session.update(entity);
		} catch(Exception exception) {
			throw exception;
		} finally {
		}
		return entity;
	}
	
	/**
	* Use this inside subclasses as a convenience method.
	*/
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Order order, Criterion... criterion) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		List<T> list = null;
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			for (Criterion c : criterion) {
				criteria.add(c);
			}
			if (order!=null) criteria.addOrder(order);
			list = criteria.list();
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Session session, Order order, Criterion... criterion) throws Exception {
		List<T> list = null;
		try {
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			for (Criterion c : criterion) {
				criteria.add(c);
			}
			if (order!=null) criteria.addOrder(order);
			list = criteria.list();
		} catch(Exception exception){
			throw exception;
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override	
	public PartialList<T> findByCriteria(int start, int count, Order order, Criterion... criterion) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		PartialList<T> partialList = null;
		try {
			transaction = session.beginTransaction();
			// total
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			for (Criterion c : criterion) {
				criteria.add(c);
			}
			criteria.setProjection(Projections.rowCount());
			long total = ((Long)criteria.list().iterator().next()).longValue();
			// partial data
			criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			for (Criterion c : criterion) {
				criteria.add(c);
			}
			if (order!=null) criteria.addOrder(order);
			criteria.setFirstResult(start);
			criteria.setMaxResults(count);
			partialList = new PartialList<T>(criteria.list(), total);
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return partialList;
	}
	
	/**
	* Use this inside subclasses as a convenience method.
	*/
	@SuppressWarnings("unchecked")
	@Override
	public T findByCriteria(Criterion... criterion) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		Transaction transaction = null;
		T t = null;
		try {
			transaction = session.beginTransaction();
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			for (Criterion c : criterion) {
				criteria.add(c);
			}
			t = (T)criteria.setMaxResults(1).uniqueResult();
			transaction.commit();
		} catch(Exception exception){
			if (transaction!=null)transaction.rollback();
			throw exception;
		} finally {
			if (session!=null) session.close();
		}
		return t;
	}
	
	/**
	* Use this inside subclasses as a convenience method.
	*/
	@SuppressWarnings("unchecked")
	@Override
	public T findByCriteria(Session session, Criterion... criterion) throws Exception {
		T t = null;
		try {
			Criteria criteria = session.createCriteria(getPersistentClass());
			criteria.setCacheable(true);
			for (Criterion c : criterion) {
				criteria.add(c);
			}
			t = (T)criteria.uniqueResult();
		} catch(Exception exception){
			throw exception;
		}
		return t;
	}

	@Override
	public int countAll() throws Exception {
		return countByCriteria();
	}

	@Override
	public int countByExample(T exampleInstance) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		try {
			Criteria crit = session.createCriteria(getPersistentClass());
			crit.setProjection(Projections.rowCount());
			crit.add(Example.create(exampleInstance));
			return (Integer) crit.list().get(0);
		} catch (Exception e) {
			throw e;
		} finally {
			if (session!=null) session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(String queryName, Object... params)
			throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		try {
			Query query = session.createQuery(queryName);
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i + 1, params[i]);
			}
			final List<T> result = (List<T>) query.list();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session!=null) session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParams(String queryName,
			Map<String, ? extends Object> params) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		try {
			Query query = session.createQuery(queryName);
			for (final Map.Entry<String, ? extends Object> param : params
					.entrySet()) {
				query.setParameter(param.getKey(), param.getValue());
			}
			final List<T> result = (List<T>) query.list();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			if (session!=null) session.close();
		}
	}
	
	protected int countByCriteria(Criterion... criterion) throws Exception {
		Session session = getSessionAnnotated(getPersistentClass().getName());
		try {
			Criteria crit = session.createCriteria(getPersistentClass());
			crit.setProjection(Projections.rowCount());
			for (final Criterion c : criterion) {
				crit.add(c);
			}
			return (Integer) crit.list().get(0);
		} catch (Exception e) {
			throw e;
		} finally {
			if (session!=null) session.close();
		}
	}

	
}
