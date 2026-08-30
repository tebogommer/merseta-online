package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UserHostingCompanyDAO;
import haj.com.entity.HostingCompany;
import haj.com.entity.UserHostingCompany;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserHostingCompanyService.
 */
public class UserHostingCompanyService extends AbstractService {
	/** The dao. */
	private UserHostingCompanyDAO dao = new UserHostingCompanyDAO();

	/**
	 * Get all UserHostingCompany.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception the exception
	 * @see   UserHostingCompany
	 */
	public List<UserHostingCompany> allUserHostingCompany() throws Exception {
	  	return dao.allUserHostingCompany();
	}


	/**
	 * Create or update UserHostingCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserHostingCompany
	 */
	public void create(UserHostingCompany entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserHostingCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserHostingCompany
	 */
	public void update(UserHostingCompany entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserHostingCompany.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserHostingCompany
	 */
	public void delete(UserHostingCompany entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception the exception
	 * @see    UserHostingCompany
	 */
	public UserHostingCompany findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserHostingCompany by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception the exception
	 * @see    UserHostingCompany
	 */
	public List<UserHostingCompany> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserHostingCompany.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserHostingCompany}
	 * @throws Exception the exception
	 */
	public List<UserHostingCompany> allUserHostingCompany(int first, int pageSize) throws Exception {
		return dao.allUserHostingCompany(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserHostingCompany for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UserHostingCompany
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserHostingCompany.class);
	}
	
    /**
     * Lazy load UserHostingCompany with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 UserHostingCompany class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserHostingCompany} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserHostingCompany> allUserHostingCompany(Class<UserHostingCompany> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String sql = "Select o from UserHostingCompany o left join fetch o.hostingCompany left join fetch o.user";
		return ( List<UserHostingCompany>)dao.sortAndFilter(first,pageSize,sortField,sortOrder,filters, sql);
	
	}
	
    /**
     * Get count of UserHostingCompany for lazy load with filters.
     *
     * @author TechFinium
     * @param entity UserHostingCompany class
     * @param filters the filters
     * @return Number of rows in the UserHostingCompany entity
     * @throws Exception the exception
     */	
	public int count(Class<UserHostingCompany> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Find count by user and housting company.
	 *
	 * @param hostingCompany the hosting company
	 * @param user the user
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long findCountByUserAndHoustingCompany(HostingCompany hostingCompany, Users user) throws Exception {
		return dao.findCountByUserAndHoustingCompany(hostingCompany, user);
	}
}
