package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;

public class LegacySkillsProgrammeAssessmentsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacySkillsProgrammeAssessments
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessments() throws Exception {
		return (List<LegacySkillsProgrammeAssessments>) super.getList(
				"select o from LegacySkillsProgrammeAssessments o");
	}

	/**
	 * Get all LegacySkillsProgrammeAssessments between from and noRows
	 * 
	 * @author TechFinium
	 * @param from   the from
	 * @param noRows the no rows
	 * @see LegacySkillsProgrammeAssessments
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessments(Long from, int noRows)
			throws Exception {
		String hql = "select o from LegacySkillsProgrammeAssessments o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<LegacySkillsProgrammeAssessments>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id the id
	 * @see LegacySkillsProgrammeAssessments
	 * @return a {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception global exception
	 */
	public LegacySkillsProgrammeAssessments findByKey(Long id) throws Exception {
		String hql = "select o from LegacySkillsProgrammeAssessments o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (LegacySkillsProgrammeAssessments) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacySkillsProgrammeAssessments by description
	 * 
	 * @author TechFinium
	 * @param description the description
	 * @see LegacySkillsProgrammeAssessments
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception global exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgrammeAssessments> findByName(String description) throws Exception {
		String hql = "select o from LegacySkillsProgrammeAssessments o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<LegacySkillsProgrammeAssessments>) super.getList(hql, parameters);
	}

	public Integer countAllResults() throws Exception {
		return ((Long) super.getUniqueResult("select count(o) from LegacySkillsProgrammeAssessments o")).intValue();
	}
	
	public Integer countAllLegacySkillsProgrammeAssessmentsNotProcessed() throws Exception {
		String hql = "select count(o) from LegacySkillsProgrammeAssessments o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessmentsNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacySkillsProgrammeAssessments o where o.processed = false";
		return (List<LegacySkillsProgrammeAssessments>) super.getList(hql, numberOfEntries);
	}

	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgrammeAssessments> findByIdNo(String idNo, String sProgrammeCode) {
		String hql = "select o from LegacySkillsProgrammeAssessments o where o.idNo = :idNo and o.sProgrammeCode = :sProgrammeCode";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("idNo", idNo);
		parameters.put("sProgrammeCode", sProgrammeCode);
		return (List<LegacySkillsProgrammeAssessments>) super.getList(hql, parameters);
	}
}
