package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.MaritalStatus;

public class MaritalStatusDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MaritalStatus
 	 * @author TechFinium 
 	 * @see    MaritalStatus
 	 * @return a list of {@link haj.com.entity.MaritalStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MaritalStatus> allMaritalStatus() throws Exception {
		return (List<MaritalStatus>)super.getList("select o from MaritalStatus o");
	}

	/**
	 * Get all MaritalStatus between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MaritalStatus
 	 * @return a list of {@link haj.com.entity.MaritalStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MaritalStatus> allMaritalStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from MaritalStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MaritalStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MaritalStatus
 	 * @return a {@link haj.com.entity.MaritalStatus}
 	 * @throws Exception global exception
 	 */
	public MaritalStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from MaritalStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MaritalStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MaritalStatus by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MaritalStatus
  	 * @return a list of {@link haj.com.entity.MaritalStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MaritalStatus> findByName(String description) throws Exception {
	 	String hql = "select o from MaritalStatus o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MaritalStatus>)super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public List<MaritalStatus> findByNameAspopulated(String description) throws Exception {
	 	String hql = "select o from MaritalStatus o where o.description like  :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MaritalStatus>)super.getList(hql, parameters);
	}
}

