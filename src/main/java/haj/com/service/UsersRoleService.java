package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.primefaces.model.SortOrder;

import haj.com.dao.UsersRoleDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.UsersRole;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersRoleService.
 */
public class UsersRoleService extends AbstractService {
	/** The dao. */
	private UsersRoleDAO dao = new UsersRoleDAO();

	/**
	 * Get all UsersRole.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UsersRole}
	 * @throws Exception the exception
	 * @see   UsersRole
	 */
	public List<UsersRole> allUsersRole() throws Exception {
	  	return dao.allUsersRole();
	}


	/**
	 * Create or update UsersRole.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UsersRole
	 */
	public void create(UsersRole entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UsersRole.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersRole
	 */
	public void update(UsersRole entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UsersRole.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersRole
	 */
	public void delete(UsersRole entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersRole}
	 * @throws Exception the exception
	 * @see    UsersRole
	 */
	public UsersRole findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UsersRole by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UsersRole}
	 * @throws Exception the exception
	 * @see    UsersRole
	 */
	public List<UsersRole> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UsersRole.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersRole}
	 * @throws Exception the exception
	 */
	public List<UsersRole> allUsersRole(int first, int pageSize) throws Exception {
		return dao.allUsersRole(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UsersRole for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UsersRole
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UsersRole.class);
	}
	
    /**
     * Lazy load UsersRole with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 UsersRole class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UsersRole} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UsersRole> allUsersRole(Class<UsersRole> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UsersRole>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UsersRole for lazy load with filters.
     *
     * @author TechFinium
     * @param entity UsersRole class
     * @param filters the filters
     * @return Number of rows in the UsersRole entity
     * @throws Exception the exception
     */	
	public int count(Class<UsersRole> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find by process.
	 *
	 * @param processRoles the process roles
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<UsersRole> findByProcess(ProcessRoles processRoles) throws Exception {
		return dao.findByProcess(processRoles);
	}
	
	public List<UsersRole> findByProcess(ProcessRoles processRoles, Tasks task) throws Exception {
		return dao.findByProcess(processRoles, task);
	}
	
	/**
	 * Find by process hosting company.
	 *
	 * @param hostingCompany the hosting company
	 * @param processEnum the process enum
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<UsersRole> findByProcessHostingCompany(HostingCompany hostingCompany, ConfigDocProcessEnum processEnum) throws Exception {
		return dao.findByProcessHostingCompany(hostingCompany, processEnum);
	}
	
	/**
	 * Dashboard.
	 *
	 * @param user the user
	 * @return the string
	 * @throws Exception the exception
	 */
	public String dashboard(Users user) throws Exception {  
		List<String> l = findDashboard(user);
		if (l.size()>0) return l.get(0);
		else return "/pages/dashboard.jsf";
	}
	
	/**
	 * Find dashboard.
	 *
	 * @param user the user
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<String> findDashboard(Users user) throws Exception { 
		return dao.findDashboard(user.getId());
	}
	
	/**
	 * Find roles.
	 *
	 * @param user the user
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Roles> findRoles(Users user) throws Exception { 
		return dao.findRoles(user.getId());
	}
	
	/**
	 * Find unique roles.
	 *
	 * @param user the user
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Roles> findUniqueRoles(Users user) throws Exception { 
		List<Roles> l =  findRoles(user);
		if (l.size()==1) return l;
		else if (l.size() > 1) {
			List<Roles> l2 = new ArrayList<Roles>();
			Map<Long,Roles> m = new HashMap<Long,Roles>();
			for (Roles role : l) {
				m.put(role.getId(), role);
			}
			
			for (Entry<Long, Roles> entry : m.entrySet()) {
				l2.add(entry.getValue());
			}
			
			return l2;
		}
		else {
			return null;
			//throw new Exception("Configuration error. You have no roles assigned. Please contact support");
		}
	}
}
