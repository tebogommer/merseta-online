package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Nationality;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class NationalityDAO.
 */
public class NationalityDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Nationality.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Nationality}
	 * @throws Exception global exception
	 * @see    Nationality
	 */
	@SuppressWarnings("unchecked")
	public List<Nationality> allNationality() throws Exception {
		return (List<Nationality>)super.getList("select o from Nationality o");
	}

	/**
	 * Get all Nationality between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Nationality}
	 * @throws Exception global exception
	 * @see    Nationality
	 */
	@SuppressWarnings("unchecked")
	public List<Nationality> allNationality(Long from, int noRows) throws Exception {
	 	String hql = "select o from Nationality o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Nationality>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Nationality}
	 * @throws Exception global exception
	 * @see    Nationality
	 */
	public Nationality findByKey(Long id) throws Exception {
	 	String hql = "select o from Nationality o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Nationality)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the nationality
	 * @throws Exception the exception
	 */
	public Nationality findByCode(String code) throws Exception {
	 	String hql = "select o from Nationality o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (Nationality)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Nationality by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Nationality}
	 * @throws Exception global exception
	 * @see    Nationality
	 */
	@SuppressWarnings("unchecked")
	public List<Nationality> findByName(String description) throws Exception {
	 	String hql = "select o from Nationality o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Nationality>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Nationality> findByNameExcludeNonSelectable(String description, Boolean canSelect) throws Exception {
	 	String hql = "select o from Nationality o where o.description like  :description and o.canSelect = :canSelect order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
	    parameters.put("canSelect", canSelect);
		return (List<Nationality>)super.getList(hql, parameters);
	}
	
	/**
	 * Find Nationality by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param nationality the nationality
	 * @return the nationality
	 * @throws Exception the exception
	 */
	public Nationality findUniqueCode(Nationality nationality) throws Exception {
	 	String hql = "select o from Nationality o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (nationality.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", nationality.getId());
	 	}
	   
	    parameters.put("code", nationality.getCode());
		return (Nationality)super.getUniqueResult(hql, parameters);
	}
}

