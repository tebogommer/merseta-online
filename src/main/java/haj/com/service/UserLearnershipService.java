package haj.com.service;

import java.util.List;

import haj.com.dao.UserLearnershipDAO;
import haj.com.entity.UserLearnership;
import haj.com.entity.UserSkillsProgramme;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class UserLearnershipService extends AbstractService {
	/** The dao. */
	private UserLearnershipDAO dao = new UserLearnershipDAO();

	/**
	 * Get all UserLearnership
 	 * @author TechFinium 
 	 * @see   UserLearnership
 	 * @return a list of {@link haj.com.entity.UserLearnership}
	 * @throws Exception the exception
 	 */
	public List<UserLearnership> allUserLearnership() throws Exception {
	  	return dao.allUserLearnership();
	}

	public List<UserLearnership> findByUserAMApplication(Long userId,Long amApID) throws Exception {
	  return dao.findByUserAMApplication(userId, amApID);
	}


	/**
	 * Create or update UserLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserLearnership
	 */
	public void create(UserLearnership entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserLearnership
	 */
	public void update(UserLearnership entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserLearnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserLearnership
	 */
	public void delete(UserLearnership entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserLearnership}
	 * @throws Exception the exception
	 * @see    UserLearnership
	 */
	public UserLearnership findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserLearnership by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserLearnership}
	 * @throws Exception the exception
	 * @see    UserLearnership
	 */
	public List<UserLearnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserLearnership
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserLearnership}
	 * @throws Exception the exception
	 */
	public List<UserLearnership> allUserLearnership(int first, int pageSize) throws Exception {
		return dao.allUserLearnership(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserLearnership for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UserLearnership
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserLearnership.class);
	}
	
    /**
     * Lazy load UserLearnership with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UserLearnership class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserLearnership} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserLearnership> allUserLearnership(Class<UserLearnership> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserLearnership>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UserLearnership for lazy load with filters
     * @author TechFinium 
     * @param entity UserLearnership class
     * @param filters the filters
     * @return Number of rows in the UserLearnership entity
     * @throws Exception the exception     
     */	
	public int count(Class<UserLearnership> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
