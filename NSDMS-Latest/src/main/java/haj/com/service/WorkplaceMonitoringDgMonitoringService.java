package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WorkplaceMonitoringDgMonitoringDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringDgMonitoring;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.framework.AbstractService;

public class WorkplaceMonitoringDgMonitoringService extends AbstractService {
	
	
	/** The dao. */
	private WorkplaceMonitoringDgMonitoringDAO dao = new WorkplaceMonitoringDgMonitoringDAO();

	/**
	 * Get all WorkplaceMonitoringDgMonitoring
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringDgMonitoring
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringDgMonitoring> allWorkplaceMonitoringDgMonitoring() throws Exception {
	  	return dao.allWorkplaceMonitoringDgMonitoring();
	}


	/**
	 * Create or update WorkplaceMonitoringDgMonitoring.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringDgMonitoring
	 */
	public void create(WorkplaceMonitoringDgMonitoring entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringDgMonitoring.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDgMonitoring
	 */
	public void update(WorkplaceMonitoringDgMonitoring entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringDgMonitoring.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDgMonitoring
	 */
	public void delete(WorkplaceMonitoringDgMonitoring entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	private List<WorkplaceMonitoringDgMonitoring> populateAdditionalInformationList(List<WorkplaceMonitoringDgMonitoring> list) throws Exception {
		for (WorkplaceMonitoringDgMonitoring entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringDgMonitoring populateAdditionalInformation(WorkplaceMonitoringDgMonitoring entity) throws Exception {
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
	 * @return a {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDgMonitoring
	 */
	public WorkplaceMonitoringDgMonitoring findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringDgMonitoring by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringDgMonitoring
	 */
	public List<WorkplaceMonitoringDgMonitoring> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringDgMonitoring
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringDgMonitoring> allWorkplaceMonitoringDgMonitoring(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringDgMonitoring(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringDgMonitoring for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringDgMonitoring
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringDgMonitoring.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringDgMonitoring with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringDgMonitoring class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringDgMonitoring} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDgMonitoring> allWorkplaceMonitoringDgMonitoring(Class<WorkplaceMonitoringDgMonitoring> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringDgMonitoring>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkplaceMonitoringDgMonitoring for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringDgMonitoring class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringDgMonitoring entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringDgMonitoring> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public WorkplaceMonitoringDgMonitoring createNewWorkplaceMonitoringDgMonitoring(ActiveContracts activeContract, String targetClass, Long targetKey, Users createUser) throws Exception{
		WorkplaceMonitoringDgMonitoring newEntry = new WorkplaceMonitoringDgMonitoring();
		if (activeContract != null && activeContract.getId() != null) {
			newEntry.setActiveContracts(activeContract);
			if (activeContract.getStatus() != null) {
				newEntry.setActiveContractStatus(activeContract.getStatus());
				if (ApprovalEnum.getOpenStatusForActiveContracts().contains(activeContract.getStatus())) {
					newEntry.setCanTermiateMoa(true);
				}
			} else {
				newEntry.setActiveContractStatus(null);
				newEntry.setCanTermiateMoa(false);
			}
			if (activeContract.getMoaType() != null) {
				newEntry.setMoaType(activeContract.getMoaType());
			}
		} else {
			newEntry.setCanTermiateMoa(false);
		}
		newEntry.setTargetClass(targetClass);
		newEntry.setTargetKey(targetKey);
		if (createUser != null && createUser.getId() != null) {
			newEntry.setLastActionUser(createUser);
		}
		newEntry.setLastActionDate(getSynchronizedDate());
		return newEntry;
	}
	
	public void updateExisitingEntry(WorkplaceMonitoringDgMonitoring entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		create(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringDgMonitoring> allWorkplaceMonitoringDgMonitoringByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringDgMonitoring o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return populateAdditionalInformationList((List<WorkplaceMonitoringDgMonitoring>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringDgMonitoringByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringDgMonitoring o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
}
