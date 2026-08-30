package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyLearnershipAssessment;

public class LegacyLearnershipAssessmentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyLearnershipAssessment
 	 * @author TechFinium 
 	 * @see    LegacyLearnershipAssessment
 	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessment() throws Exception {
		return (List<LegacyLearnershipAssessment>)super.getList("select o from LegacyLearnershipAssessment o");
	}

	/**
	 * Get all LegacyLearnershipAssessment between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyLearnershipAssessment
 	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessment(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyLearnershipAssessment o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyLearnershipAssessment>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyLearnershipAssessment
 	 * @return a {@link haj.com.entity.LegacyLearnershipAssessment}
 	 * @throws Exception global exception
 	 */
	public LegacyLearnershipAssessment findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyLearnershipAssessment o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyLearnershipAssessment)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyLearnershipAssessment by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyLearnershipAssessment
  	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyLearnershipAssessment o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyLearnershipAssessment>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyLearnershipAssessment o")).intValue();
	}
	
	public Integer countAllLegacyLearnershipAssessmentNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyLearnershipAssessment o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment> allLegacyLearnershipAssessmentNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyLearnershipAssessment o where o.processed = false";
		return (List<LegacyLearnershipAssessment>) super.getList(hql, numberOfEntries);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment> findByIdNo(String learnerId, Long learnershipID) {
		String hql = "select o from LegacyLearnershipAssessment o where o.learnerId = :learnerId and o.learnership.id = :learnershipID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("learnerId", learnerId);
	    parameters.put("learnershipID", learnershipID);
		return (List<LegacyLearnershipAssessment>)super.getList(hql, parameters);
	}
}

