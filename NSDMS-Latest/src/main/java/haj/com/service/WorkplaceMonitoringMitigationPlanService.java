package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkplaceMonitoringMitigationPlanDAO;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.WorkplaceMonitoringMitigationPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.OpenClosedEnum;
import haj.com.entity.enums.StatusEnum;
import haj.com.entity.enums.YesNoEnum;
import haj.com.framework.AbstractService;

public class WorkplaceMonitoringMitigationPlanService extends AbstractService {

	/** The dao. */
	private WorkplaceMonitoringMitigationPlanDAO dao = new WorkplaceMonitoringMitigationPlanDAO();
	
	
	/* Vars */
	private String softDeleteHql = " o.softDeleted = false ";

	/**
	 * Get all WorkplaceMonitoringMitigationPlan
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringMitigationPlan
	 * @return a list of
	 *         {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlan() throws Exception {
		return dao.allWorkplaceMonitoringMitigationPlan();
	}

	/**
	 * Create or update WorkplaceMonitoringMitigationPlan.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringMitigationPlan
	 */
	public void create(WorkplaceMonitoringMitigationPlan entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update WorkplaceMonitoringMitigationPlan.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringMitigationPlan
	 */
	public void update(WorkplaceMonitoringMitigationPlan entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WorkplaceMonitoringMitigationPlan.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringMitigationPlan
	 */
	public void delete(WorkplaceMonitoringMitigationPlan entity) throws Exception {
		this.dao.delete(entity);
	}
	
	private List<WorkplaceMonitoringMitigationPlan> populateAdditionalInformationList(List<WorkplaceMonitoringMitigationPlan> list) throws Exception {
		for (WorkplaceMonitoringMitigationPlan entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringMitigationPlan populateAdditionalInformation(WorkplaceMonitoringMitigationPlan entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringMitigationPlan
	 */
	public WorkplaceMonitoringMitigationPlan findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringMitigationPlan by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of
	 *         {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringMitigationPlan
	 */
	public List<WorkplaceMonitoringMitigationPlan> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WorkplaceMonitoringMitigationPlan
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of
	 *         {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlan(int first, int pageSize)
			throws Exception {
		return dao.allWorkplaceMonitoringMitigationPlan(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WorkplaceMonitoringMitigationPlan for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WorkplaceMonitoringMitigationPlan
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WorkplaceMonitoringMitigationPlan.class);
	}

	/**
	 * Lazy load WorkplaceMonitoringMitigationPlan with pagination, filter,
	 * sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WorkplaceMonitoringMitigationPlan class
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
	 * @return a list of
	 *         {@link haj.com.entity.WorkplaceMonitoringMitigationPlan}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlan(Class<WorkplaceMonitoringMitigationPlan> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WorkplaceMonitoringMitigationPlan>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of WorkplaceMonitoringMitigationPlan for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WorkplaceMonitoringMitigationPlan class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WorkplaceMonitoringMitigationPlan entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WorkplaceMonitoringMitigationPlan> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlanWithSoftDelete(Class<WorkplaceMonitoringMitigationPlan> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WorkplaceMonitoringMitigationPlan o where " + softDeleteHql;
		return populateAdditionalInformationList((List<WorkplaceMonitoringMitigationPlan>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllWorkplaceMonitoringMitigationPlanWithSoftDelete(Class<WorkplaceMonitoringMitigationPlan> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringMitigationPlan o where " + softDeleteHql;
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigationPlan> allWorkplaceMonitoringMitigationPlanBySiteVisit(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long workplaceMonitoringSiteVisitId) throws Exception {
		String hql = "select o from WorkplaceMonitoringMitigationPlan o where o.workplaceMonitoringSiteVisit.id = :workplaceMonitoringSiteVisitId and " + softDeleteHql;
		filters.put("workplaceMonitoringSiteVisitId", workplaceMonitoringSiteVisitId);
		return populateAdditionalInformationList((List<WorkplaceMonitoringMitigationPlan>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllWorkplaceMonitoringMitigationPlanBySiteVisit(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringMitigationPlan o where o.workplaceMonitoringSiteVisit.id = :workplaceMonitoringSiteVisitId and " + softDeleteHql;
		return dao.countWhere(filters, hql);
	}
	
	public WorkplaceMonitoringMitigationPlan findByGrantComplianceSurvey(Long grantComplianceSurveyId) throws Exception {
		return dao.findByGrantComplianceSurvey(grantComplianceSurveyId);
	}
	
	public WorkplaceMonitoringMitigationPlan findByLearnerSurveyAnswer(Long learnerSurveyAnswerId) throws Exception {
		return dao.findByLearnerSurveyAnswer(learnerSurveyAnswerId);
	}
	
	public List<WorkplaceMonitoringMitigationPlan> findByWorkplaceMonitoringSiteVisitId(Long workplaceMonitoringSiteVisitId) throws Exception {
		return dao.findByWorkplaceMonitoringSiteVisitId(workplaceMonitoringSiteVisitId);
	}
	
	public Integer countMitigationPlanByInformationProvidedAndSiteVisitId(Long workplaceMonitoringSiteVisitId, Boolean infoProvided) throws Exception {
		return dao.countMitigationPlanByInformationProvidedAndSiteVisitId(workplaceMonitoringSiteVisitId, infoProvided);
	}
	
	public Integer countNonCompliantAndOpenMitigationPlanBySiteVisitId(Long workplaceMonitoringSiteVisitId, YesNoEnum yesNoValue, OpenClosedEnum openCloseEnum) throws Exception {
		return dao.countNonCompliantAndOpenMitigationPlanBySiteVisitId(workplaceMonitoringSiteVisitId, yesNoValue, openCloseEnum);
	}
	
	public Integer countOpenMitigationPlanBySiteVisitId(Long workplaceMonitoringSiteVisitId, OpenClosedEnum openCloseEnum) throws Exception {
		return dao.countOpenMitigationPlanBySiteVisitId(workplaceMonitoringSiteVisitId, openCloseEnum);
	}
	
	public void updatePlan(WorkplaceMonitoringMitigationPlan entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		entity.setAllInfoProvided(true);
		update(entity);
	}
	
	/* Generation aspects Start*/
	
	// Learner Monitoring Survey Answers
	public void generateMitigationPlanByLearnerMonitoringSurvey(WorkplaceMonitoringSiteVisit siteVisit, Users user, WorkplaceMonitoringLearnerSurvey learnerSurveyLink, WorkplaceMonitoringLearnerSurveyAnswers learnerSurveyAnswerLink) throws Exception { 
		WorkplaceMonitoringMitigationPlan existingPlan = findByLearnerSurveyAnswer(learnerSurveyAnswerLink.getId());
		if (learnerSurveyAnswerLink.getAnswer() == YesNoEnum.No) {
			String systemGenMsg = "";
			if (existingPlan != null && existingPlan.getId() != null) {
				existingPlan.setLastActionUser(user);
				existingPlan.setLastActionDate(getSynchronizedDate());
				existingPlan.setSoftDeleted(false);
				update(existingPlan);
			} else {
				createNewMitigationPlan(siteVisit, user, null, learnerSurveyLink, learnerSurveyAnswerLink, true, systemGenMsg);
			}
		} else {
			if (existingPlan != null && existingPlan.getId() != null) {
				existingPlan.setLastActionUser(user);
				existingPlan.setLastActionDate(getSynchronizedDate());
				existingPlan.setSoftDeleted(true);
				update(existingPlan);
			}
		}
	}
	
	// Discretionary Grant Compliance Survey Create
	public void generateMitigationPlanByGrantComplianceSurvey(WorkplaceMonitoringSiteVisit siteVisit, Users user, WorkplaceMonitoringDiscretionaryGrantComplianceSurvey survey) throws Exception { 
		WorkplaceMonitoringMitigationPlan existingPlan = findByGrantComplianceSurvey(survey.getId());
		if (survey.getAnswer() == YesNoEnum.No) {
			String systemGenMsg = "";
			if (existingPlan != null && existingPlan.getId() != null) {
				existingPlan.setLastActionUser(user);
				existingPlan.setLastActionDate(getSynchronizedDate());
				existingPlan.setSoftDeleted(false);
				update(existingPlan);
			} else {
				createNewMitigationPlan(siteVisit, user, survey, null, null, true, systemGenMsg);
			}
		} else {
			if (existingPlan != null && existingPlan.getId() != null) {
				existingPlan.setLastActionUser(user);
				existingPlan.setLastActionDate(getSynchronizedDate());
				existingPlan.setSoftDeleted(true);
				update(existingPlan);
			}
		}
	}
	
	// Generic create method
	public void createNewMitigationPlan(WorkplaceMonitoringSiteVisit siteVisit, Users sessonUser, 
			WorkplaceMonitoringDiscretionaryGrantComplianceSurvey survey, WorkplaceMonitoringLearnerSurvey learnerSurveyLink, WorkplaceMonitoringLearnerSurveyAnswers learnerSurveyAnswerLink, 
			Boolean systemGenerated, String systemGenMsg) throws Exception {
		
		// new instance where it sets default information
		WorkplaceMonitoringMitigationPlan plan = new WorkplaceMonitoringMitigationPlan();
		plan.setCreateUser(sessonUser);
		plan.setLastActionUser(sessonUser);
		plan.setLastActionDate(getSynchronizedDate());
		plan.setWorkplaceMonitoringSiteVisit(siteVisit);
		plan.setSystemGenerated(systemGenerated);
		plan.setStatus(StatusEnum.NotStarted);
		plan.setOpenClosedEnum(OpenClosedEnum.Open);
		plan.setNonComplianceIssue(YesNoEnum.Yes);
		plan.setSoftDeleted(false);
		plan.setAllInfoProvided(false);
		if (systemGenerated != null && systemGenerated) {
			plan.setSystemGeneratedMessage(systemGenMsg);
		}
		
		// Grant Compliance Survey
		if (survey != null && survey.getId() != null) {
			plan.setDiscretionaryGrantComplianceLinkSurvey(survey);
		}
		
		// Learner Survey
		if (learnerSurveyLink != null && learnerSurveyLink.getId() != null) {
			if (learnerSurveyAnswerLink != null && learnerSurveyAnswerLink.getId() != null) {
				plan.setLearnerSurveyLink(learnerSurveyLink);
				plan.setLearnerSurveyAnswerLink(learnerSurveyAnswerLink);
			}
		}
		
		create(plan);
	}
	
	/* Generation aspects End*/
	
		
}