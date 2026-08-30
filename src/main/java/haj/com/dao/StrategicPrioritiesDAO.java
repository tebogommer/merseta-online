package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.StrategicPriorities;

public class StrategicPrioritiesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all StrategicPriorities
 	 * @author TechFinium 
 	 * @see    StrategicPriorities
 	 * @return a list of {@link haj.com.entity.StrategicPriorities}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StrategicPriorities> allStrategicPriorities() throws Exception {
		return (List<StrategicPriorities>)super.getList("select o from StrategicPriorities o");
	}

	/**
	 * Get all StrategicPriorities between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    StrategicPriorities
 	 * @return a list of {@link haj.com.entity.StrategicPriorities}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StrategicPriorities> allStrategicPriorities(Long from, int noRows) throws Exception {
	 	String hql = "select o from StrategicPriorities o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<StrategicPriorities>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    StrategicPriorities
 	 * @return a {@link haj.com.entity.StrategicPriorities}
 	 * @throws Exception global exception
 	 */
	public StrategicPriorities findByKey(Long id) throws Exception {
	 	String hql = "select o from StrategicPriorities o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (StrategicPriorities)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find StrategicPriorities by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    StrategicPriorities
  	 * @return a list of {@link haj.com.entity.StrategicPriorities}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<StrategicPriorities> findByName(String description) throws Exception {
	 	String hql = "select o from StrategicPriorities o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<StrategicPriorities>)super.getList(hql, parameters);
	}
}

