package com.example.demoHomeCredit.dao.impl;

import java.io.Serializable;

import com.example.demoHomeCredit.common.util.GenericHibernateDAO;
import com.example.demoHomeCredit.dao.APIDAOFactory;
import com.example.demoHomeCredit.dao.LookupDAO;
import com.example.demoHomeCredit.dao.SessionProvider;
import com.example.demoHomeCredit.dao.UserGroupDAO;
import com.example.demoHomeCredit.dao.UsersDAO;

public class APIDAOFactoryHibernate extends APIDAOFactory implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("rawtypes")
	private GenericHibernateDAO instantiateDAO(Class daoClass) {
		try {
			GenericHibernateDAO dao = (GenericHibernateDAO) daoClass.newInstance();
			return dao;
		} catch (Exception ex) {
			throw new RuntimeException("Can not instantiate DAO: " + daoClass, ex);
		}
	}


	@Override
	public SessionProvider getSessionProviderDAO() {
		// TODO Auto-generated method stub
		return (SessionProvider) instantiateDAO(SessionProviderImpl.class);
	}
	
	@Override
	public LookupDAO getLookupDAO() {
		return (LookupDAO) instantiateDAO(LookupDAOHibernate.class);
	}


	@Override
	public UsersDAO getUsersDAO() {
		return (UsersDAO) instantiateDAO(UsersDAOHibernate.class);
	}


	@Override
	public UserGroupDAO getUserGroupDAO() {
		return (UserGroupDAO) instantiateDAO(UserGroupDAOHibernate.class);
	}



}
