package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyAssessorQualificationDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyAssessorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorQualification
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> allLegacyAssessorQualification() throws Exception {
		return (List<LegacyAssessorQualification>) super.getList("select o from LegacyAssessorQualification o");
	}

	/**
	 * Get all LegacyAssessorQualification between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacyAssessorQualification
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> allLegacyAssessorQualification(Long from, int noRows) throws Exception {
		String hql = "select o from LegacyAssessorQualification o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyAssessorQualification>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacyAssessorQualification
	 * @return a {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception global exception
	 */
	public LegacyAssessorQualification findByKey(Long id) throws Exception {
		String hql = "select o from LegacyAssessorQualification o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyAssessorQualification) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyAssessorQualification by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacyAssessorQualification
	 * @return a list of {@link haj.com.entity.LegacyAssessorQualification}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> findByName(String description) throws Exception {
		String hql = "select o from LegacyAssessorQualification o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyAssessorQualification>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyAssessorQualification o")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> findByAssessorIdNumber(String idNumber) throws Exception {
	 	String hql = "select o from LegacyAssessorQualification o where o.assessorId = :assessorid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorid", idNumber.trim());
		return (List<LegacyAssessorQualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> findByAssessorRegNo(String assessorRegNo) throws Exception {
	 	String hql = "select o from LegacyAssessorQualification o where o.assessorRegNo = :assessorRegNo and o.qualification is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorRegNo", assessorRegNo.trim());
		return (List<LegacyAssessorQualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> findByAssessorIdNumberRegNo(String entry) throws Exception {
	 	String hql = "select o from LegacyAssessorQualification o where (o.assessorRegNo = :entry or o.assessorId = :entry)";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("entry", entry.trim());
		return (List<LegacyAssessorQualification>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyAssessorQualificationNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyAssessorQualification o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyAssessorQualification> allLegacyAssessorQualificationNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyAssessorQualification o where o.processed = false";
		return (List<LegacyAssessorQualification>) super.getList(hql, numberOfEntries);
	}
}
