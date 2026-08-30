package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Subfield;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SubfieldDAO.
 */
public class SubfieldDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Subfield.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Subfield}
	 * @throws Exception global exception
	 * @see    Subfield
	 */
	@SuppressWarnings("unchecked")
	public List<Subfield> allSubfield() throws Exception {
		return (List<Subfield>)super.getList("select o from Subfield o");
	}

	/**
	 * Get all Subfield between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Subfield}
	 * @throws Exception global exception
	 * @see    Subfield
	 */
	@SuppressWarnings("unchecked")
	public List<Subfield> allSubfield(Long from, int noRows) throws Exception {
	 	String hql = "select o from Subfield o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Subfield>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Subfield}
	 * @throws Exception global exception
	 * @see    Subfield
	 */
	public Subfield findByKey(Long id) throws Exception {
	 	String hql = "select o from Subfield o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Subfield)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Subfield by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Subfield}
	 * @throws Exception global exception
	 * @see    Subfield
	 */
	@SuppressWarnings("unchecked")
	public List<Subfield> findByName(String description) throws Exception {
	 	String hql = "select o from Subfield o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Subfield>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Subfield> findByDescriptionList(String description) throws Exception {
	 	String hql = "select o from Subfield o where Upper(o.description) = :description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description.trim().toUpperCase());
		return (List<Subfield>)super.getList(hql, parameters);
	}
	
	public Subfield findByDescription(String description) throws Exception {
	 	String hql = "select o from Subfield o where Upper(o.description) = :description" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description.trim().toUpperCase());
		return (Subfield)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param subfield the subfield
	 * @return the subfield
	 * @throws Exception the exception
	 */
	public Subfield findUniqueCode(Subfield subfield) throws Exception {
	 	String hql = "select o from Subfield o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (subfield.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", subfield.getId());
	 	}
	   
	    parameters.put("code", subfield.getCode());
		return (Subfield)super.getUniqueResult(hql, parameters);
	}
}

