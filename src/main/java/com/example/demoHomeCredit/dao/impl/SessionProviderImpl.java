package com.example.demoHomeCredit.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import com.example.demoHomeCredit.dao.SessionProvider;

@Component("sp")
public class SessionProviderImpl implements SessionProvider {
    private final SessionFactory sessionFactory;
    private final ThreadLocal<Session> cache;
    
    
    public SessionProviderImpl(){
          Configuration configuration = new Configuration().configure();
          this.sessionFactory = configuration.buildSessionFactory();
          this.cache = new ThreadLocal<Session>();
    }
    
    public Session getSession(){
        Session s = cache.get();
        if(s == null || !s.isOpen()){
            s = sessionFactory.openSession();
            cache.set(s);
        }
        return s;
    }
    
    public SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
    public void close(){
        sessionFactory.close();
    }

    public void closeCurrentThreadSession() {
        Session s = cache.get();
        if(s != null && s.isOpen()){
            s.close();
        }
        cache.remove();
    }
    
}