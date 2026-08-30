package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Subdomain;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SubdomainDAO.
 */
public class SubdomainDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Subdomain.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Subdomain}
	 * @throws Exception global exception
	 * @see    Subdomain
	 */
	@SuppressWarnings("unchecked")
	public List<Subdomain> allSubdomain() throws Exception {
		return (List<Subdomain>)super.getList("select o from Subdomain o");
	}

	/**
	 * Get all Subdomain between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Subdomain}
	 * @throws Exception global exception
	 * @see    Subdomain
	 */
	@SuppressWarnings("unchecked")
	public List<Subdomain> allSubdomain(Long from, int noRows) throws Exception {
	 	String hql = "select o from Subdomain o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Subdomain>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Subdomain}
	 * @throws Exception global exception
	 * @see    Subdomain
	 */
	public Subdomain findByKey(Long id) throws Exception {
	 	String hql = "select o from Subdomain o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Subdomain)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Subdomain by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Subdomain}
	 * @throws Exception global exception
	 * @see    Subdomain
	 */
	@SuppressWarnings("unchecked")
	public List<Subdomain> findByName(String description) throws Exception {
	 	String hql = "select o from Subdomain o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Subdomain>)super.getList(hql, parameters);
	}
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param subdomain the subdomain
	 * @return a {@link haj.com.entity.Subdomain}
	 * @throws Exception global exception
	 * @see    Subdomain
	 */
    public Subdomain findUniqueCode(Subdomain subdomain) throws Exception {
	 	String hql = "select o from Subdomain o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (subdomain.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", subdomain.getId());
	 	}
	   
	    parameters.put("code", subdomain.getCode());
		return (Subdomain)super.getUniqueResult(hql, parameters);
	}
}

