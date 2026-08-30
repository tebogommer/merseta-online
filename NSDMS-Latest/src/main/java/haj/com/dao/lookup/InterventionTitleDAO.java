package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.InterventionTitle;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InterventionTitleDAO.
 */
public class InterventionTitleDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all InterventionTitle.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.InterventionTitle}
	 * @throws Exception global exception
	 * @see    InterventionTitle
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionTitle> allInterventionTitle() throws Exception {
		return (List<InterventionTitle>)super.getList("select o from InterventionTitle o");
	}

	/**
	 * Get all InterventionTitle between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.InterventionTitle}
	 * @throws Exception global exception
	 * @see    InterventionTitle
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionTitle> allInterventionTitle(Long from, int noRows) throws Exception {
	 	String hql = "select o from InterventionTitle o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<InterventionTitle>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.InterventionTitle}
	 * @throws Exception global exception
	 * @see    InterventionTitle
	 */
	public InterventionTitle findByKey(Long id) throws Exception {
	 	String hql = "select o from InterventionTitle o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (InterventionTitle)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find InterventionTitle by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.InterventionTitle}
	 * @throws Exception global exception
	 * @see    InterventionTitle
	 */
	@SuppressWarnings("unchecked")
	public List<InterventionTitle> findByName(String description) throws Exception {
	 	String hql = "select o from InterventionTitle o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<InterventionTitle>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the intervention type
	 * @throws Exception the exception
	 */
	public InterventionTitle findByCode(String code) throws Exception {
		String hql = "select o from InterventionTitle o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (InterventionTitle) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find object by  code.
	 *
	 * @author TechFinium
	 * @param interventionTitle the intervention type
	 * @return a {@link haj.com.entity.InterventionTitle}
	 * @throws Exception global exception
	 * @see    InterventionTitle
	 */
	public InterventionTitle findUniqueCode(InterventionTitle interventionTitle) throws Exception {
	 	String hql = "select o from InterventionTitle o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (interventionTitle.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", interventionTitle.getId());
	 	}
	   
	    parameters.put("code", interventionTitle.getCode());
		return (InterventionTitle)super.getUniqueResult(hql, parameters);
	}
	
}

