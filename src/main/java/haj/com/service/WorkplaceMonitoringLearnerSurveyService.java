package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkplaceMonitoringLearnerSurveyDAO;
import haj.com.entity.CompanyLearners;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringLearnerSurvey;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WorkplaceMonitoringLearnerSurveyService extends AbstractService {

	/** The dao. */
	private WorkplaceMonitoringLearnerSurveyDAO dao = new WorkplaceMonitoringLearnerSurveyDAO();
	
	
	/* The Service Levels */
	private WorkplaceMonitoringLearnerSurveyAnswersService workplaceMonitoringLearnerSurveyAnswersService = new WorkplaceMonitoringLearnerSurveyAnswersService();
	private WorkplaceMonitoringMitigationPlanService workplaceMonitoringMitigationPlanService;

	/**
	 * Get all WorkplaceMonitoringLearnerSurvey
	 * 
	 * @author TechFinium
	 * @see WorkplaceMonitoringLearnerSurvey
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkplaceMonitoringLearnerSurvey> allWorkplaceMonitoringLearnerSurvey() throws Exception {
		return dao.allWorkplaceMonitoringLearnerSurvey();
	}

	/**
	 * Create or update WorkplaceMonitoringLearnerSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringLearnerSurvey
	 */
	public void create(WorkplaceMonitoringLearnerSurvey entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update WorkplaceMonitoringLearnerSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringLearnerSurvey
	 */
	public void update(WorkplaceMonitoringLearnerSurvey entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WorkplaceMonitoringLearnerSurvey.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringLearnerSurvey
	 */
	public void delete(WorkplaceMonitoringLearnerSurvey entity) throws Exception {
		this.dao.delete(entity);
	}
	
	private List<WorkplaceMonitoringLearnerSurvey> populateAdditionalInformationList(List<WorkplaceMonitoringLearnerSurvey> list) throws Exception {
		for (WorkplaceMonitoringLearnerSurvey entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringLearnerSurvey populateAdditionalInformation(WorkplaceMonitoringLearnerSurvey entity) throws Exception {
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
	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringLearnerSurvey
	 */
	public WorkplaceMonitoringLearnerSurvey findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringLearnerSurvey by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
	 * @throws Exception
	 *             the exception
	 * @see WorkplaceMonitoringLearnerSurvey
	 */
	public List<WorkplaceMonitoringLearnerSurvey> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WorkplaceMonitoringLearnerSurvey
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
	 * @throws Exception
	 *             the exception
	 */
	public List<WorkplaceMonitoringLearnerSurvey> allWorkplaceMonitoringLearnerSurvey(int first, int pageSize)
			throws Exception {
		return dao.allWorkplaceMonitoringLearnerSurvey(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WorkplaceMonitoringLearnerSurvey for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the WorkplaceMonitoringLearnerSurvey
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WorkplaceMonitoringLearnerSurvey.class);
	}

	/**
	 * Lazy load WorkplaceMonitoringLearnerSurvey with pagination, filter,
	 * sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            WorkplaceMonitoringLearnerSurvey class
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
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurvey}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurvey> allWorkplaceMonitoringLearnerSurvey(Class<WorkplaceMonitoringLearnerSurvey> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WorkplaceMonitoringLearnerSurvey>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of WorkplaceMonitoringLearnerSurvey for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            WorkplaceMonitoringLearnerSurvey class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WorkplaceMonitoringLearnerSurvey entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WorkplaceMonitoringLearnerSurvey> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurvey> allWorkplaceMonitoringLearnerSurveyByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringLearnerSurvey o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return populateAdditionalInformationList((List<WorkplaceMonitoringLearnerSurvey>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringLearnerSurveyByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringLearnerSurvey o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public WorkplaceMonitoringLearnerSurvey prepNewLearnerMonitroingSurveyWithQuestions(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, CompanyLearners selectedLearner, Users sessionUser) throws Exception{
		WorkplaceMonitoringLearnerSurvey entity = new WorkplaceMonitoringLearnerSurvey();
		entity.setCompanyLearners(selectedLearner);
		entity.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
		entity.setTargetKey(workplaceMonitoringSiteVisit.getId());
		entity.setInterventionType(selectedLearner.getInterventionType());
		entity.setCreateUser(sessionUser);
		entity.setWorkplaceMonitoringLearnerSurveyAnswersList(new ArrayList<>());
		entity.getWorkplaceMonitoringLearnerSurveyAnswersList().addAll(workplaceMonitoringLearnerSurveyAnswersService.findByNoTargetClassAndKeyByIntervetionType(selectedLearner.getInterventionType().getId()));
		return entity;
	}
	
	public void validiateInformation(WorkplaceMonitoringLearnerSurvey learnerSurvey) throws Exception{
		// check to ensure all answers provided
		if (!learnerSurvey.getWorkplaceMonitoringLearnerSurveyAnswersList().isEmpty()) {
			for (WorkplaceMonitoringLearnerSurveyAnswers question : learnerSurvey.getWorkplaceMonitoringLearnerSurveyAnswersList()) {
				if (question.getAnswer() == null) {
					throw new Exception("Provide all answers for the survey before proceeding.");
				}
			}
		} else {
			throw new Exception("Error locating questions for the survey. Contact Support!");
		}
	}
	
	public void createSurveyForMonitoringSiteVisit(WorkplaceMonitoringLearnerSurvey learnerSurvey, Users sessionUser) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		createList.add(learnerSurvey);
		for (WorkplaceMonitoringLearnerSurveyAnswers question : learnerSurvey.getWorkplaceMonitoringLearnerSurveyAnswersList()) {
			question.setId(null);
			question.setTargetClass(learnerSurvey.getClass().getName());
			question.setTargetKey(learnerSurvey.getId());
			question.setLastActionUser(sessionUser);
			question.setLastActionDate(getSynchronizedDate());
			createList.add(question);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}

	public void createFullSurvey(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, WorkplaceMonitoringLearnerSurvey mainSurvey, List<WorkplaceMonitoringLearnerSurveyAnswers> workplaceMonitoringLearnerSurveyAnswersList, Users activeUser)  throws Exception{
		// check if all answers provided
		for (WorkplaceMonitoringLearnerSurveyAnswers answer : workplaceMonitoringLearnerSurveyAnswersList) {
			if (answer.getAnswer() == null) {
				throw new Exception("Provide all answers before proceeding.");
			}
		}
		mainSurvey.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
		mainSurvey.setTargetKey(workplaceMonitoringSiteVisit.getId());
		mainSurvey.setCreateUser(activeUser);
		create(mainSurvey);
		List<IDataEntity> createList = new ArrayList<>();
		for (WorkplaceMonitoringLearnerSurveyAnswers answer : workplaceMonitoringLearnerSurveyAnswersList) {
			answer.setId(null);
			answer.setTargetClass(mainSurvey.getClass().getName());
			answer.setTargetKey(mainSurvey.getId());
			answer.setLastActionUser(activeUser);
			answer.setLastActionDate(getSynchronizedDate());
			createList.add(answer);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		
		// do mitigation plan actions
		if (workplaceMonitoringSiteVisit != null) {
			// relocate main survey
			WorkplaceMonitoringLearnerSurvey learnerSurveyLink = findByKey(mainSurvey.getId());
			// relocate all answers
			List<WorkplaceMonitoringLearnerSurveyAnswers> answersList = workplaceMonitoringLearnerSurveyAnswersService.findByTargetClassAndKey(learnerSurveyLink.getClass().getName(), learnerSurveyLink.getId());
			if (!answersList.isEmpty()) {
				
				if (workplaceMonitoringMitigationPlanService == null) {
					workplaceMonitoringMitigationPlanService = new WorkplaceMonitoringMitigationPlanService();
				}
				
				for (WorkplaceMonitoringLearnerSurveyAnswers answer : answersList) {
					workplaceMonitoringMitigationPlanService.generateMitigationPlanByLearnerMonitoringSurvey(workplaceMonitoringSiteVisit, activeUser, learnerSurveyLink, answer);
				}
				
			}
			
		}
	}

	public Integer countByTargetClassKeyAndCompanyLearner(String targetClass, Long targetKey, Long companyLearnersId) throws Exception{
		return dao.countByTargetClassKeyAndCompanyLearner(targetClass, targetKey, companyLearnersId);
	}
}
