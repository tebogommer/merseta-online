package haj.com.service;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UserQualificationsDAO;
import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.UserQualifications;
import haj.com.entity.Users;
import haj.com.entity.enums.AssessorModeratorAppTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class UserQualificationsService.
 */
public class UserQualificationsService extends AbstractService {
	/** The dao. */
	private UserQualificationsDAO dao = new UserQualificationsDAO();

	/**
	 * Get all UserQualifications.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception the exception
	 * @see   UserQualifications
	 */
	public List<UserQualifications> allUserQualifications() throws Exception {
	  	return dao.allUserQualifications();
	}


	/**
	 * Create or update UserQualifications.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserQualifications
	 */
	public void create(UserQualifications entity) throws Exception  {
		/*UserQualifications uq = findByUserAndQualification(entity.getUser(), entity.getQualification());
		if (entity.getId() == null && uq != null) {
			throw new ValidationException("qualification.already.exists", entity.getQualification().getDescription());
		}*/
		if (entity.getId() ==null) {
			this.dao.create(entity);
		}else{
			this.dao.update(entity);
		}		
	}


	/**
	 * Update  UserQualifications.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserQualifications
	 */
	public void update(UserQualifications entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserQualifications.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserQualifications
	 */
	public void delete(IDataEntity entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserQualifications}
	 * @throws Exception the exception
	 * @see    UserQualifications
	 */
	public UserQualifications findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserQualifications by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception the exception
	 * @see    UserQualifications
	 */
	public List<UserQualifications> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserQualifications.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception the exception
	 */
	public List<UserQualifications> allUserQualifications(int first, int pageSize) throws Exception {
		return dao.allUserQualifications(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserQualifications for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UserQualifications
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserQualifications.class);
	}
	
    /**
     * Lazy load UserQualifications with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 UserQualifications class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserQualifications} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserQualifications> allUserQualifications(Class<UserQualifications> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserQualifications>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UserQualifications for lazy load with filters.
     *
     * @author TechFinium
     * @param entity UserQualifications class
     * @param filters the filters
     * @return Number of rows in the UserQualifications entity
     * @throws Exception the exception
     */	
	public int count(Class<UserQualifications> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/**
	 * Finds all UserQualifications by user id.
	 *
	 * @param user the user
	 * @return List<UserQualifications>
	 * @throws Exception the exception
	 */
	public List<UserQualifications> findByUser(Users user) throws Exception{
		return dao.findByUser(user.getId());
	}
	
	/**
	 * Find UserQualifications by user and forAssessorModeratorApplication.
	 *
	 * @author TechFinium
	 * @param userId the user id
	 * @return a list of {@link haj.com.entity.UserQualifications}
	 * @throws Exception global exception
	 * @see    UserQualifications
	 */
	@SuppressWarnings("unchecked")
	public List<UserQualifications> findByUserAMApplication(Long userId,Long amApID) throws Exception {
	  return dao.findByUserAMApplication(userId, amApID);
	}
	
	public List<UserQualifications> findByUserAMApplicationAndAccept(Long userId,Long amApID,Boolean accept) throws Exception {
		  return dao.findByUserAMApplicationAndAccept(userId, amApID,accept);
		}
	
	/**
	 * Locates UserQualifications by user and qualification.
	 *
	 * @param user the user
	 * @param qualification the qualification
	 * @return UserQualifications
	 * @throws Exception the exception
	 */
	public UserQualifications findByUserAndQualification(Users user, Qualification qualification) throws Exception {
		return dao.findByUserAndQualification(user.getId(), qualification.getId());
	}
	
	/**
	 * Find by user id left join.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<UserQualifications> findByUserIdLeftJoin(Long userId) throws Exception {
		return dao.findByUserIdLeftJoin(userId);
	}
	
	/**
	 * Creates the with list.
	 *
	 * @param u the u
	 * @param uqList the uq list
	 * @throws Exception the exception
	 */
	public void createWithList(Users u, List<UserQualifications> uqList) throws Exception {
		for (UserQualifications uq : uqList) {
			uq.setUser(u);
			create(uq);
		}
	}
	
	/**
	 * Finds AssessorModeratorApplication by user and qualification ID.
	 *
	 * @param uid the uid
	 * @param qualificationId the qualification id
	 * @param assessorModeratorAppTypeEnum 
	 * @return AssessorModeratorApplication
	 * @throws Exception the exception
	 */
	public List<AssessorModeratorApplication> findAMApplicationByUserAndQual(Long uid, Long qualificationId, AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum) throws Exception {
	 	return dao.findAMApplicationByUserAndQual(uid, qualificationId,assessorModeratorAppTypeEnum);
	}
	
	public List<AssessorModeratorApplication> findAMApplicationByUserQualAppTypeAndAccept(Long uid, Long qualificationId, AssessorModeratorAppTypeEnum assessorModeratorAppTypeEnum,Boolean accept) throws Exception {
	 	return dao.findAMApplicationByUserQualAppTypeAndAccept(uid, qualificationId,assessorModeratorAppTypeEnum,accept);
	}
	
	public List<Qualification> findQualificationByAMApplication(Long amApID) throws Exception {
	 	return dao.findQualificationByAMApplication(amApID);
	}

	public List<UserQualifications> findByTargetKeyAndTargetClas(Long id, String name) {
		return dao.findByTargetKeyAndTargetClas( id,  name);
	}
	
	public UserQualifications findByKeyWithJoin(Long id) throws Exception {
		return dao.findByKeyWithJoin(id);
	}
	
	public List<UserQualifications> findUserQualificationByAssessModeratorAppllication(Long assessorModeratorApplicationId) throws Exception {
		return dao.findUserQualificationByAssessModeratorAppllication(assessorModeratorApplicationId);
	}
	
	public List<Qualification> findQualificationByAssessModeratorAppllicationWhereApproved(Long assessorModeratorApplicationId, Boolean accept) throws Exception {
		return dao.findQualificationByAssessModeratorAppllicationWhereApproved(assessorModeratorApplicationId, accept);
	}
	
	public List<UserQualifications> findUserQualificationByAssessModeratorAppllicationAndQualification(Long assessorModeratorApplicationId, Long qualificationId) throws Exception {
		return dao.findUserQualificationByAssessModeratorAppllicationAndQualification(assessorModeratorApplicationId, qualificationId);
	}
}
