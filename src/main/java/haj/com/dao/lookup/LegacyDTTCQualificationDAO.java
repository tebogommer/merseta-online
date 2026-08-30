package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyDTTCQualification;
import haj.com.entity.lookup.LegacyModeratorQualification;

public class LegacyDTTCQualificationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyDTTCQualification
 	 * @author TechFinium 
 	 * @see    LegacyDTTCQualification
 	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCQualification> allLegacyDTTCQualification() throws Exception {
		return (List<LegacyDTTCQualification>)super.getList("select o from LegacyDTTCQualification o");
	}

	/**
	 * Get all LegacyDTTCQualification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyDTTCQualification
 	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCQualification> allLegacyDTTCQualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyDTTCQualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyDTTCQualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyDTTCQualification
 	 * @return a {@link haj.com.entity.LegacyDTTCQualification}
 	 * @throws Exception global exception
 	 */
	public LegacyDTTCQualification findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyDTTCQualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyDTTCQualification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyDTTCQualification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyDTTCQualification
  	 * @return a list of {@link haj.com.entity.LegacyDTTCQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCQualification> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyDTTCQualification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyDTTCQualification>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyDTTCQualificationNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyDTTCQualification o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCQualification> allLegacyDTTCQualificationNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyModeratorQualification o where o.processed = false";
		return (List<LegacyDTTCQualification>) super.getList(hql,  numberOfEntries);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyModeratorQualification o")).intValue();
	}
}

