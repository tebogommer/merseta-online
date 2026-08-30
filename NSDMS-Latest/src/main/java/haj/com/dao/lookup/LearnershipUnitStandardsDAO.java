package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LearnershipUnitStandards;

public class LearnershipUnitStandardsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LearnershipUnitStandards
 	 * @author TechFinium 
 	 * @see    LearnershipUnitStandards
 	 * @return a list of {@link haj.com.entity.LearnershipUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipUnitStandards> allLearnershipUnitStandards() throws Exception {
		return (List<LearnershipUnitStandards>)super.getList("select o from LearnershipUnitStandards o");
	}

	/**
	 * Get all LearnershipUnitStandards between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LearnershipUnitStandards
 	 * @return a list of {@link haj.com.entity.LearnershipUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipUnitStandards> allLearnershipUnitStandards(Long from, int noRows) throws Exception {
	 	String hql = "select o from LearnershipUnitStandards o " ;
	    Map<String, Object> parameters = new HashMap<>();
	    
		return (List<LearnershipUnitStandards>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LearnershipUnitStandards
 	 * @return a {@link haj.com.entity.LearnershipUnitStandards}
 	 * @throws Exception global exception
 	 */
	public LearnershipUnitStandards findByKey(Long id) throws Exception {
	 	String hql = "select o from LearnershipUnitStandards o where o.id = :id " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		return (LearnershipUnitStandards)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LearnershipUnitStandards by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LearnershipUnitStandards
  	 * @return a list of {@link haj.com.entity.LearnershipUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LearnershipUnitStandards> findByName(String description) throws Exception {
	 	String hql = "select o from LearnershipUnitStandards o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LearnershipUnitStandards>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LearnershipUnitStandards> findByLearnership(Long learnershipId) throws Exception {
	 	String hql = "select o from LearnershipUnitStandards o where o.learnership.id =  :learnershipId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("learnershipId", learnershipId);
		return (List<LearnershipUnitStandards>)super.getList(hql, parameters);
	}
	
	public Integer countByUnitStandardAndLearnership(Long unitStandardId, Long learnershipId) throws Exception {
	 	String hql = "select count(o) from LearnershipUnitStandards o where o.learnership.id =  :learnershipId and o.unitStandards.id = :unitStandardId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("learnershipId", learnershipId);
	    parameters.put("unitStandardId", unitStandardId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

