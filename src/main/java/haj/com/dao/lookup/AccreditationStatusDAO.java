package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.AccreditationStatus;

public class AccreditationStatusDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all AccreditationStatus
 	 * @author TechFinium 
 	 * @see    AccreditationStatus
 	 * @return a list of {@link haj.com.entity.AccreditationStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AccreditationStatus> allAccreditationStatus() throws Exception {
		return (List<AccreditationStatus>)super.getList("select o from AccreditationStatus o");
	}
	
	@SuppressWarnings("unchecked")
	public List<AccreditationStatus> allNoLegacyAccreditationStatus() throws Exception {
		return (List<AccreditationStatus>)super.getList("select o from AccreditationStatus o where o.legacyStatus is null or o.legacyStatus <> true");
	}

	/**
	 * Get all AccreditationStatus between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    AccreditationStatus
 	 * @return a list of {@link haj.com.entity.AccreditationStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AccreditationStatus> allAccreditationStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from AccreditationStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<AccreditationStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    AccreditationStatus
 	 * @return a {@link haj.com.entity.AccreditationStatus}
 	 * @throws Exception global exception
 	 */
	public AccreditationStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from AccreditationStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (AccreditationStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find AccreditationStatus by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    AccreditationStatus
  	 * @return a list of {@link haj.com.entity.AccreditationStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<AccreditationStatus> findByName(String description) throws Exception {
	 	String hql = "select o from AccreditationStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<AccreditationStatus>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<AccreditationStatus> findByDescription(String description) throws Exception {
	 	String hql = "select o from AccreditationStatus o where o.description = :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description);
		return (List<AccreditationStatus>)super.getList(hql, parameters);
	}
	
}

