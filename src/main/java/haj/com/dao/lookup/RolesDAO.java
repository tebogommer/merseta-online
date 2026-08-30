package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class RolesDAO.
 */
public class RolesDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Roles.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception global exception
	 * @see    Roles
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> allRoles() throws Exception {
		return (List<Roles>)super.getList("select o from Roles o");
	}

	/**
	 * Get all Roles between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception global exception
	 * @see    Roles
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> allRoles(Long from, int noRows) throws Exception {
	 	String hql = "select o from Roles o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Roles>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Roles}
	 * @throws Exception global exception
	 * @see    Roles
	 */
	public Roles findByKey(Long id) throws Exception {
	 	String hql = "select o from Roles o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Roles)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Roles by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception global exception
	 * @see    Roles
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> findByName(String description) throws Exception {
	 	String hql = "select o from Roles o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Roles>)super.getList(hql, parameters);
	}
	
	/**
	 * Get all Roles between from and noRows.
	 *
	 * @author TechFinium
	 * @param hostingCompanyProcess the hosting company process
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception global exception
	 * @see    Roles
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> allRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
//	 	String hql = "select o from Roles o where o.id not in (select h.roles.id from ProcessRoles h where h.hostingCompanyProcess.id = :hostingCompanyProcessID)" ;
		String hql = "select o from Roles o";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
//	    parameters.put("hostingCompanyProcessID", hostingCompanyProcess.getId());
		return (List<Roles>)super.getList(hql, parameters);
	}
	
	/**
	 * Find Roles by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param roles the roles
	 * @return the roles
	 * @throws Exception the exception
	 */
	public Roles findUniqueCode(Roles roles) throws Exception {
	 	String hql = "select o from Roles o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (roles.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", roles.getId());
	 	}
	   
	    parameters.put("code", roles.getCode());
		return (Roles)super.getUniqueResult(hql, parameters);
	}
}

