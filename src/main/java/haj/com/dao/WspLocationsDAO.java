package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WspLocations;

public class WspLocationsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspLocations
 	 * @author TechFinium 
 	 * @see    WspLocations
 	 * @return a list of {@link haj.com.entity.WspLocations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspLocations> allWspLocations() throws Exception {
		return (List<WspLocations>)super.getList("select o from WspLocations o");
	}

	/**
	 * Get all WspLocations between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspLocations
 	 * @return a list of {@link haj.com.entity.WspLocations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspLocations> allWspLocations(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspLocations o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspLocations>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspLocations
 	 * @return a {@link haj.com.entity.WspLocations}
 	 * @throws Exception global exception
 	 */
	public WspLocations findByKey(Long id) throws Exception {
	 	String hql = "select o from WspLocations o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspLocations)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspLocations by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspLocations
  	 * @return a list of {@link haj.com.entity.WspLocations}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspLocations> findByName(String description) throws Exception {
	 	String hql = "select o from WspLocations o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspLocations>)super.getList(hql, parameters);
	}
}

