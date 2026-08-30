package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.OrganisedLabourUnion;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class OrganisedLabourUnionDAO.
 */
public class OrganisedLabourUnionDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all OrganisedLabourUnion.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.OrganisedLabourUnion}
	 * @throws Exception global exception
	 * @see    OrganisedLabourUnion
	 */
	@SuppressWarnings("unchecked")
	public List<OrganisedLabourUnion> allOrganisedLabourUnion() throws Exception {
		return (List<OrganisedLabourUnion>)super.getList("select o from OrganisedLabourUnion o");
	}

	/**
	 * Get all OrganisedLabourUnion between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.OrganisedLabourUnion}
	 * @throws Exception global exception
	 * @see    OrganisedLabourUnion
	 */
	@SuppressWarnings("unchecked")
	public List<OrganisedLabourUnion> allOrganisedLabourUnion(Long from, int noRows) throws Exception {
	 	String hql = "select o from OrganisedLabourUnion o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<OrganisedLabourUnion>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.OrganisedLabourUnion}
	 * @throws Exception global exception
	 * @see    OrganisedLabourUnion
	 */
	public OrganisedLabourUnion findByKey(Long id) throws Exception {
	 	String hql = "select o from OrganisedLabourUnion o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (OrganisedLabourUnion)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find OrganisedLabourUnion by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.OrganisedLabourUnion}
	 * @throws Exception global exception
	 * @see    OrganisedLabourUnion
	 */
	@SuppressWarnings("unchecked")
	public List<OrganisedLabourUnion> findByName(String description) throws Exception {
	 	String hql = "select o from OrganisedLabourUnion o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<OrganisedLabourUnion>)super.getList(hql, parameters);
	}
}

