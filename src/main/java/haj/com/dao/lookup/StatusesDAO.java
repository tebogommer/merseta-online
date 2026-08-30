package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Statuses;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class StatusesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Statuses
 	 * @author TechFinium 
 	 * @see    Statuses
 	 * @return a list of {@link haj.com.entity.Statuses}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Statuses> allStatuses() throws Exception {
		return (List<Statuses>)super.getList("select o from Statuses o");
	}

	/**
	 * Get all Statuses between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    Statuses
 	 * @return a list of {@link haj.com.entity.Statuses}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Statuses> allStatuses(Long from, int noRows) throws Exception {
	 	String hql = "select o from Statuses o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Statuses>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    Statuses
 	 * @return a {@link haj.com.entity.Statuses}
 	 * @throws Exception global exception
 	 */
	public Statuses findByKey(Long id) throws Exception {
	 	String hql = "select o from Statuses o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Statuses)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Statuses by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    Statuses
  	 * @return a list of {@link haj.com.entity.Statuses}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<Statuses> findByName(String description) throws Exception {
	 	String hql = "select o from Statuses o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Statuses>)super.getList(hql, parameters);
	}
}

