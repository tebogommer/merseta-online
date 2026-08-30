package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.NqfLevels;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class NqfLevelsDAO.
 */
public class NqfLevelsDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NqfLevels.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception
	 *             the exception
	 * @see NqfLevels
	 */
	@SuppressWarnings("unchecked")
	public List<NqfLevels> allNqfLevels() throws Exception {
		return (List<NqfLevels>) super.getList("select o from NqfLevels o");
	}

	/**
	 * Find nqf levels autocomplete.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<NqfLevels> findNqfLevelsAutocomplete(String description) throws Exception {
		String hql = "select o from NqfLevels o where o.description LIKE :description or o.level = :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "%" + description + "%");
		return (List<NqfLevels>) super.getList(hql, parameters);
	}

	/**
	 * All nqf levels.
	 *
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<NqfLevels> allNqfLevels(Long from, int noRows) throws Exception {
		String hql = "select o from NqfLevels o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<NqfLevels>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return NqfLevels
	 * @throws Exception
	 *             the exception
	 * @see NqfLevels
	 */
	public NqfLevels findByKey(Long id) throws Exception {
		String hql = "select o from NqfLevels o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (NqfLevels) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by level.
	 *
	 * @param id the id
	 * @return the nqf levels
	 * @throws Exception the exception
	 */
	public NqfLevels findByLevel(Integer id) throws Exception {
		String hql = "select o from NqfLevels o where o.level = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (NqfLevels) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NqfLevels by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception
	 *             the exception
	 * @see NqfLevels
	 */
	@SuppressWarnings("unchecked")
	public List<NqfLevels> findByName(String description) throws Exception {
		String hql = "select o from NqfLevels o where o.description like  :description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<NqfLevels>) super.getList(hql, parameters);
	}
	
	/**
	 * Find NqfLevels by code.
	 *
	 * @author TechFinium
	 * @param nqfLevels the nqf levels
	 * @return a {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception             the exception
	 * @see NqfLevels
	 */
	
	public NqfLevels findUniqueCode(NqfLevels nqfLevels) throws Exception {
	 	String hql = "select o from NqfLevels o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (nqfLevels.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", nqfLevels.getId());
	 	}
	    parameters.put("code", nqfLevels.getCode());
		return (NqfLevels)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find NqfLevels by saqaDescription.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a single instance of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception
	 *             the exception
	 * @see NqfLevels
	 */
	public NqfLevels findBySaqaDescription(String saqaDescription) throws Exception {
		String hql = "select o from NqfLevels o where o.saqaQualificationDescription = :saqaDescription";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("saqaDescription", saqaDescription);
		return (NqfLevels) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find NqfLevels by saqaDescription.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a single instance of {@link haj.com.entity.lookup.NqfLevels}
	 * @throws Exception
	 *             the exception
	 * @see NqfLevels
	 */
	public NqfLevels findByCode(String code) throws Exception {
		String hql = "select o from NqfLevels o where o.code = :code";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code.trim());
		return (NqfLevels) super.getUniqueResult(hql, parameters);
	}
	
	public NqfLevels findByPre2009Description(String pre2009Description) throws Exception {
		String hql = "select o from NqfLevels o where o.pre2009Description = :pre2009Description";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("pre2009Description", pre2009Description);
		return (NqfLevels) super.getUniqueResult(hql, parameters);
	}
	
	
	
}
