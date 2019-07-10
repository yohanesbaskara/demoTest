package com.example.demoHomeCredit.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demoHomeCredit.dao.CustomGenericDao;
import com.example.demoHomeCredit.dao.SessionProvider;


@SuppressWarnings("unchecked")
public class CustomGenericDaoImpl<T> implements CustomGenericDao<T> {

    private final Class<T> entityClass;
    
    @Autowired
    public SessionProvider sp;

    public CustomGenericDaoImpl() {
        this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public boolean create(T object) {
    	Transaction trans = sp.getSession().beginTransaction();
    	try{
    		sp.getSession().save(object);
    		trans.commit();
    		return true;
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
    		trans.rollback();
    		return false;
    	}
    }
    
    public List<T> read() {
		List<T> list = sp.getSession().createCriteria(entityClass).list();
        return list;
    }

    public boolean update(T object) {
    	Transaction trans = sp.getSession().beginTransaction();
    	try{
    		sp.getSession().update(object);
    		trans.commit();
    		return true;
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
    		trans.rollback();
    		return false;
    	}
    }

    public boolean delete(T object) {
    	Transaction trans = sp.getSession().beginTransaction();
    	try{
    		sp.getSession().delete(object);
    		trans.commit();
    		return true;
    	}catch (Exception e) {
    		System.out.println(e.getMessage());
    		trans.rollback();
    		return false;
    	}
    }

    public T findById(Object id) {
    	System.out.println(sp.getSession()+ "<<<<<<<<<<<<<<<<<<SESSION");
        T result = (T) sp.getSession()
                .createCriteria(entityClass).add(Restrictions.idEq(id)).list().get(0);
        return result;
    }

    public List<T> findByCustom(String key, Object value) {
        List<T> result = sp.getSession()
                .createCriteria(entityClass).add(Restrictions.eq(key, value)).list();
        return result;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public T findByCriteria(Criterion... criterion) throws Exception {
		T t = null;
		try {
			Criteria criteria = sp.getSession().createCriteria(entityClass);
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


}