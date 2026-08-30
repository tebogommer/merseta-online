package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UsersDisabilityDAO;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class UsersDisabilityService extends AbstractService {
	/** The dao. */
	private UsersDisabilityDAO dao = new UsersDisabilityDAO();
	
	private static UsersDisabilityService usersDisabilityService = null;
	public static synchronized UsersDisabilityService instance() {
		if (usersDisabilityService == null) {
			usersDisabilityService = new UsersDisabilityService();
		}
		return usersDisabilityService;
	}

	/**
	 * Get all UsersDisability
 	 * @author TechFinium 
 	 * @see   UsersDisability
 	 * @return a list of {@link haj.com.entity.UsersDisability}
	 * @throws Exception the exception
 	 */
	public List<UsersDisability> allUsersDisability() throws Exception {
	  	return dao.allUsersDisability();
	}


	/**
	 * Create or update UsersDisability.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UsersDisability
	 */
	public void create(UsersDisability entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UsersDisability.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersDisability
	 */
	public void update(UsersDisability entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UsersDisability.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersDisability
	 */
	public void delete(UsersDisability entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersDisability}
	 * @throws Exception the exception
	 * @see    UsersDisability
	 */
	public UsersDisability findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UsersDisability by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UsersDisability}
	 * @throws Exception the exception
	 * @see    UsersDisability
	 */
	public List<UsersDisability> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UsersDisability
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersDisability}
	 * @throws Exception the exception
	 */
	public List<UsersDisability> allUsersDisability(int first, int pageSize) throws Exception {
		return dao.allUsersDisability(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UsersDisability for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UsersDisability
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UsersDisability.class);
	}
	
    /**
     * Lazy load UsersDisability with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UsersDisability class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UsersDisability} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UsersDisability> allUsersDisability(Class<UsersDisability> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UsersDisability>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UsersDisability for lazy load with filters
     * @author TechFinium 
     * @param entity UsersDisability class
     * @param filters the filters
     * @return Number of rows in the UsersDisability entity
     * @throws Exception the exception     
     */	
	public int count(Class<UsersDisability> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<UsersDisability> findByKeyUser(Users user) {
		return dao.findByKeyUser(user.getId());
	}

	public List<UsersDisability> findByTargetClassAndKey(Long id, String name) {
		return dao.findByTargetClassAndKey(id, name);
	}
	
	public int countByKeyUser(Long userID) throws Exception {
		return dao.countByKeyUser(userID);
	}
	
	public void createLinkToUser(List<UsersDisability> entries, Users userToLink, Users sessionUser) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> updateList = new ArrayList<>();
		if (!entries.isEmpty()) {
			for (UsersDisability userLanguage : entries) {
				userLanguage.setUpdateUser(sessionUser);
				userLanguage.setUpdateDate(getSynchronizedDate());
				if (userLanguage.getId() == null) {
					userLanguage.setUser(userToLink);
					createList.add(userLanguage);
				} else {
					updateList.add(userLanguage);
				}
			}
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
}
