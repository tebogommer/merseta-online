package haj.com.dao;

import java.util.List;

import haj.com.entity.HAJProperties;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class HAJPropertiesDAO.
 */
public class HAJPropertiesDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * All props.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<HAJProperties> allProps() throws Exception {
		return (List<HAJProperties>)getList("select o from HAJProperties o");
	}
}
