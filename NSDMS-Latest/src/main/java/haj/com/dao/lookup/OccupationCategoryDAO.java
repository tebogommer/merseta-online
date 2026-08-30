package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.OccupationCategory;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class OccupationCategoryDAO.
 */
public class OccupationCategoryDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all OccupationCategory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.OccupationCategory}
	 * @throws Exception global exception
	 * @see    OccupationCategory
	 */
	@SuppressWarnings("unchecked")
	public List<OccupationCategory> allOccupationCategory() throws Exception {
		return (List<OccupationCategory>)super.getList("select o from OccupationCategory o");
	}

	/**
	 * Get all OccupationCategory between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.OccupationCategory}
	 * @throws Exception global exception
	 * @see    OccupationCategory
	 */
	@SuppressWarnings("unchecked")
	public List<OccupationCategory> allOccupationCategory(Long from, int noRows) throws Exception {
	 	String hql = "select o from OccupationCategory o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<OccupationCategory>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.OccupationCategory}
	 * @throws Exception global exception
	 * @see    OccupationCategory
	 */
	public OccupationCategory findByKey(Long id) throws Exception {
	 	String hql = "select o from OccupationCategory o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (OccupationCategory)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the occupation category
	 * @throws Exception the exception
	 */
	public OccupationCategory findByCode(String code) throws Exception {
	 	String hql = "select o from OccupationCategory o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (OccupationCategory)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find OccupationCategory by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.OccupationCategory}
	 * @throws Exception global exception
	 * @see    OccupationCategory
	 */
	@SuppressWarnings("unchecked")
	public List<OccupationCategory> findByName(String description) throws Exception {
	 	String hql = "select o from OccupationCategory o where o.description like  :description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<OccupationCategory>)super.getList(hql, parameters);
	}
	
	/**
	 * Find OccupationCategory by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param occupationCategory the occupation category
	 * @return the occupation category
	 * @throws Exception the exception
	 */
	public OccupationCategory findUniqueCode(OccupationCategory occupationCategory) throws Exception {
	 	String hql = "select o from OccupationCategory o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (occupationCategory.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", occupationCategory.getId());
	 	}
	   
	    parameters.put("code", occupationCategory.getCode());
		return (OccupationCategory)super.getUniqueResult(hql, parameters);
	}
}

