package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UserPermissionsDAO;
import haj.com.entity.UserPermissions;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;

public class UserPermissionsService extends AbstractService {
	/** The dao. */
	private UserPermissionsDAO dao = new UserPermissionsDAO();
	
	private static UserPermissionsService userPermissionsService;
	public static synchronized UserPermissionsService instance() {
		if (userPermissionsService == null) {
			userPermissionsService = new UserPermissionsService();
		}
		return userPermissionsService;
	}
	

	/**
	 * Get all UserPermissions
 	 * @author TechFinium 
 	 * @see   UserPermissions
 	 * @return a list of {@link haj.com.entity.UserPermissions}
	 * @throws Exception the exception
 	 */
	public List<UserPermissions> allUserPermissions() throws Exception {
	  	return dao.allUserPermissions();
	}


	/**
	 * Create or update UserPermissions.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserPermissions
	 */
	public void create(UserPermissions entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserPermissions.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserPermissions
	 */
	public void update(UserPermissions entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserPermissions.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserPermissions
	 */
	public void delete(UserPermissions entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserPermissions}
	 * @throws Exception the exception
	 * @see    UserPermissions
	 */
	public UserPermissions findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserPermissions by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserPermissions}
	 * @throws Exception the exception
	 * @see    UserPermissions
	 */
	public List<UserPermissions> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserPermissions
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserPermissions}
	 * @throws Exception the exception
	 */
	public List<UserPermissions> allUserPermissions(int first, int pageSize) throws Exception {
		return dao.allUserPermissions(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserPermissions for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UserPermissions
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserPermissions.class);
	}
	
    /**
     * Lazy load UserPermissions with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UserPermissions class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserPermissions} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserPermissions> allUserPermissions(Class<UserPermissions> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserPermissions>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UserPermissions for lazy load with filters
     * @author TechFinium 
     * @param entity UserPermissions class
     * @param filters the filters
     * @return Number of rows in the UserPermissions entity
     * @throws Exception the exception     
     */	
	public int count(Class<UserPermissions> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public UserPermissions findByUser(Long userId) throws Exception {
		return dao.findByUser(userId);
	}
	
	public UserPermissions findByUserAndCreate(Users user) throws Exception{
		UserPermissions userPermissions = findByUser(user.getId());
		if (userPermissions == null) {
			userPermissions = new UserPermissions(user);
			create(userPermissions);
			return findByUser(user.getId());
		} else {
			return userPermissions;
		}
	}
	
}