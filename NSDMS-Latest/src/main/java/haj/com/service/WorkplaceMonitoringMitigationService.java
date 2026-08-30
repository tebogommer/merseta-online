package haj.com.service;

import java.util.List;

import haj.com.dao.WorkplaceMonitoringMitigationDAO;
import haj.com.entity.CompanyLearners;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.WorkplaceMonitoringMitigation;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class WorkplaceMonitoringMitigationService extends AbstractService {
	/** The dao. */
	private WorkplaceMonitoringMitigationDAO dao = new WorkplaceMonitoringMitigationDAO();

	/**
	 * Get all WorkplaceMonitoringMitigation
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringMitigation
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringMitigation> allWorkplaceMonitoringMitigation() throws Exception {
	  	return dao.allWorkplaceMonitoringMitigation();
	}


	/**
	 * Create or update WorkplaceMonitoringMitigation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringMitigation
	 */
	public void create(WorkplaceMonitoringMitigation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringMitigation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringMitigation
	 */
	public void update(WorkplaceMonitoringMitigation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringMitigation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringMitigation
	 */
	public void delete(WorkplaceMonitoringMitigation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringMitigation
	 */
	public WorkplaceMonitoringMitigation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringMitigation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringMitigation
	 */
	public List<WorkplaceMonitoringMitigation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringMitigation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringMitigation}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringMitigation> allWorkplaceMonitoringMitigation(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringMitigation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringMitigation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringMitigation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringMitigation.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringMitigation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringMitigation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringMitigation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringMitigation> allWorkplaceMonitoringMitigation(Class<WorkplaceMonitoringMitigation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringMitigation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkplaceMonitoringMitigation for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringMitigation class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringMitigation entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringMitigation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<WorkplaceMonitoringMitigation> sortAndFilter(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, WorkplaceMonitoring workplaceMonitoring) {
		return dao.sortAndFilter(startingAt, pageSize, sortField, sortOrder, filters, workplaceMonitoring);
	}
	
	public int count(Map<String, Object> filters, WorkplaceMonitoring workplaceMonitoring) {
		return dao.count(filters, workplaceMonitoring);
	}
}
