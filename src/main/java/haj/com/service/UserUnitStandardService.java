package haj.com.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UserUnitStandardDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.UserUnitStandard;
import haj.com.entity.Users;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.lookup.UnitStandard;
import haj.com.entity.lookup.UnitStandards;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UserUnitStandardService.
 */
public class UserUnitStandardService extends AbstractService {
	/** The dao. */
	private UserUnitStandardDAO dao = new UserUnitStandardDAO();

	/**
	 * Get all UserUnitStandard.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception the exception
	 * @see   UserUnitStandard
	 */
	public List<UserUnitStandard> allUserUnitStandard() throws Exception {
	  	return dao.allUserUnitStandard();
	}


	/**
	 * Create or update UserUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserUnitStandard
	 */
	public void create(UserUnitStandard entity) throws Exception  {
		UserUnitStandard uus = findByUserAndUnitStandard(entity.getUser(), entity.getUnitStandard());
		if (entity.getId() == null && uus != null) {
			throw new ValidationException("qualification.already.exists", entity.getUnitStandard().getTitle());
		}
		if (entity.getId() ==null) {
			this.dao.create(entity);
		} else {
			this.dao.update(entity);	
		}	 
	}
	
	/**
	 * Create or update UserUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserUnitStandard
	 */
	public void createAUserUnitStandard(UserUnitStandard entity) throws Exception  {
		
		if (entity.getId() ==null) {
			this.dao.create(entity);
		} else {
			this.dao.update(entity);	
		}	 
	}


	/**
	 * Create or update UserUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserUnitStandard
	 */
	public void createAUserUnitStandard(AssessorModeratorApplication assessorModeratorApplication,UnitStandards unitStandards) throws Exception  {
		if(findByUnitStandardAndAPApplication(unitStandards.getId(),assessorModeratorApplication.getId()).size() >0) {
			throw new Exception("Unit Standards has already been added for this user");
		}
		UserUnitStandard userUnitStandard = new UserUnitStandard();
		userUnitStandard.setAccept(true);
		userUnitStandard.setForAssessorModeratorApplication(assessorModeratorApplication);
		userUnitStandard.setUser(assessorModeratorApplication.getUser());
		userUnitStandard.setUnitStandard(unitStandards);
		this.dao.create(assessorModeratorApplication);			 
	}
	/**
	 * Update  UserUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserUnitStandard
	 */
	public void update(UserUnitStandard entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserUnitStandard
	 */
	public void delete(UserUnitStandard entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception the exception
	 * @see    UserUnitStandard
	 */
	public UserUnitStandard findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception the exception
	 * @see    UserUnitStandard
	 */
	public List<UserUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserUnitStandard.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception the exception
	 */
	public List<UserUnitStandard> allUserUnitStandard(int first, int pageSize) throws Exception {
		return dao.allUserUnitStandard(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserUnitStandard for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UserUnitStandard
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserUnitStandard.class);
	}
	
    /**
     * Lazy load UserUnitStandard with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 UserUnitStandard class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserUnitStandard} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> allUserUnitStandard(Class<UserUnitStandard> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserUnitStandard>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UserUnitStandard for lazy load with filters.
     *
     * @author TechFinium
     * @param entity UserUnitStandard class
     * @param filters the filters
     * @return Number of rows in the UserUnitStandard entity
     * @throws Exception the exception
     */	
	public int count(Class<UserUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Finds all UserUnitStandard by user id.
	 *
	 * @param user the user
	 * @return List<UserUnitStandard>
	 * @throws Exception the exception
	 */
	public List<UserUnitStandard> findByUser(Users user) throws Exception{
		return dao.findByUser(user.getId());
	}
	
	/**
	 * Find UserUnitStandard by user and forAssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param uid the uid
	 * @return a list of {@link haj.com.entity.UserUnitStandard}
	 * @throws Exception global exception
	 * @see    UserUnitStandard
	 */
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByUserAndAPApplication(Long uid,Long amAppID ) throws Exception {
	 	return dao.findByUserAndAPApplication(uid, amAppID);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserUnitStandard> findByUnitStandardAndAPApplication(Long unitStandardID,Long assessorModeratorApplicationID )throws Exception {
	 	return dao.findByUnitStandardAndAPApplication(unitStandardID, assessorModeratorApplicationID);
	}
	
	public List<UserUnitStandard> findByUserAndAPApplicationAndAccept(Long uid,Long amAppID,Boolean accept ) throws Exception {
	 	return dao.findByUserAndAPApplicationAndAccept(uid, amAppID,accept);
	}
	
	/**
	 * Locates UserUnitStandard by user and unit standard.
	 *
	 * @param user the user
	 * @param unitStandard the unit standard
	 * @return UserUnitStandard
	 * @throws Exception the exception
	 */
	public UserUnitStandard findByUserAndUnitStandard(Users user, UnitStandards unitStandard) throws Exception {
		return dao.findByUserAndUnitStandard(user.getId(), unitStandard.getId());
	}
	
	/**
	 * Finds AssessorModeratorApplication by user and unitStandard ID.
	 *
	 * @param uid the uid
	 * @param appType 
	 * @param qualificationId the qualification id
	 * @return AssessorModeratorApplication
	 * @throws Exception the exception
	 */
	public List<AssessorModeratorApplication> findAMApplicationByUserAndUS(Long uid, Long unitStandardId, AssessorModeratorAppTypeEnum appType) throws Exception {
		return dao.findAMApplicationByUserAndUS(uid, unitStandardId,appType);
	}
	
	public List<AssessorModeratorApplication> findAMApplicationByUserUSAndAccept(Long uid, Long unitStandardId, AssessorModeratorAppTypeEnum appType,Boolean accept) throws Exception {
		return dao.findAMApplicationByUserUSAndAccept(uid, unitStandardId,appType,accept);
	}
	
	public List<AssessorModeratorApplication> findAMApplicationByUserUSAppTypeAndAccept(Long uid, Long unitStandardId, AssessorModeratorAppTypeEnum appType,Boolean accept) throws Exception {
		return dao.findAMApplicationByUserUSAppTypeAndAccept(uid, unitStandardId,appType,accept);
	}
	
	public List<UnitStandards> findUnitStandardByAMApplication(Long id) throws Exception {
	 	return dao.findUnitStandardByAMApplication(id);
	}


	public List<UserUnitStandard> findByTargetKeyAndTargetClas(Long id, String targetClass) {
		return dao.findByTargetKeyAndTargetClas(id,targetClass);
	}
}
