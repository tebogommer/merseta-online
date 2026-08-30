package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;

public class LegacyAssessorLearnershipDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyAssessorLearnership
 	 * @author TechFinium 
 	 * @see    LegacyAssessorLearnership
 	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> allLegacyAssessorLearnership() throws Exception {
		return (List<LegacyAssessorLearnership>)super.getList("select o from LegacyAssessorLearnership o");
	}

	/**
	 * Get all LegacyAssessorLearnership between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyAssessorLearnership
 	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> allLegacyAssessorLearnership(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyAssessorLearnership o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyAssessorLearnership>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyAssessorLearnership
 	 * @return a {@link haj.com.entity.LegacyAssessorLearnership}
 	 * @throws Exception global exception
 	 */
	public LegacyAssessorLearnership findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyAssessorLearnership o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyAssessorLearnership)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyAssessorLearnership by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyAssessorLearnership
  	 * @return a list of {@link haj.com.entity.LegacyAssessorLearnership}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyAssessorLearnership o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyAssessorLearnership>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyAssessorLearnership o")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> findByAssessorIdNumber(String idNumber) throws Exception {
	 	String hql = "select o from LegacyAssessorLearnership o where o.assessorId = :assessorid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorid", idNumber.trim());
		return (List<LegacyAssessorLearnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> findByAssessorRegNo(String assessorRegNo) throws Exception {
	 	String hql = "select o from LegacyAssessorLearnership o where o.assessorRegNo = :assessorRegNo and o.learnership is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorRegNo", assessorRegNo.trim());
		return (List<LegacyAssessorLearnership>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorLearnership> findByAssessorIdNumberRegNo(String entry) throws Exception {
	 	String hql = "select o from LegacyAssessorLearnership o where (o.assessorRegNo = :entry or o.assessorId = :entry)";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("entry", entry.trim());
		return (List<LegacyAssessorLearnership>)super.getList(hql, parameters);
	}
}

