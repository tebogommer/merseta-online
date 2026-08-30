package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.InterventionLevel;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionLevelDAO.
 */
public class InterventionLevelDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all InterventionLevel.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterventionLevel}
	 * @throws Exception global exception
	 * @see    InterventionLevel
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionLevel> allInterventionLevel() throws Exception {
		return (List<InterventionLevel>)super.getList("select o from InterventionLevel o");
	}

	/**
	 * Get all InterventionLevel between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.InterventionLevel}
	 * @throws Exception global exception
	 * @see    InterventionLevel
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionLevel> allInterventionLevel(Long from, int noRows) throws Exception {
	 	String hql = "select o from InterventionLevel o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<InterventionLevel>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InterventionLevel}
	 * @throws Exception global exception
	 * @see    InterventionLevel
	 */
	public InterventionLevel findByKey(Long id) throws Exception {
	 	String hql = "select o from InterventionLevel o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (InterventionLevel)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the intervention level
	 * @throws Exception the exception
	 */
	public InterventionLevel findByCode(String code) throws Exception {
	 	String hql = "select o from InterventionLevel o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (InterventionLevel)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InterventionLevel by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.InterventionLevel}
	 * @throws Exception global exception
	 * @see    InterventionLevel
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionLevel> findByName(String description) throws Exception {
	 	String hql = "select o from InterventionLevel o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<InterventionLevel>)super.getList(hql, parameters);
	}
}

