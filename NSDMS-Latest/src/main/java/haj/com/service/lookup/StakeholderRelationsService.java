package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.dao.lookup.StakeholderRelationsDAO;
import haj.com.entity.Users;
import haj.com.entity.enums.RelationTypeEnum;
import haj.com.entity.lookup.StakeholderRelations;
import haj.com.entity.lookup.StakeholderRelationsSurvey;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.DocService;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class StakeholderRelationsService extends AbstractService {

	/** The dao. */
	private StakeholderRelationsDAO dao = new StakeholderRelationsDAO();

	/**
	 * Get all StakeholderRelations
	 * 
	 * @author TechFinium
	 * @see StakeholderRelations
	 * @return a list of {@link haj.com.entity.StakeholderRelations}
	 * @throws Exception
	 *             the exception
	 */
	public List<StakeholderRelations> allStakeholderRelations() throws Exception {
		return dao.allStakeholderRelations();
	}

	/**
	 * Create or update StakeholderRelations.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelations
	 */
	public void create(StakeholderRelations entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update StakeholderRelations.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelations
	 */
	public void update(StakeholderRelations entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete StakeholderRelations.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelations
	 */
	public void delete(StakeholderRelations entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.StakeholderRelations}
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelations
	 */
	public StakeholderRelations findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find StakeholderRelations by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.StakeholderRelations}
	 * @throws Exception
	 *             the exception
	 * @see StakeholderRelations
	 */
	public List<StakeholderRelations> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load StakeholderRelations
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.StakeholderRelations}
	 * @throws Exception
	 *             the exception
	 */
	public List<StakeholderRelations> allStakeholderRelations(int first, int pageSize) throws Exception {
		return dao.allStakeholderRelations(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of StakeholderRelations for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the StakeholderRelations
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(StakeholderRelations.class);
	}

	/**
	 * Lazy load StakeholderRelations with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            StakeholderRelations class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.StakeholderRelations} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allStakeholderRelations(Class<StakeholderRelations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<StakeholderRelations>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters), false, null);
	}

	/**
	 * Get count of StakeholderRelations for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            StakeholderRelations class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the StakeholderRelations entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<StakeholderRelations> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	/**
	 * 
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allStakeholderRelationsWhereUserNull(Class<StakeholderRelations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from StakeholderRelations o where o.user is null ";
		return populateAdditionalInformationList((List<StakeholderRelations>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), false, null);
	}
	
	/**
	 * 
	 * @param entity
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	public int countAllStakeholderRelationsWhereUserNull(Class<StakeholderRelations> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from StakeholderRelations o where o.user is null";
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * Surveys not taken by session users
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param sessionUser
	 * @param enumType
	 * @return List<StakeholderRelations>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allSurveysNotTakenByUsers(Class<StakeholderRelations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users sessionUser, RelationTypeEnum enumType) throws Exception {
		String hql = "select o from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user is null and o.id not in (select x.parentLink from StakeholderRelations x where x.user.id = :userID)";
		filters.put("enumType", enumType);
		filters.put("userID", sessionUser.getId());
		return populateAdditionalInformationList((List<StakeholderRelations>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), false, null);
	}

	/**
	 * Count of Surveys not taken by session users
	 * @param entity
	 * @param filters
	 * @return int
	 * @throws Exception
	 */
	public int countAllSurveysNotTakenByUsers(Class<StakeholderRelations> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user is null and o.id not in (select x.parentLink from StakeholderRelations x where x.user.id = :userID)";
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * All Surveys taken by session user
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param sessionUser
	 * @param enumType
	 * @return List<StakeholderRelations>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allSurveysTakenByUser(Class<StakeholderRelations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users sessionUser, RelationTypeEnum enumType) throws Exception {
		String hql = "select o from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user.id = :userID";
		filters.put("enumType", enumType);
		filters.put("userID", sessionUser.getId());
		return populateAdditionalInformationList((List<StakeholderRelations>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), false, null);
	}
	
	/**
	 * Count of Surveys taken by session user
	 * @param entity
	 * @param filters
	 * @return int
	 * @throws Exception
	 */
	public int countAllSurveysTakenByUser(Class<StakeholderRelations> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user.id = :userID";
		return dao.countWhere(filters, hql);
	}
	
	/**
	 * All Surveys configured in the admin module
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param sessionUser
	 * @param enumType
	 * @return List<StakeholderRelations>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allSurveysConfigured(Class<StakeholderRelations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, RelationTypeEnum enumType) throws Exception {
		String hql = "select o from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user is null";
		filters.put("enumType", enumType);
		return populateAdditionalInformationList((List<StakeholderRelations>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), false, null);
	}
	
	/**
	 * Count of Surveys taken by session user
	 * @param entity
	 * @param filters
	 * @return int
	 * @throws Exception
	 */
	public int countAllSurveysConfigured(Class<StakeholderRelations> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user is null";
		return dao.countWhere(filters, hql);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<StakeholderRelations> allSurveysTakenByUsers(Class<StakeholderRelations> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, RelationTypeEnum enumType, boolean dateFilerRequired, Date fromDate, Date toDate) throws Exception {
		String hql = "select o from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user is not null";
		filters.put("enumType", enumType);
		if (dateFilerRequired) {
			hql += " and date(o.dateSubmitted) between date(:fromDate) and date(:toDate)";
			filters.put("fromDate", fromDate);
			filters.put("toDate", toDate);
		}
		return populateAdditionalInformationList((List<StakeholderRelations>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), false, null);
	}
	
	public int countAllSurveysTakenByUsers(Class<StakeholderRelations> entity, Map<String, Object> filters,boolean dateFilerRequired) throws Exception {
		String hql = "select count(o) from StakeholderRelations o where o.relationTypeEnum = :enumType and o.user is not null";
		if (dateFilerRequired) {
			hql += " and date(o.dateSubmitted) between date(:fromDate) and date(:toDate)";
		}
		return dao.countWhere(filters, hql);
	}
	
	
	
	/**
	 * Count on total surveys completed by users
	 * @return Long
	 * @throws Exception
	 */
	public Long countTotalSurveysTakenByUsers() throws Exception {
		return dao.countTotalSurveysTakenByUsers();
	}
	
	/**
	 * Count on total surveys configured in the admin module
	 * @return Long
	 * @throws Exception
	 */
	public Long countTotalSurveysAvalible() throws Exception {
		return dao.countTotalSurveysAvalible();
	}
	
	/**
	 * Find StakeholderRelations by RelationTypeEnum and where user is not assigned
 	 * @author TechFinium 
 	 * @param enumPassed
 	 * @see    StakeholderRelations
  	 * @return a list of {@link haj.com.entity.StakeholderRelations}
 	 * @throws Exception global exception
 	 */
	public List<StakeholderRelations> findByRelationTypeEnum(RelationTypeEnum enumPassed) throws Exception {
		return populateAdditionalInformationList(dao.findByRelationTypeEnum(enumPassed) ,false, null);
	}

	/**
	 * Populates additional information against list of StakeholderRelations
	 * 
	 * @param stakeholderRelationsList
	 * @param serachByUser
	 * @param user
	 * @return stakeholderRelationsList
	 * @throws Exception
	 */
	public List<StakeholderRelations> populateAdditionalInformationList(List<StakeholderRelations> stakeholderRelationsList, boolean serachByUser, Users user) throws Exception {
		for (StakeholderRelations stakeholderRelations : stakeholderRelationsList) {
			populateAdditionalInformation(stakeholderRelations, serachByUser, user);
		}
		return stakeholderRelationsList;
	}

	/**
	 * Populates additional information against StakeholderRelations
	 * 
	 * @see StakeholderRelationsSurveyService
	 * @see StakeholderRelations
	 * @param stakeholderRelations
	 * @param serachByUser
	 * @param user
	 * @return StakeholderRelations
	 * @throws Exception
	 */
	private StakeholderRelations populateAdditionalInformation(StakeholderRelations stakeholderRelations,boolean serachByUser, Users user) throws Exception {
		// if the assigned type is a survey, populate survey questions assigned
		if (stakeholderRelations.getRelationTypeEnum() == RelationTypeEnum.Survey) {
			// if search is by user will locate 
			if (serachByUser) {
				stakeholderRelations.setStakeholderRelationsSurveyList(
						StakeholderRelationsSurveyService.instance().findByStakeholderRelationsAndUser(stakeholderRelations, user));
			} else {
				stakeholderRelations.setStakeholderRelationsSurveyList(
						StakeholderRelationsSurveyService.instance().findByStakeholderRelationsUserNotAssigned(stakeholderRelations));
			}
		}
		// sets docs to a transient list assigned to stakeholderRelations
		if (stakeholderRelations.getRelationTypeEnum() == RelationTypeEnum.NewsLetter) {
			stakeholderRelations.setDocAssignedList(DocService.instance().searchByTargetKeyAndClass(stakeholderRelations.getClass().getName(), stakeholderRelations.getId()));
		}
		return stakeholderRelations;
	}

	/**
	 * Notifies the selected user/users of the notification created
	 * temp use where it sends to hard coded email address
	 * @param stakeholderrelations
	 */
	public void notifyusers(StakeholderRelations stakeholderrelations) throws Exception{
		List<String> emailAddress = new ArrayList<>();
		emailAddress.addAll(GenericUtility.processEmailNotfications());
		for (String email : emailAddress) {
			notifyWithNotification(stakeholderrelations.getDescription(), email, null);
		}
	}

	/**
	 * Sends Notification 
	 * @param notificationMessage
	 * @param emailAddress
	 * @throws Exception
	 */
	private void notifyWithNotification(String notificationMessage, String emailAddress, Users user) throws Exception{
		String subject = "Stakeholder Relations Notification";
		if (user != null) {
			String msg = "<p> Dear: "+ user.getFirstName() + " " + user.getLastName() +" </p>"+ "<p> " + notificationMessage + " </p>";
			GenericUtility.sendMail(user.getEmail(), subject, msg, null);
		} else {
			String firstName = "";
			String lastName = "";
			String msg = "<p> Dear: "+ firstName + " " + lastName +" </p>"+ "<p> " + notificationMessage + " </p>";
			GenericUtility.sendMail(emailAddress, subject, msg, null);
		}
	}

	/**
	 * Validates and creates the new survey
	 * @param survey
	 * @param answerList
	 * @throws Exception
	 */
	public void submitAndCreateSurvey(StakeholderRelations survey, List<StakeholderRelationsSurvey> answerList, Users sessionUser) throws Exception{
		List<IDataEntity> entityList = new ArrayList<>();
		// adds the parent survey
		survey.setDateSubmitted(new Date());
		entityList.add(survey);
		
		// loops through and validates the survey
		for (StakeholderRelationsSurvey answer : answerList) {
			
			if (answer.getId() != null) {
				answer.setId(null);
			}
			
			if (!answer.getNotAtAll() && !answer.getNotReally() && !answer.getNuetral() && !answer.getToaLimitedExtent() && !answer.getToaLargeExtent()) {
				throw new Exception("Please select an answer for: " + answer.getDescription() + " before proceeding with submission");
			}
			// sets new survey
			answer.setStakeholderRelations(survey);
			answer.setUser(sessionUser);
			entityList.add(answer);
		}
		
		// creates all entries
		if (entityList.size() > 0) {
			dao.createBatch(entityList);
		}
	}
}
