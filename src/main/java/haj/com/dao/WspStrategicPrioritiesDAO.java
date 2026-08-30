package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.WspStrategicPriorities;

public class WspStrategicPrioritiesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspStrategicPriorities
 	 * @author TechFinium 
 	 * @see    WspStrategicPriorities
 	 * @return a list of {@link haj.com.entity.WspStrategicPriorities}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspStrategicPriorities> allWspStrategicPriorities() throws Exception {
		return (List<WspStrategicPriorities>)super.getList("select o from WspStrategicPriorities o");
	}

	/**
	 * Get all WspStrategicPriorities between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    WspStrategicPriorities
 	 * @return a list of {@link haj.com.entity.WspStrategicPriorities}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspStrategicPriorities> allWspStrategicPriorities(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspStrategicPriorities o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspStrategicPriorities>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    WspStrategicPriorities
 	 * @return a {@link haj.com.entity.WspStrategicPriorities}
 	 * @throws Exception global exception
 	 */
	public WspStrategicPriorities findByKey(Long id) throws Exception {
	 	String hql = "select o from WspStrategicPriorities o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspStrategicPriorities)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspStrategicPriorities by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    WspStrategicPriorities
  	 * @return a list of {@link haj.com.entity.WspStrategicPriorities}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<WspStrategicPriorities> findByName(String description) throws Exception {
	 	String hql = "select o from WspStrategicPriorities o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspStrategicPriorities>)super.getList(hql, parameters);
	}
}

