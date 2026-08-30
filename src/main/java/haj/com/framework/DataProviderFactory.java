package haj.com.framework;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating DataProvider objects.
 */
public class DataProviderFactory implements Serializable
{
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;
  
  /** The instance. */
  private static DataProviderFactory instance;
  
  /**
   * Return the singleton instance.
   *
   * @return single instance of DataProviderFactory
   */
  public static DataProviderFactory getInstance()
  {
    if (instance==null)
      createInstance();
    return instance;
  }
  
  /**
   * Create the singleton instance.
   * 
   */
  private static synchronized void createInstance()
  {
    instance = new DataProviderFactory();
  }
  
  /** The session factory map. */
  private static Map<String, SessionFactory> sessionFactoryMap = new HashMap<String, SessionFactory>();
  
  /**
   * Private constructor to force singleton.
   * 
   */
  private DataProviderFactory()
  {
    
  }
  
  /**
   * Return SessionFactory with specified id from available map.
   *
   * @param id the id
   * @return the session factory
   */
  public SessionFactory getSessionFactory(String id)
  {
    if (sessionFactoryMap.containsKey(id))
      return sessionFactoryMap.get(id);
    return null;
  }
  
  /**
   * Add SessionFactory with specified id to available map.
   *
   * @param id the id
   * @param sessionFactory the session factory
   */
  public void putSessionFactory(String id, SessionFactory sessionFactory)
  {
    if (!sessionFactoryMap.containsKey(id))
      sessionFactoryMap.put(id, sessionFactory);
  }
}
