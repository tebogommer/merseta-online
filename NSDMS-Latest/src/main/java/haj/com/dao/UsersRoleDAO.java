package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.HostingCompany;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Tasks;
import haj.com.entity.UsersRole;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersRoleDAO.
 */
public class UsersRoleDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UsersRole.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UsersRole}
	 * @throws Exception global exception
	 * @see    UsersRole
	 */
	@SuppressWarnings("unchecked")
	public List<UsersRole> allUsersRole() throws Exception {
		return (List<UsersRole>)super.getList("select o from UsersRole o");
	}

	/**
	 * Get all UsersRole between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.UsersRole}
	 * @throws Exception global exception
	 * @see    UsersRole
	 */
	@SuppressWarnings("unchecked")
	public List<UsersRole> allUsersRole(Long from, int noRows) throws Exception {
	 	String hql = "select o from UsersRole o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UsersRole>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersRole}
	 * @throws Exception global exception
	 * @see    UsersRole
	 */
	public UsersRole findByKey(Long id) throws Exception {
	 	String hql = "select o from UsersRole o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UsersRole)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UsersRole by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.UsersRole}
	 * @throws Exception global exception
	 * @see    UsersRole
	 */
	@SuppressWarnings("unchecked")
	public List<UsersRole> findByName(String description) throws Exception {
	 	String hql = "select o from UsersRole o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UsersRole>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by process.
	 *
	 * @param processRoles the process roles
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersRole> findByProcess(ProcessRoles processRoles) throws Exception {
	 	String hql = "select o from UsersRole o left join fetch o.users where o.processRoles.id = :processRolesID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesID", processRoles.getId());
		return (List<UsersRole>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersRole> findByProcess(ProcessRoles processRoles, Tasks task) throws Exception {
//		System.out.println("processRolesID---"+processRoles.getId());
//		System.out.println("taskID---"+task.getId());
	 	String hql = "select o from UsersRole o left join fetch o.users where o.processRoles.id = :processRolesID and o.users.id not in (select x.user.id from TaskUsers x where x.task.id = :taskID)" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("processRolesID", processRoles.getId());
	    parameters.put("taskID", task.getId());
		return (List<UsersRole>)super.getList(hql, parameters);
	}
	
	/**
	 * Find by process hosting company.
	 *
	 * @param hostingCompany the hosting company
	 * @param processEnum the process enum
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<UsersRole> findByProcessHostingCompany(HostingCompany hostingCompany, ConfigDocProcessEnum processEnum) throws Exception {
	 	String hql = "select o from UsersRole o "
	 			+ "left join fetch o.users "
	 			+ "left join fetch o.processRoles p "
	 			+ "left join fetch p.hostingCompanyProcess hcp "
	 			+ "where hcp.hostingCompany.id = :hostingCompanyID "
	 			+ "and hcp.workflowProcess = :processEnum";
	 	
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("hostingCompanyID", hostingCompany.getId());
	    parameters.put("processEnum", processEnum);
		return (List<UsersRole>)super.getList(hql, parameters);
	}
	
	/**
	 * Find dashboard.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<String> findDashboard(Long userId) throws Exception {
		String hql = "select distinct(o.processRoles.roles.dashboard) from  UsersRole o " + 
				"where o.users.id = :userId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return ( List<String>)super.getList(hql, parameters);
	}
	
	/**
	 * Find roles.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Roles> findRoles(Long userId) throws Exception {
		String hql = "select o.processRoles.roles from  UsersRole o " + 
				"where o.users.id = :userId";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return ( List<Roles>)super.getList(hql, parameters);
	}
	
}

