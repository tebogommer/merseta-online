package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsRegistrationUnitStandardsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsRegistrationUnitStandards
	 * 
	 * @author TechFinium
	 * @see SkillsRegistrationUnitStandards
	 * @return a list of {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards() throws Exception {
		return (List<SkillsRegistrationUnitStandards>) super.getList("select o from SkillsRegistrationUnitStandards o");
	}

	/**
	 * Get all SkillsRegistrationUnitStandards between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SkillsRegistrationUnitStandards
	 * @return a list of {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards(Long from, int noRows) throws Exception {
		String hql = "select o from SkillsRegistrationUnitStandards o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SkillsRegistrationUnitStandards>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> allUnitStandards(Long skillsRegistrationID) throws Exception {
		String hql = "select o.unitStandards from SkillsRegistrationUnitStandards o where o.skillsRegistration.id = :skillsRegistrationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsRegistrationID", skillsRegistrationID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	

	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards(Long skillsRegistrationID) throws Exception {
		String hql = "select o from SkillsRegistrationUnitStandards o where o.skillsRegistration.id = :skillsRegistrationID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsRegistrationID", skillsRegistrationID);
		return (List<SkillsRegistrationUnitStandards>) super.getList(hql, parameters);
	}


	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SkillsRegistrationUnitStandards
	 * @return a {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	public SkillsRegistrationUnitStandards findByKey(Long id) throws Exception {
		String hql = "select o from SkillsRegistrationUnitStandards o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SkillsRegistrationUnitStandards) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsRegistrationUnitStandards by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SkillsRegistrationUnitStandards
	 * @return a list of {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationUnitStandards> findByName(String description) throws Exception {
		String hql = "select o from SkillsRegistrationUnitStandards o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<SkillsRegistrationUnitStandards>) super.getList(hql, parameters);
	}
}
