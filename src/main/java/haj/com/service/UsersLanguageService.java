package haj.com.service;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UsersLanguageDAO;
import haj.com.entity.Users;
import haj.com.entity.UsersLanguage;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class UsersLanguageService extends AbstractService {
	/** The dao. */
	private UsersLanguageDAO dao = new UsersLanguageDAO();
	
	private static UsersLanguageService usersLanguageService = null;
	public static synchronized UsersLanguageService instance() {
		if (usersLanguageService == null) {
			usersLanguageService = new UsersLanguageService();
		}
		return usersLanguageService;
	}

	/**
	 * Get all UsersLanguage
 	 * @author TechFinium 
 	 * @see   UsersLanguage
 	 * @return a list of {@link haj.com.entity.UsersLanguage}
	 * @throws Exception the exception
 	 */
	public List<UsersLanguage> allUsersLanguage() throws Exception {
	  	return dao.allUsersLanguage();
	}
	
	/**
	 * Get all UsersLanguage by user
 	 * @author TechFinium 
 	 *  * @param Users the users
 	 * @see    UsersLanguage
 	 * @return a list of {@link haj.com.entity.UsersLanguage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> findByUser(Users users) throws Exception {
		 return dao.findByUser(users);
	}

	
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> findByUserId(Long userID) throws Exception {
		 return dao.findByUserId(userID);
	}

	/**
	 * Create or update UsersLanguage.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UsersLanguage
	 */
	public void create(UsersLanguage entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UsersLanguage.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersLanguage
	 */
	public void update(UsersLanguage entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UsersLanguage.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersLanguage
	 */
	public void delete(UsersLanguage entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersLanguage}
	 * @throws Exception the exception
	 * @see    UsersLanguage
	 */
	public UsersLanguage findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UsersLanguage by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UsersLanguage}
	 * @throws Exception the exception
	 * @see    UsersLanguage
	 */
	public List<UsersLanguage> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UsersLanguage
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersLanguage}
	 * @throws Exception the exception
	 */
	public List<UsersLanguage> allUsersLanguage(int first, int pageSize) throws Exception {
		return dao.allUsersLanguage(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UsersLanguage for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UsersLanguage
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UsersLanguage.class);
	}
	
    /**
     * Lazy load UsersLanguage with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UsersLanguage class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UsersLanguage} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UsersLanguage> allUsersLanguage(Class<UsersLanguage> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UsersLanguage>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UsersLanguage for lazy load with filters
     * @author TechFinium 
     * @param entity UsersLanguage class
     * @param filters the filters
     * @return Number of rows in the UsersLanguage entity
     * @throws Exception the exception     
     */	
	public int count(Class<UsersLanguage> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public List<UsersLanguage> findByTargetClassAndKey(Long id, String name) {
		return dao.findByTargetClassAndKey(id,name);
	}
	
	public int countByUserId(Long userID) throws Exception {
		return dao.countByUserId(userID);
	}
	
	public void createLinkToUser(List<UsersLanguage> entries, Users userToLink, Users sessionUser) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		List<IDataEntity> updateList = new ArrayList<>();
		if (!entries.isEmpty()) {
			for (UsersLanguage usersDisability : entries) {
				usersDisability.setUpdateUser(sessionUser);
				usersDisability.setUpdateDate(getSynchronizedDate());
				if (usersDisability.getId() == null) {
					usersDisability.setUser(userToLink);
					createList.add(usersDisability);
				} else {
					updateList.add(usersDisability);
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