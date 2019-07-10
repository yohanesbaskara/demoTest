package com.example.demoHomeCredit.common.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;

@SuppressWarnings("deprecation")
public class HibernateUtil {
	
	static Log logger = LogFactory.getFactory().getInstance("HibernateUtil");
	
	
	private static final boolean usingAnnotations = true;
    private static HashMap<String, SessionFactory> sessionFactoryMap = new HashMap<String, SessionFactory>();
    //public static final ThreadLocal sessionMapsThreadLocal = new ThreadLocal();

    static {
        try {
            String fileName;
            SessionFactory sf;
            // Create the SessionFactory objects from *.cfg.xml
            ArrayList<String> cfglist = getHibernateCfgFiles("/");
            
            for( String key : cfglist) {
                fileName = key + ".cfg.xml";
                if( usingAnnotations) {
                    sf = new Configuration().configure(fileName).buildSessionFactory();
                } else {
                    sf = new Configuration().configure(fileName).buildSessionFactory();                    
                }
                sessionFactoryMap.put(key, sf);
            }
            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Initial SessionFactory creation failed.", ex);
            //System.out.println("Initial SessionFactories creation failed.");
            throw new ExceptionInInitializerError(ex);
    
        } // end of the try - catch block
    }
    
    /*public static Session currentSession() throws HibernateException {
        return currentSession("");
    }*/

    public static Session currentSession(String key) throws HibernateException {
       /* HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        if( sessionMaps == null) {
            sessionMaps = new HashMap();
            sessionMapsThreadLocal.set(sessionMaps);
        }*/
        // Open a new Session, if this Thread has none yet
       /* Session s = (Session) sessionMaps.get(key);
        if( s == null) {
            s = ((SessionFactory) sessionFactoryMap.get(key)).openSession();
            sessionMaps.put(key, s);
        }*/
    	ModelInterceptor interceptor = new ModelInterceptor();
        Session s = ((SessionFactory) sessionFactoryMap.get(key)).withOptions().interceptor(interceptor).openSession();
        return s;
    }
    
    public static Session currentSessionAnnotated(String className) throws HibernateException {
        /*HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        if( sessionMaps == null) {
            sessionMaps = new HashMap();
            sessionMapsThreadLocal.set(sessionMaps);
        }*/
        // get key first
        String key = "";
        if( sessionFactoryMap != null) {
        	for (Map.Entry<String, SessionFactory> entry : sessionFactoryMap.entrySet()) {
  //      		logger.info(" Y : "+className + " // "+entry.getValue()+ " // " + entry.getKey());
        		ClassMetadata metadata = entry.getValue().getClassMetadata(className);
        		if (metadata!=null) {
        			key = entry.getKey();
//        			logger.info(" X : "+className + " // " + metadata.getEntityName() + " // " + key );
        		}
        	}
        }
        // Open a new Session, if this Thread has none yet
        /*Session s = (Session) sessionMaps.get(key);
        if( s == null || (s!=null && !s.isOpen()) || (s!=null && !s.isConnected())) {   
        	ModelInterceptor interceptor = new ModelInterceptor();
            s = ((SessionFactory) sessionFactoryMap.get(key)).withOptions().interceptor(interceptor).openSession();
            sessionMaps.put(key, s);
        }*/
        ModelInterceptor interceptor = new ModelInterceptor();
        Session s = ((SessionFactory) sessionFactoryMap.get(key)).withOptions().interceptor(interceptor).openSession();
        return s;
    }

    public static ArrayList<String> getHibernateCfgFiles(String path) {
        ArrayList<String> ret_arry = new ArrayList<String>();
        String fileName, key;
        String[] str_arry;

        URL url = Configuration.class.getResource( path);
        File file = new File( url.getFile());
        if( file.isDirectory()) {
            for( File f : file.listFiles()) {
                if( f.isFile()) {
                    fileName = f.getName();
                    if( fileName.endsWith(".cfg.xml")) {
                        str_arry = fileName.split("\\.");
                        key = str_arry[0];
                        ret_arry.add( key);
                    }
                }
            }
        }     
        return ret_arry;
        
    }

    /*public static void closeSession() {
        logger.debug("closing a single session");
        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if( sessionMaps != null) {
            Session session = sessionMaps.get("");
            if( session != null && session.isOpen())
                session.close();
        }
    }

    public static void closeSessions() throws HibernateException {
        logger.debug("closing sessions");
        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if( sessionMaps != null) {
            for( Session session : sessionMaps.values()) {
                if( session.isOpen())
                    session.close();
            };
        }
    }*/
    
