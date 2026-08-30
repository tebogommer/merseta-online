package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.RolesDAO;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class RolesService.
 */
public class RolesService extends AbstractService {
	/** The dao. */
	private RolesDAO dao = new RolesDAO();
	
	private static RolesService rolesService;
	public static synchronized RolesService instance() {
		if (rolesService == null) {
			rolesService = new RolesService();
		}
		return rolesService;
	}

	/**
	 * Get all Roles.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception the exception
	 * @see   Roles
	 */
	public List<Roles> allRoles() throws Exception {
	  	return dao.allRoles();
	}


	/**
	 * Create or update Roles.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Roles
	 */
	public void create(Roles entity) throws Exception  {
		if (dao.findUniqueCode(entity) != null) throw new Exception("Code must be unique");
		   if (entity.getId() ==null) {
			 entity.setCreateDate(new java.util.Date());
			 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  Roles.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Roles
	 */
	public void update(Roles entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Roles.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Roles
	 */
	public void delete(Roles entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Roles}
	 * @throws Exception the exception
	 * @see    Roles
	 */
	public Roles findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find Roles by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception the exception
	 * @see    Roles
	 */
	public List<Roles> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load Roles.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Roles}
	 * @throws Exception the exception
	 */
	public List<Roles> allRoles(int first, int pageSize) throws Exception {
		return dao.allRoles(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of Roles for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Roles
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Roles.class);
	}
	
    /**
     * Lazy load Roles with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Roles class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Roles} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Roles> allRoles(Class<Roles> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<Roles>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of Roles for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Roles class
     * @param filters the filters
     * @return Number of rows in the Roles entity
     * @throws Exception the exception
     */	
	public int count(Class<Roles> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * All roles.
	 *
	 * @param hostingCompanyProcess the hosting company process
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<Roles> allRoles(HostingCompanyProcess hostingCompanyProcess) throws Exception {
		return dao.allRoles(hostingCompanyProcess);
	}
}
