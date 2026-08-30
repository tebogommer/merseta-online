package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.SkillsRegistrationQualificationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsRegistrationQualificationUnitStandardsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsRegistrationQualificationUnitStandards
	 * 
	 * @author TechFinium
	 * @see SkillsRegistrationQualificationUnitStandards
	 * @return a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards() throws Exception {
		return (List<SkillsRegistrationQualificationUnitStandards>) super.getList("select o from SkillsRegistrationQualificationUnitStandards o");
	}

	/**
	 * Get all SkillsRegistrationQualificationUnitStandards between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SkillsRegistrationQualificationUnitStandards
	 * @return a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards(Long from, int noRows) throws Exception {
		String hql = "select o from SkillsRegistrationQualificationUnitStandards o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SkillsRegistrationQualificationUnitStandards>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> allUnitStandards(Long skillsRegistrationID) throws Exception {
		String hql = "select o.unitStandards from SkillsRegistrationQualificationUnitStandards o where o.skillsRegistration.id = :skillsRegistrationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsRegistrationID", skillsRegistrationID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards(Long skillsRegistrationID) throws Exception {
		String hql = "select o from SkillsRegistrationQualificationUnitStandards o where o.skillsRegistration.id = :skillsRegistrationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsRegistrationID", skillsRegistrationID);
		return (List<SkillsRegistrationQualificationUnitStandards>) super.getList(hql, parameters);
	}


	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SkillsRegistrationQualificationUnitStandards
	 * @return a {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	public SkillsRegistrationQualificationUnitStandards findByKey(Long id) throws Exception {
		String hql = "select o from SkillsRegistrationQualificationUnitStandards o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SkillsRegistrationQualificationUnitStandards) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsRegistrationQualificationUnitStandards by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SkillsRegistrationQualificationUnitStandards
	 * @return a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationQualificationUnitStandards> findByName(String description) throws Exception {
		String hql = "select o from SkillsRegistrationQualificationUnitStandards o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SkillsRegistrationQualificationUnitStandards>) super.getList(hql, parameters);
	}
}
