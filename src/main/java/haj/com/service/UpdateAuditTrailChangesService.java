package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UpdateAuditTrailChangesDAO;
import haj.com.entity.UpdateAuditTrailChanges;
import haj.com.framework.AbstractService;

public class UpdateAuditTrailChangesService extends AbstractService {
	/** The dao. */
	private UpdateAuditTrailChangesDAO dao = new UpdateAuditTrailChangesDAO();

	/**
	 * Get all UpdateAuditTrailChanges
 	 * @author TechFinium 
 	 * @see   UpdateAuditTrailChanges
 	 * @return a list of {@link haj.com.entity.UpdateAuditTrailChanges}
	 * @throws Exception the exception
 	 */
	public List<UpdateAuditTrailChanges> allUpdateAuditTrailChanges() throws Exception {
	  	return dao.allUpdateAuditTrailChanges();
	}


	/**
	 * Create or update UpdateAuditTrailChanges.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UpdateAuditTrailChanges
	 */
	public void create(UpdateAuditTrailChanges entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UpdateAuditTrailChanges.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UpdateAuditTrailChanges
	 */
	public void update(UpdateAuditTrailChanges entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UpdateAuditTrailChanges.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UpdateAuditTrailChanges
	 */
	public void delete(UpdateAuditTrailChanges entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UpdateAuditTrailChanges}
	 * @throws Exception the exception
	 * @see    UpdateAuditTrailChanges
	 */
	public UpdateAuditTrailChanges findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UpdateAuditTrailChanges by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UpdateAuditTrailChanges}
	 * @throws Exception the exception
	 * @see    UpdateAuditTrailChanges
	 */
	public List<UpdateAuditTrailChanges> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UpdateAuditTrailChanges
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UpdateAuditTrailChanges}
	 * @throws Exception the exception
	 */
	public List<UpdateAuditTrailChanges> allUpdateAuditTrailChanges(int first, int pageSize) throws Exception {
		return dao.allUpdateAuditTrailChanges(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UpdateAuditTrailChanges for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UpdateAuditTrailChanges
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UpdateAuditTrailChanges.class);
	}
	
    /**
     * Lazy load UpdateAuditTrailChanges with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UpdateAuditTrailChanges class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UpdateAuditTrailChanges} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UpdateAuditTrailChanges> allUpdateAuditTrailChanges(Class<UpdateAuditTrailChanges> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UpdateAuditTrailChanges>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UpdateAuditTrailChanges for lazy load with filters
     * @author TechFinium 
     * @param entity UpdateAuditTrailChanges class
     * @param filters the filters
     * @return Number of rows in the UpdateAuditTrailChanges entity
     * @throws Exception the exception     
     */	
	public int count(Class<UpdateAuditTrailChanges> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
