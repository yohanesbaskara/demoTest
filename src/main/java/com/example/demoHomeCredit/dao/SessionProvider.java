package com.example.demoHomeCredit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface SessionProvider {

    Session getSession();

    SessionFactory getSessionFactory();

    void close();
    
    void closeCurrentThreadSession();

}