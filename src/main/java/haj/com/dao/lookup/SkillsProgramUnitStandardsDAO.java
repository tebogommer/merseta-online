package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsProgramUnitStandardsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsProgramUnitStandards
 	 * @author TechFinium 
 	 * @see    SkillsProgramUnitStandards
 	 * @return a list of {@link haj.com.entity.SkillsProgramUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards() throws Exception {
		return (List<SkillsProgramUnitStandards>)super.getList("select o from SkillsProgramUnitStandards o");
	}

	/**
	 * Get all SkillsProgramUnitStandards between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SkillsProgramUnitStandards
 	 * @return a list of {@link haj.com.entity.SkillsProgramUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards(Long from, int noRows) throws Exception {
	 	String hql = "select o from SkillsProgramUnitStandards o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SkillsProgramUnitStandards>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SkillsProgramUnitStandards
 	 * @return a {@link haj.com.entity.SkillsProgramUnitStandards}
 	 * @throws Exception global exception
 	 */
	public SkillsProgramUnitStandards findByKey(Long id) throws Exception {
	 	String hql = "select o from SkillsProgramUnitStandards o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SkillsProgramUnitStandards)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsProgramUnitStandards by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SkillsProgramUnitStandards
  	 * @return a list of {@link haj.com.entity.SkillsProgramUnitStandards}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> findByName(String description) throws Exception {
	 	String hql = "select o from SkillsProgramUnitStandards o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SkillsProgramUnitStandards>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> findBySkillsProgramKey(Long skillsProgramID) throws Exception {
		String hql = "select o from SkillsProgramUnitStandards o where o.skillsProgram.id = :skillsProgramID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsProgramID", skillsProgramID);
		return (List<SkillsProgramUnitStandards>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> findBySkillsProgramKey(Long skillsProgramID, Long summativeAssessmentReportID) throws Exception {
		String hql = "select o from SkillsProgramUnitStandards o where o.skillsProgram.id = :skillsProgramID and o.unitStandards.id not in(select x.unitStandards.id from SummativeAssessmentReportUnitStandards x where x.summativeAssessmentReport.id = :summativeAssessmentReportID)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsProgramID", skillsProgramID);
	    parameters.put("summativeAssessmentReportID", summativeAssessmentReportID);
		return (List<SkillsProgramUnitStandards>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findUnitStandardsBySkillsProgrammeId(Long skillsProgramID) throws Exception {
		String hql = "select o.unitStandards from SkillsProgramUnitStandards o where o.skillsProgram.id = :skillsProgramID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("skillsProgramID", skillsProgramID);
		return (List<UnitStandards>)super.getList(hql, parameters);
	}
}

