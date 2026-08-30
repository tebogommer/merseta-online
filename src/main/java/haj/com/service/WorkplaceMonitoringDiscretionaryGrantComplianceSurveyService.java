package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkplaceMonitoringDiscretionaryGrantComplianceSurveyDAO;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WorkplaceMonitoringDiscretionaryGrantComplianceSurveyService extends AbstractService {
	/** The dao. */
	private WorkplaceMonitoringDiscretionaryGrantComplianceSurveyDAO dao = new WorkplaceMonitoringDiscretionaryGrantComplianceSurveyDAO();
	
	/* The Service Levels */
	private WorkplaceMonitoringMitigationPlanService monitoringMitigationPlanService;

	/**
	 * Get all WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey() throws Exception {
	  	return dao.allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey();
	}

	/**
	 * Create or update WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void create(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void update(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public void delete(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	private List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> populateAdditionalInformationList(List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> list) throws Exception {
		for (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringDiscretionaryGrantComplianceSurvey populateAdditionalInformation(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public WorkplaceMonitoringDiscretionaryGrantComplianceSurvey findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringDiscretionaryGrantComplianceSurvey by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 */
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringDiscretionaryGrantComplianceSurvey for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringDiscretionaryGrantComplianceSurvey
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringDiscretionaryGrantComplianceSurvey with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringDiscretionaryGrantComplianceSurvey class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringDiscretionaryGrantComplianceSurvey} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurvey(Class<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of WorkplaceMonitoringDiscretionaryGrantComplianceSurvey for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringDiscretionaryGrantComplianceSurvey class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> findByNoTargetClassAndKey() throws Exception {
		return dao.findByNoTargetClassAndKey();
	}
	
	public Integer countByNoTargetClassAndKey() throws Exception {
		return dao.countByNoTargetClassAndKey();
	}
	
	public void createLookUpEntry(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity) throws Exception{
		entity.setTargetClass(null);
		entity.setTargetKey(null);
		create(entity);
	}
	
	public void updateExisitingEntry(WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		create(entity);
	}
	
	public void updateEntryAndActionPlan(WorkplaceMonitoringSiteVisit siteVisit, WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entity, Users sessionUser) throws Exception{
		updateExisitingEntry(entity, sessionUser);
		
		// do mitigation plan logic
		if (siteVisit != null) {
			if (monitoringMitigationPlanService == null) {
				monitoringMitigationPlanService = new WorkplaceMonitoringMitigationPlanService();
			}
			monitoringMitigationPlanService.generateMitigationPlanByGrantComplianceSurvey(siteVisit, sessionUser, entity);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurveyWhereNoTragetClassAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.targetClass is null and o.targetKey is null";
		return (List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringDiscretionaryGrantComplianceSurveyWhereNoTragetClassAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.targetClass is null and o.targetKey is null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> allWorkplaceMonitoringDiscretionaryGrantComplianceSurveyByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return populateAdditionalInformationList((List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringDiscretionaryGrantComplianceSurveyByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringDiscretionaryGrantComplianceSurvey o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public void createWorkplaceMonitoringDiscretionaryGrantComplianceSurvey(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, Users sessionUser) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> list = findByNoTargetClassAndKey();
		for (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entry : list) {
			entry.setId(null);
			entry.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
			entry.setTargetKey(workplaceMonitoringSiteVisit.getId());
			if (sessionUser != null && sessionUser.getId() != null) {
				entry.setLastActionUser(sessionUser);
			}
			entry.setLastActionDate(getSynchronizedDate());
			createList.add(entry);
		}
		
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}
	
	public List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> returnWorkplaceMonitoringDiscretionaryGrantComplianceSurveyToBeCreated(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, Users sessionUser) throws Exception{
		List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> createList = new ArrayList<>();
		List<WorkplaceMonitoringDiscretionaryGrantComplianceSurvey> list = findByNoTargetClassAndKey();
		for (WorkplaceMonitoringDiscretionaryGrantComplianceSurvey entry : list) {
			entry.setId(null);
			entry.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
			entry.setTargetKey(workplaceMonitoringSiteVisit.getId());
			if (sessionUser != null && sessionUser.getId() != null) {
				entry.setLastActionUser(sessionUser);
			}
			entry.setLastActionDate(getSynchronizedDate());
			createList.add(entry);
		}
		list = null;
		
		if (!createList.isEmpty()) {
			return createList;
		} else {
			return new ArrayList<>();
		}
	}
}