    /**
     * Closes the current SessionFactory and releases all resources.
     * <p>
     * The only other method that can be called on HibernateUtil
     * after this one is rebuildSessionFactory(Configuration).
     */
    public static void shutdown() {
        logger.debug("Shutting down Hibernate");
        //closeSessions();
        // Close caches and connection pools
        if( sessionFactoryMap != null) {
            for( SessionFactory sf : sessionFactoryMap.values()) {
                 sf.close();
            }
        }
        sessionFactoryMap = null;  // Clear static variables
    }
	
	
/*	private static SessionFactory sessionFactory;
	
	static {
		try {
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();			
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static Session getSession() {
		// entity intercepter
		//ModelInterceptor interceptor = new ModelInterceptor();
		//Session session = getSessionFactory().withOptions().interceptor(interceptor).openSession();
		ModelInterceptor interceptor = new ModelInterceptor();
		//Session session = getSessionFactory().openSession(interceptor);
		Session session = getSessionFactory().withOptions().interceptor(interceptor).openSession();
				
		//logger.info("Statistics : "+getSessionFactory().getStatistics());
		
		Statistics stats = getSessionFactory().getStatistics();

		double queryCacheHitCount  = stats.getQueryCacheHitCount();
		double queryCacheMissCount = stats.getQueryCacheMissCount();
		double queryCacheHitRatio = queryCacheHitCount / (queryCacheHitCount + queryCacheMissCount);

		logger.info("Query Hit ratio:" + queryCacheHitRatio);

		EntityStatistics entityStats = stats.getEntityStatistics( Cat.class.getName() );
		long changes =
		        entityStats.getInsertCount()
		        + entityStats.getUpdateCount()
		        + entityStats.getDeleteCount();
		log.info(Cat.class.getName() + " changed " + changes + "times"  );
		
		
		
		return session;
	}
	
	public static SessionFactory getSessionFactory() {
		// Alternatively, you could look up in JNDI here
		return sessionFactory;
	}
	
	public static void shutdown() {
		// Close caches and connection pools
		getSessionFactory().close();
	}*/
	
	
	
	
/*	private static HashMap<String, SessionFactory> sessionFactoryMap = new HashMap<String, SessionFactory>();
	 
    public static final ThreadLocal sessionMapsThreadLocal = new ThreadLocal();
 
    public static Session currentSession(String key) throws HibernateException { 
        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get(); 
        if (sessionMaps == null) {
            sessionMaps = new HashMap();
            sessionMapsThreadLocal.set(sessionMaps);
        }
 
        // Open a new Session, if this Thread has none yet
        Session s = (Session) sessionMaps.get(key);
        if (s == null) {
            s = ((SessionFactory) sessionFactoryMap.get(key)).openSession();
            sessionMaps.put(key, s);
        } 
        return s;
    }
 
    public static Session currentSession() throws HibernateException {
        return currentSession("");
    }
 
    public static void closeSessions() throws HibernateException {
        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if (sessionMaps != null) {
            for (Session session : sessionMaps.values()) {
                if (session.isOpen())
                    session.close();
            };
        }
    }
 
    public static void closeSession() {
        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if (sessionMaps != null) {
            Session session = sessionMaps.get("");
            if (session != null && session.isOpen())
                session.close();
        }
    }
 
    public static void buildSessionFactories(HashMap<String, String> configs) {
        try {
            // Create the SessionFactory
            for (String key : configs.keySet()) {
                URL url = HibernateUtil.class.getResource(configs.get(key));
                SessionFactory sessionFactory = new Configuration().configure(url).buildSessionFactory();
                sessionFactoryMap.put(key, sessionFactory);
            } 
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex); 
        } // end of the try - catch block
    }
 
    public static void buildSessionFactory(String key, String path) {
        try {
            // Create the SessionFactory
            URL url = HibernateUtil.class.getResource(path);
            SessionFactory sessionFactory = new Configuration().configure(url).buildSessionFactory();
            sessionFactoryMap.put(key, sessionFactory); 
        } catch (Throwable ex) { 
        	logger.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex); 
        } // end of the try - catch block
    }*/
	
	
	
	
	
	
	

}
