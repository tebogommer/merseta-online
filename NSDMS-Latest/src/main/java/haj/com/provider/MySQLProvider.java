package haj.com.provider;

import haj.com.framework.AbstractDataProvider;



// TODO: Auto-generated Javadoc
/**
 * The Class MySQLProvider.
 */
public class MySQLProvider extends AbstractDataProvider
{
  
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new my SQL provider.
   */
  public MySQLProvider()
  {
    super();
  }
  
  /* (non-Javadoc)
   * @see haj.com.framework.AbstractDataProvider#getConfigFileName()
   */
  @Override
  protected String getConfigFileName()
  {
    return "hibernate.cfg.xml";
  }

}
