package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkplaceMonitoringActionPlanDAO;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringActionPlan;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.ActionPlanValidiationTypeEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WorkplaceMonitoringActionPlanService extends AbstractService {
	
	
	/** The dao. */
	private WorkplaceMonitoringActionPlanDAO dao = new WorkplaceMonitoringActionPlanDAO();

	// entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
	
	/**
	 * Get all WorkplaceMonitoringActionPlan
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringActionPlan
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlan() throws Exception {
	  	return dao.allWorkplaceMonitoringActionPlan();
	}


	/**
	 * Create or update WorkplaceMonitoringActionPlan.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringActionPlan
	 */
	public void create(WorkplaceMonitoringActionPlan entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringActionPlan.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringActionPlan
	 */
	public void update(WorkplaceMonitoringActionPlan entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringActionPlan.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringActionPlan
	 */
	public void delete(WorkplaceMonitoringActionPlan entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringActionPlan}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringActionPlan
	 */
	public WorkplaceMonitoringActionPlan findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringActionPlan by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringActionPlan
	 */
	public List<WorkplaceMonitoringActionPlan> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringActionPlan
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlan(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringActionPlan(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringActionPlan for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringActionPlan
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	    return dao.count(WorkplaceMonitoringActionPlan.class);
	}
	
	private List<WorkplaceMonitoringActionPlan> populateAdditionalInformationList(List<WorkplaceMonitoringActionPlan> list) throws Exception {
		for (WorkplaceMonitoringActionPlan entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringActionPlan populateAdditionalInformation(WorkplaceMonitoringActionPlan entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}
	
    /**
     * Lazy load WorkplaceMonitoringActionPlan with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringActionPlan class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringActionPlan} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlan(Class<WorkplaceMonitoringActionPlan> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WorkplaceMonitoringActionPlan>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of WorkplaceMonitoringActionPlan for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringActionPlan class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringActionPlan entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringActionPlan> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void createLookUpEntry(WorkplaceMonitoringActionPlan entity) throws Exception{
		if (entity.getId() != null) {
			if (countByActionPlanValidiationTypeAndDoesNotEqualId(entity.getActionPlanValidiationTypeEnum(), entity.getId()) > 0) {
				throw new Exception("Validiation Type Already Assigned. Please Select A Different Validiation.");
			}
		} else {
			if (countByActionPlanValidiationType(entity.getActionPlanValidiationTypeEnum()) > 0) {
				throw new Exception("Validiation Type Already Assigned. Please Select A Different Validiation.");
			}
		}
		entity.setTargetClass(null);
		entity.setTargetKey(null);
		create(entity);
	}
	
	public void updateExisitingEntry(WorkplaceMonitoringActionPlan entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		create(entity);
	}
	
	public List<WorkplaceMonitoringActionPlan> findByNoTargetClassAndKey() throws Exception {
		return dao.findByNoTargetClassAndKey();
	}
	
	public Integer countByActionPlanValidiationType(ActionPlanValidiationTypeEnum actionPlanValidiationType) throws Exception {
		return dao.countByActionPlanValidiationType(actionPlanValidiationType);
	}
	
	public Integer countByActionPlanValidiationTypeAndDoesNotEqualId(ActionPlanValidiationTypeEnum actionPlanValidiationType, Long workplaceMonitoringActionPlanId) throws Exception {
		return dao.countByActionPlanValidiationTypeAndDoesNotEqualId(actionPlanValidiationType, workplaceMonitoringActionPlanId);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlanWhereNoTragetClassAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WorkplaceMonitoringActionPlan o where o.targetClass is null and o.targetKey is null";
		return populateAdditionalInformationList((List<WorkplaceMonitoringActionPlan>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringActionPlanWhereNoTragetClassAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringActionPlan o where o.targetClass is null and o.targetKey is null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringActionPlan> allWorkplaceMonitoringActionPlanByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringActionPlan o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return populateAdditionalInformationList((List<WorkplaceMonitoringActionPlan>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringActionPlanByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringActionPlan o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public void createWorkplaceMonitoring(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, Users sessionUser) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		List<WorkplaceMonitoringActionPlan> list = findByNoTargetClassAndKey();
		for (WorkplaceMonitoringActionPlan workplaceMonitoringActionPlan : list) {
			WorkplaceMonitoringActionPlan newEntry = new WorkplaceMonitoringActionPlan();
			newEntry.setActionPlanValidiationTypeEnum(workplaceMonitoringActionPlan.getActionPlanValidiationTypeEnum());
			newEntry.setCriteria(workplaceMonitoringActionPlan.getCriteria());
			newEntry.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
			newEntry.setTargetKey(workplaceMonitoringSiteVisit.getId());
			if (sessionUser != null && sessionUser.getId() != null) {
				newEntry.setLastActionUser(sessionUser);
			}
			newEntry.setLastActionDate(getSynchronizedDate());
			createList.add(newEntry);
			
			// calculate risks here for future dev
		}

		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		

	}
	
	public List<WorkplaceMonitoringActionPlan> returnWorkplaceMonitoringActionPlanListToBeCreated(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit, Users sessionUser) throws Exception{
		List<WorkplaceMonitoringActionPlan> createList = new ArrayList<>();
		List<WorkplaceMonitoringActionPlan> list = findByNoTargetClassAndKey();
		for (WorkplaceMonitoringActionPlan workplaceMonitoringActionPlan : list) {
			WorkplaceMonitoringActionPlan newEntry = new WorkplaceMonitoringActionPlan();
			newEntry.setActionPlanValidiationTypeEnum(workplaceMonitoringActionPlan.getActionPlanValidiationTypeEnum());
			newEntry.setCriteria(workplaceMonitoringActionPlan.getCriteria());
			newEntry.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
			newEntry.setTargetKey(workplaceMonitoringSiteVisit.getId());
			if (sessionUser != null && sessionUser.getId() != null) {
				newEntry.setLastActionUser(sessionUser);
			}
			newEntry.setLastActionDate(getSynchronizedDate());
			createList.add(newEntry);
			// calculate risks here for future dev
		}
		list = null;

		if (createList.isEmpty()) {
			return new ArrayList<>();
		}else {
			return createList;
		}

	}
}
