package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkplaceMonitoringLearnerSurveyAnswersDAO;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers;
import haj.com.entity.lookup.InterventionType;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WorkplaceMonitoringLearnerSurveyAnswersService extends AbstractService {
	
	/** The dao. */
	private WorkplaceMonitoringLearnerSurveyAnswersDAO dao = new WorkplaceMonitoringLearnerSurveyAnswersDAO();

	/**
	 * Get all WorkplaceMonitoringLearnerSurveyAnswers
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringLearnerSurveyAnswers
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswers() throws Exception {
	  	return dao.allWorkplaceMonitoringLearnerSurveyAnswers();
	}


	/**
	 * Create or update WorkplaceMonitoringLearnerSurveyAnswers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void create(WorkplaceMonitoringLearnerSurveyAnswers entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	public void createLookUpEntry(WorkplaceMonitoringLearnerSurveyAnswers entity) throws Exception{
		entity.setTargetClass(null);
		entity.setTargetKey(null);
		create(entity);
	}
	
	public void copyLookUpAnswersByInterventionTypeToAnother(InterventionType interventionTypeAnswers, InterventionType newInterventionType) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		List<WorkplaceMonitoringLearnerSurveyAnswers> entityList = findByNoTargetClassAndKeyByIntervetionType(interventionTypeAnswers.getId());
		if (entityList.isEmpty()) {
			throw new Exception("No questions configured for intervention type: " + interventionTypeAnswers.getDescription());
		}
		for (WorkplaceMonitoringLearnerSurveyAnswers workplaceMonitoringLearnerSurveyAnswers : entityList) {
			WorkplaceMonitoringLearnerSurveyAnswers newEntry = new WorkplaceMonitoringLearnerSurveyAnswers();
			newEntry.setQuestion(workplaceMonitoringLearnerSurveyAnswers.getQuestion());
			newEntry.setInterventionType(newInterventionType);
			createList.add(newEntry);
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
	}
	
	public void deleteAllQuestionsByInterventionType(InterventionType interventionType) throws Exception {
		List<IDataEntity> deleteList = new ArrayList<>();
		List<WorkplaceMonitoringLearnerSurveyAnswers> entityList = findByNoTargetClassAndKeyByIntervetionType(interventionType.getId());
		if (entityList.isEmpty()) {
			throw new Exception("No questions configured for intervention type: " + interventionType.getDescription());
		}else {
			deleteList.addAll(entityList);
		}
		if (!deleteList.isEmpty()) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public void updateExisitingEntry(WorkplaceMonitoringLearnerSurveyAnswers entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		update(entity);
	}
	
	/**
	 * Update  WorkplaceMonitoringLearnerSurveyAnswers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void update(WorkplaceMonitoringLearnerSurveyAnswers entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringLearnerSurveyAnswers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public void delete(WorkplaceMonitoringLearnerSurveyAnswers entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public WorkplaceMonitoringLearnerSurveyAnswers findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringLearnerSurveyAnswers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerSurveyAnswers
	 */
	public List<WorkplaceMonitoringLearnerSurveyAnswers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringLearnerSurveyAnswers
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswers(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringLearnerSurveyAnswers(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringLearnerSurveyAnswers for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringLearnerSurveyAnswers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringLearnerSurveyAnswers.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringLearnerSurveyAnswers with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringLearnerSurveyAnswers class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringLearnerSurveyAnswers} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswers(Class<WorkplaceMonitoringLearnerSurveyAnswers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringLearnerSurveyAnswers>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of WorkplaceMonitoringLearnerSurveyAnswers for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringLearnerSurveyAnswers class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringLearnerSurveyAnswers entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringLearnerSurveyAnswers> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<WorkplaceMonitoringLearnerSurveyAnswers> findByNoTargetClassAndKeyByIntervetionType(Long interventionTypeId) throws Exception {
		return dao.findByNoTargetClassAndKeyByIntervetionType(interventionTypeId);
	}
	
	public List<WorkplaceMonitoringLearnerSurveyAnswers> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	public Integer countByNoTargetClassAndKeyByIntervetionType(Long interventionTypeId) throws Exception {
		return dao.countByNoTargetClassAndKeyByIntervetionType(interventionTypeId);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswersWhereNoTragetClassAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass is null and o.targetKey is null";
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringLearnerSurveyAnswersWhereNoTragetClassAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass is null and o.targetKey is null";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerSurveyAnswers> allWorkplaceMonitoringLearnerSurveyAnswersByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<WorkplaceMonitoringLearnerSurveyAnswers>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllWorkplaceMonitoringLearnerSurveyAnswersByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringLearnerSurveyAnswers o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
}
