package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.LegacyModeratorQualification;

public class LegacyModeratorQualificationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyModeratorQualification
 	 * @author TechFinium 
 	 * @see    LegacyModeratorQualification
 	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorQualification> allLegacyModeratorQualification() throws Exception {
		return (List<LegacyModeratorQualification>)super.getList("select o from LegacyModeratorQualification o");
	}

	/**
	 * Get all LegacyModeratorQualification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyModeratorQualification
 	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorQualification> allLegacyModeratorQualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyModeratorQualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyModeratorQualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorQualification> findByModeratorRegNo(String moderatorRegNo) throws Exception {
	 	String hql = "select o from LegacyModeratorQualification o where o.moderatorRegNo = :moderatorRegNo and o.qualification is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("moderatorRegNo", moderatorRegNo.trim());
		return (List<LegacyModeratorQualification>)super.getList(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyModeratorQualification
 	 * @return a {@link haj.com.entity.LegacyModeratorQualification}
 	 * @throws Exception global exception
 	 */
	public LegacyModeratorQualification findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyModeratorQualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyModeratorQualification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyModeratorQualification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyModeratorQualification
  	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorQualification> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyModeratorQualification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyModeratorQualification>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyModeratorQualificationNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyModeratorQualification o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorQualification> allLegacyModeratorQualificationNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyModeratorQualification o where o.processed = false";
		return (List<LegacyModeratorQualification>) super.getList(hql,  numberOfEntries);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyModeratorQualification o")).intValue();
	}
}


