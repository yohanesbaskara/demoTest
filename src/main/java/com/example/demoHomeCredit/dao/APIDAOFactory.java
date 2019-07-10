 package com.example.demoHomeCredit.dao;

public abstract class APIDAOFactory {

	/**
	* Factory method for instantiation of concrete factories.
	*/
	@SuppressWarnings("rawtypes")
	public static APIDAOFactory instance(Class factory) {
		try {
			return (APIDAOFactory)factory.newInstance();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Couldn't create BasicDAOFactory: " + factory);
		}
	}
	
	// Add your DAO interfaces here
	
	public abstract SessionProvider getSessionProviderDAO();
	public abstract UserGroupDAO getUserGroupDAO();
	public abstract UsersDAO getUsersDAO();
	public abstract LookupDAO getLookupDAO();

}
