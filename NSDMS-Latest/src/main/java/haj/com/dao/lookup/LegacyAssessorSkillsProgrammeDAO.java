package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyAssessorSkillsProgrammeDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyAssessorSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgramme() throws Exception {
		return (List<LegacyAssessorSkillsProgramme>) super.getList("select o from LegacyAssessorSkillsProgramme o");
	}

	/**
	 * Get all LegacyAssessorSkillsProgramme between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacyAssessorSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgramme(Long from, int noRows)
			throws Exception {
		String hql = "select o from LegacyAssessorSkillsProgramme o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacyAssessorSkillsProgramme>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacyAssessorSkillsProgramme
	 * @return a {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception global exception
	 */
	public LegacyAssessorSkillsProgramme findByKey(Long id) throws Exception {
		String hql = "select o from LegacyAssessorSkillsProgramme o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacyAssessorSkillsProgramme) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyAssessorSkillsProgramme by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacyAssessorSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> findByName(String description) throws Exception {
		String hql = "select o from LegacyAssessorSkillsProgramme o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacyAssessorSkillsProgramme>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacyAssessorSkillsProgramme o")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> findByAssessorIdNumber(String idNumber) throws Exception {
	 	String hql = "select o from LegacyAssessorSkillsProgramme o where o.assessorId = :assessorid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorid", idNumber.trim());
		return (List<LegacyAssessorSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> findByAssessorRegNo(String assessorRegNo) throws Exception {
	 	String hql = "select o from LegacyAssessorSkillsProgramme o where o.assessorRegNo = :assessorRegNo and o.skillsProgram is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorRegNo", assessorRegNo.trim());
		return (List<LegacyAssessorSkillsProgramme>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> findByAssessorIdNumberRegNo(String entry) throws Exception {
	 	String hql = "select o from LegacyAssessorSkillsProgramme o where (o.assessorRegNo = :entry or o.assessorId = :entry)";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("entry", entry.trim());
		return (List<LegacyAssessorSkillsProgramme>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyAssessorSkillsProgrammeNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyAssessorSkillsProgramme o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyAssessorSkillsProgramme o where o.processed = false";
		return (List<LegacyAssessorSkillsProgramme>) super.getList(hql, numberOfEntries);
	}
}
