package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Institution;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class InstitutionDAO.
 */
public class InstitutionDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Institution.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Institution}
	 * @throws Exception global exception
	 * @see    Institution
	 */
	@SuppressWarnings("unchecked")
	public List<Institution> allInstitution() throws Exception {
		return (List<Institution>)super.getList("select o from Institution o");
	}

	/**
	 * Get all Institution between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Institution}
	 * @throws Exception global exception
	 * @see    Institution
	 */
	@SuppressWarnings("unchecked")
	public List<Institution> allInstitution(Long from, int noRows) throws Exception {
	 	String hql = "select o from Institution o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Institution>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Institution}
	 * @throws Exception global exception
	 * @see    Institution
	 */
	public Institution findByKey(Long id) throws Exception {
	 	String hql = "select o from Institution o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Institution)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Institution by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Institution}
	 * @throws Exception global exception
	 * @see    Institution
	 */
	@SuppressWarnings("unchecked")
	public List<Institution> findByName(String description) throws Exception {
	 	String hql = "select o from Institution o where o.description like  :description order by o.description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Institution>)super.getList(hql, parameters);
	}
}

