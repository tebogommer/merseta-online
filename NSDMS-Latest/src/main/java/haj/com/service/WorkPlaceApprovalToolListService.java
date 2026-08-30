package haj.com.service;

import java.util.List;

import haj.com.dao.WorkPlaceApprovalToolListDAO;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalToolList;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class WorkPlaceApprovalToolListService extends AbstractService {
	/** The dao. */
	private WorkPlaceApprovalToolListDAO dao = new WorkPlaceApprovalToolListDAO();

	/**
	 * Get all WorkPlaceApprovalToolList
 	 * @author TechFinium 
 	 * @see   WorkPlaceApprovalToolList
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalToolList}
	 * @throws Exception the exception
 	 */
	public List<WorkPlaceApprovalToolList> allWorkPlaceApprovalToolList() throws Exception {
	  	return dao.allWorkPlaceApprovalToolList();
	}


	/**
	 * Create or update WorkPlaceApprovalToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkPlaceApprovalToolList
	 */
	public void create(WorkPlaceApprovalToolList entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkPlaceApprovalToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalToolList
	 */
	public void update(WorkPlaceApprovalToolList entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkPlaceApprovalToolList.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalToolList
	 */
	public void delete(WorkPlaceApprovalToolList entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkPlaceApprovalToolList}
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalToolList
	 */
	public WorkPlaceApprovalToolList findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkPlaceApprovalToolList by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalToolList}
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalToolList
	 */
	public List<WorkPlaceApprovalToolList> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkPlaceApprovalToolList
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalToolList}
	 * @throws Exception the exception
	 */
	public List<WorkPlaceApprovalToolList> allWorkPlaceApprovalToolList(int first, int pageSize) throws Exception {
		return dao.allWorkPlaceApprovalToolList(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkPlaceApprovalToolList for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkPlaceApprovalToolList
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkPlaceApprovalToolList.class);
	}
	
    /**
     * Lazy load WorkPlaceApprovalToolList with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkPlaceApprovalToolList class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkPlaceApprovalToolList} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalToolList> allWorkPlaceApprovalToolList(Class<WorkPlaceApprovalToolList> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkPlaceApprovalToolList>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkPlaceApprovalToolList for lazy load with filters
     * @author TechFinium 
     * @param entity WorkPlaceApprovalToolList class
     * @param filters the filters
     * @return Number of rows in the WorkPlaceApprovalToolList entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkPlaceApprovalToolList> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<WorkPlaceApprovalToolList> findByworkplaceapproval(WorkPlaceApproval workplaceapproval) {
		return dao.findByworkplaceapproval(workplaceapproval.getId());
	}
}
