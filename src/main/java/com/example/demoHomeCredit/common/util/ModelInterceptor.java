package com.example.demoHomeCredit.common.util;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

public class ModelInterceptor extends EmptyInterceptor {

	private static final long serialVersionUID = 1L;
	Log log = LogFactory.getFactory().getInstance(this.getClass());
	
	Session session;
	
	Set<Object> updates = new HashSet<Object>();
	
	public void setSession(Session session) {
		this.session=session;
	}

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		return super.onLoad(entity, id, state, propertyNames, types);
	}

	@Override
	public void afterTransactionCompletion(Transaction tx) {
		super.afterTransactionCompletion(tx);
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		super.onDelete(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id,
			Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		// attribute change_on of all entity replaced with new date
		for (int i=0; i<propertyNames.length; i++) {
			if ("changeOn".equalsIgnoreCase(propertyNames[i])) {
				currentState[i] = new Timestamp(new Date().getTime());
			}
		}
		return super.onFlushDirty(entity, id, currentState, previousState,
				propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state,
			String[] propertyNames, Type[] types) {
		// attribute change_on of all entity replaced with new date
		for (int i=0; i<propertyNames.length; i++) {
			if ("createOn".equalsIgnoreCase(propertyNames[i])) {
				state[i] = new Timestamp(new Date().getTime());
			}
		}
		return super.onSave(entity, id, state, propertyNames, types);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void postFlush(Iterator entities) {
		super.postFlush(entities);
	}

}
