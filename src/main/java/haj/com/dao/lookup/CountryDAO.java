package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Country;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CountryDAO.
 */
public class CountryDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Country.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Country}
	 * @throws Exception global exception
	 * @see    Country
	 */
	@SuppressWarnings("unchecked")
	public List<Country> allCountry() throws Exception {
		return (List<Country>)super.getList("select o from Country o");
	}

	/**
	 * Get all Country between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Country}
	 * @throws Exception global exception
	 * @see    Country
	 */
	@SuppressWarnings("unchecked")
	public List<Country> allCountry(Long from, int noRows) throws Exception {
	 	String hql = "select o from Country o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Country>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Country}
	 * @throws Exception global exception
	 * @see    Country
	 */
	public Country findByKey(Long id) throws Exception {
	 	String hql = "select o from Country o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Country)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Country by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Country}
	 * @throws Exception global exception
	 * @see    Country
	 */
	@SuppressWarnings("unchecked")
	public List<Country> findByName(String description) throws Exception {
	 	String hql = "select o from Country o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Country>)super.getList(hql, parameters);
	}
}

