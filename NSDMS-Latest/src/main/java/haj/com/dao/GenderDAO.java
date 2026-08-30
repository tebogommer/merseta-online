package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Gender;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class GenderDAO.
 */
public class GenderDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Gender.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.Gender}
	 * @throws Exception             global exception
	 * @see Gender
	 */
	@SuppressWarnings("unchecked")
	public List<Gender> allGender() throws Exception {
		return (List<Gender>) super.getList("select o from Gender o");
	}

	/**
	 * Get all Gender between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.lookup.Gender}
	 * @throws Exception             global exception
	 * @see Gender
	 */
	@SuppressWarnings("unchecked")
	public List<Gender> allGender(Long from, int noRows) throws Exception {
		String hql = "select o from Gender o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<Gender>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.lookup.Gender}
	 * @throws Exception             global exception
	 * @see Gender
	 */
	public Gender findByKey(Long id) throws Exception {
		String hql = "select o from Gender o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Gender) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the gender
	 * @throws Exception the exception
	 */
	public Gender findByCode(String code) throws Exception {
		String hql = "select o from Gender o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Gender) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Gender by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.lookup.Gender}
	 * @throws Exception             global exception
	 * @see Gender
	 */
	@SuppressWarnings("unchecked")
	public List<Gender> findByGenderName(String description) throws Exception {
		String hql = "select o from Gender o where o.genderName like  :description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description.trim() + "%");
		return (List<Gender>) super.getList(hql, parameters);
	}
}
