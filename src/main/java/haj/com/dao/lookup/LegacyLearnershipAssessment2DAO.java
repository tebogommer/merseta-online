package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyLearnershipAssessment2;

public class LegacyLearnershipAssessment2DAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyLearnershipAssessment2
 	 * @author TechFinium 
 	 * @see    LegacyLearnershipAssessment2
 	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2() throws Exception {
		return (List<LegacyLearnershipAssessment2>)super.getList("select o from LegacyLearnershipAssessment2 o");
	}

	/**
	 * Get all LegacyLearnershipAssessment2 between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyLearnershipAssessment2
 	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyLearnershipAssessment2 o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyLearnershipAssessment2>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyLearnershipAssessment2
 	 * @return a {@link haj.com.entity.LegacyLearnershipAssessment2}
 	 * @throws Exception global exception
 	 */
	public LegacyLearnershipAssessment2 findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyLearnershipAssessment2 o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyLearnershipAssessment2)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyLearnershipAssessment2 by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyLearnershipAssessment2
  	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment2> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyLearnershipAssessment2 o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyLearnershipAssessment2>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyLearnershipAssessment2 o")).intValue();
	}
	
	public Integer countAllLegacyLearnershipAssessment2NotProcessed() throws Exception {
		String hql = "select count(o) from LegacyLearnershipAssessment2 o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2NotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyLearnershipAssessment2 o where o.processed = false";
		return (List<LegacyLearnershipAssessment2>) super.getList(hql, numberOfEntries);
	}
}

