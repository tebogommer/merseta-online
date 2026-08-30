package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.OrganisationType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class OrganisationTypeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all OrganisationType
 	 * @author TechFinium 
 	 * @see    OrganisationType
 	 * @return a list of {@link haj.com.entity.OrganisationType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OrganisationType> allOrganisationType() throws Exception {
		return (List<OrganisationType>)super.getList("select o from OrganisationType o");
	}

	/**
	 * Get all OrganisationType between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    OrganisationType
 	 * @return a list of {@link haj.com.entity.OrganisationType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OrganisationType> allOrganisationType(Long from, int noRows) throws Exception {
	 	String hql = "select o from OrganisationType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<OrganisationType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    OrganisationType
 	 * @return a {@link haj.com.entity.OrganisationType}
 	 * @throws Exception global exception
 	 */
	public OrganisationType findByKey(Long id) throws Exception {
	 	String hql = "select o from OrganisationType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (OrganisationType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find OrganisationType by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    OrganisationType
  	 * @return a list of {@link haj.com.entity.OrganisationType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<OrganisationType> findByName(String description) throws Exception {
	 	String hql = "select o from OrganisationType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<OrganisationType>)super.getList(hql, parameters);
	}
}

