package haj.com.framework;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractDataProvider.
 */
public abstract class AbstractDataProvider implements Serializable
{
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Return file name (hibernate config) for this provider.
   *
   * @return the config file name
   */
  protected abstract String getConfigFileName();
  
  /**
   * Instantiates a new abstract data provider.
   */
  public AbstractDataProvider()
  {
    createSessionFactory();
  }
  
  /**
   * Create the SessionFactory for this Data Provider.
   */
  private void createSessionFactory()
  {
    if (DataProviderFactory.getInstance().getSessionFactory(getConfigFileName()) == null)
    {
    /*	
      Configuration configuration = new Configuration();
      configuration.configure(getConfigFileName());
      ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
      SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
      DataProviderFactory.getInstance().putSessionFactory(getConfigFileName(), sessionFactory);      
      */
        Configuration configuration = new Configuration();
        configuration.configure(getConfigFileName());
        //ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();

       // ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();        
       // SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        DataProviderFactory.getInstance().putSessionFactory(getConfigFileName(), sessionFactory); 
    }
  }
  
  /**
   * Return the SessionFactory instance for this provider.
   *
   * @return the session factory
   */
  public SessionFactory getSessionFactory()
  {
    return DataProviderFactory.getInstance().getSessionFactory(getConfigFileName());
  }
  
  /**
   * Open and return a new Session from this provider.
   *
   * @return the session
   */
  public Session getSession()
  {
    return getSessionFactory().openSession();
    //return getSessionFactory().getCurrentSession();
  }
  
 
}
